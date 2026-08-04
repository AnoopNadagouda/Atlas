package com.atlas.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClusterHealthDto {
    private String overallStatus;
    private long timestamp;
    private Map<String, String> services;
    private Map<String, String> datastores;
}
