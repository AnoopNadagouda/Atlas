package com.atlas.gateway.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.common.dto.ClusterHealthDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ClusterHealthController {

    @GetMapping("/cluster/health")
    public Mono<ResponseEntity<ApiResponse<ClusterHealthDto>>> getClusterHealth() {
        Map<String, String> services = new LinkedHashMap<>();
        services.put("atlas-config-service", "UP");
        services.put("atlas-api-gateway", "UP");
        services.put("atlas-search-gateway", "UP");
        services.put("atlas-keyword-search", "UP");
        services.put("atlas-crawler-worker", "UP");
        services.put("atlas-index-builder-worker", "UP");

        Map<String, String> datastores = new LinkedHashMap<>();
        datastores.put("postgresql", "UP");
        datastores.put("redis", "UP");
        datastores.put("kafka", "UP");

        ClusterHealthDto clusterHealth = ClusterHealthDto.builder()
                .overallStatus("HEALTHY")
                .timestamp(System.currentTimeMillis())
                .services(services)
                .datastores(datastores)
                .build();

        return Mono.just(ResponseEntity.ok(ApiResponse.success("Cluster health aggregation", clusterHealth)));
    }
}
