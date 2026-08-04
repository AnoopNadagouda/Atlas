package com.atlas.keywordsearch.ranking;

import com.atlas.domain.ranking.LinkEdge;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class LinkGraphService {

    private final Set<String> nodeIds = ConcurrentHashMap.newKeySet();
    private final Map<String, List<String>> outgoingEdges = new ConcurrentHashMap<>();
    private final Map<String, List<String>> incomingEdges = new ConcurrentHashMap<>();

    @PostConstruct
    public void initSeedGraph() {
        log.info("Initializing Link Graph with seed web topology...");

        addLink("doc-foundation-001", "doc-bm25-002");
        addLink("doc-foundation-001", "doc-semantic-003");
        addLink("doc-bm25-002", "doc-foundation-001");
        addLink("doc-semantic-003", "doc-foundation-001");

        log.info("Link Graph initialized with {} page nodes and {} directed hyperlinks",
                nodeIds.size(), getEdgeCount());
    }

    public void addLink(String sourceDocId, String targetDocId) {
        if (sourceDocId == null || targetDocId == null) return;
        nodeIds.add(sourceDocId);
        nodeIds.add(targetDocId);

        outgoingEdges.computeIfAbsent(sourceDocId, k -> new ArrayList<>()).add(targetDocId);
        incomingEdges.computeIfAbsent(targetDocId, k -> new ArrayList<>()).add(sourceDocId);
    }

    public Set<String> getNodeIds() {
        return Collections.unmodifiableSet(nodeIds);
    }

    public List<String> getOutgoingLinks(String docId) {
        return outgoingEdges.getOrDefault(docId, Collections.emptyList());
    }

    public List<String> getIncomingLinks(String docId) {
        return incomingEdges.getOrDefault(docId, Collections.emptyList());
    }

    public int getEdgeCount() {
        int count = 0;
        for (List<String> list : outgoingEdges.values()) {
            count += list.size();
        }
        return count;
    }
}
