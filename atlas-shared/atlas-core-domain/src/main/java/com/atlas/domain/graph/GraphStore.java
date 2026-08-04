package com.atlas.domain.graph;

import java.util.List;

public interface GraphStore {

    void saveEntity(EntityNode entity);

    EntityNode getEntityById(String id);

    EntityNode resolveCanonicalEntity(String nameOrAlias);

    void saveRelationship(RelationshipEdge relationship);

    List<RelationshipEdge> getEntityNeighbors(String entityId);

    List<EntityNode> searchEntities(String query, int limit);

    int getEntityCount();

    int getRelationshipCount();
}
