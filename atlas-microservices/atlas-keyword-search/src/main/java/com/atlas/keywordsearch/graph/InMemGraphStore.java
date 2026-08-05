package com.atlas.keywordsearch.graph;
import com.atlas.domain.graph.EntityNode;
import com.atlas.domain.graph.GraphStore;
import com.atlas.domain.graph.RelationshipEdge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Primary
@Component("inMemGraphStore")
public class InMemGraphStore implements GraphStore {

    private final Map<String, EntityNode> entities = new ConcurrentHashMap<>();
    private final Map<String, String> aliasToIdMap = new ConcurrentHashMap<>();
    private final Map<String, List<RelationshipEdge>> edges = new ConcurrentHashMap<>();

    @Override
    public void saveEntity(EntityNode entity) {
        if (entity == null || entity.getId() == null) return;
        entities.put(entity.getId(), entity);

        String canonical = entity.getCanonicalName() != null ? entity.getCanonicalName().toLowerCase().trim() : entity.getName().toLowerCase().trim();
        aliasToIdMap.put(canonical, entity.getId());
        aliasToIdMap.put(entity.getName().toLowerCase().trim(), entity.getId());
        

        if (entity.getAliases() != null) {
            for (String alias : entity.getAliases()) {
                aliasToIdMap.put(alias.toLowerCase().trim(), entity.getId());
            }
        }
        log.info("[InMemGraphStore] Saved Entity: '{}' ({})", entity.getName(), entity.getType());
    }

    @Override
    public EntityNode getEntityById(String id) {
        return id != null ? entities.get(id) : null;
    }

    @Override
    public EntityNode resolveCanonicalEntity(String nameOrAlias) {
        if (nameOrAlias == null || nameOrAlias.isBlank()) return null;
        String id = aliasToIdMap.get(nameOrAlias.toLowerCase().trim());
        return id != null ? entities.get(id) : null;
    }

    @Override
    public void saveRelationship(RelationshipEdge relationship) {
        if (relationship == null || relationship.getSourceEntityId() == null) return;
        edges.computeIfAbsent(relationship.getSourceEntityId(), k -> new ArrayList<>()).add(relationship);
        edges.computeIfAbsent(relationship.getTargetEntityId(), k -> new ArrayList<>()).add(relationship);
        log.info("[InMemGraphStore] Saved Relationship: {} -[{}]-> {}",
                relationship.getSourceEntityId(), relationship.getRelationType(), relationship.getTargetEntityId());
    }

    @Override
    public List<RelationshipEdge> getEntityNeighbors(String entityId) {
        if (entityId == null) return Collections.emptyList();
        return edges.getOrDefault(entityId, Collections.emptyList());
    }

    @Override
    public List<EntityNode> searchEntities(String query, int limit) {
        if (query == null || query.isBlank()) return Collections.emptyList();
        String q = query.toLowerCase().trim();

        List<EntityNode> matches = new ArrayList<>();
        for (EntityNode node : entities.values()) {
            if (node.getName().toLowerCase().contains(q) ||
                (node.getCanonicalName() != null && node.getCanonicalName().toLowerCase().contains(q))) {
                matches.add(node);
            }
        }
        return matches.subList(0, Math.min(limit, matches.size()));
    }

    @Override
    public int getEntityCount() {
        return entities.size();
    }

    @Override
    public int getRelationshipCount() {
        int count = 0;
        for (List<RelationshipEdge> list : edges.values()) {
            count += list.size();
        }
        return count / 2; // Undirected degree count adjustment
    }
}
