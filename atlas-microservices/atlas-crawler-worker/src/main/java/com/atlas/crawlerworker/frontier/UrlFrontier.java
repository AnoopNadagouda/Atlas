package com.atlas.crawlerworker.frontier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

@Component
public class UrlFrontier {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CrawlTask implements Comparable<CrawlTask> {
        private String jobId;
        private String url;
        private String normalizedUrl;
        private String parentUrl;
        private int depth;
        private int priority;

        @Override
        public int compareTo(CrawlTask o) {
            // Lower depth & higher priority first
            int depthComp = Integer.compare(this.depth, o.depth);
            if (depthComp != 0) return depthComp;
            return Integer.compare(o.priority, this.priority);
        }
    }

    private final PriorityBlockingQueue<CrawlTask> queue = new PriorityBlockingQueue<>(1000);
    private final Set<String> visitedSet = ConcurrentHashMap.newKeySet();

    public boolean schedule(CrawlTask task) {
        if (task == null || task.getNormalizedUrl() == null) return false;
        
        String key = task.getJobId() + ":" + task.getNormalizedUrl();
        if (visitedSet.add(key)) {
            queue.offer(task);
            return true;
        }
        return false;
    }

    public CrawlTask poll() {
        return queue.poll();
    }

    public int queueSize() {
        return queue.size();
    }

    public boolean isVisited(String jobId, String normalizedUrl) {
        return visitedSet.contains(jobId + ":" + normalizedUrl);
    }

    public void clearJob(String jobId) {
        visitedSet.removeIf(key -> key.startsWith(jobId + ":"));
        queue.removeIf(task -> task.getJobId().equals(jobId));
    }
}
