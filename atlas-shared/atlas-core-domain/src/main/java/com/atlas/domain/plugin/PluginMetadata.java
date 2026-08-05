package com.atlas.domain.plugin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PluginMetadata {
    private String pluginId;
    private String name;
    private String latestVersion;
    private String publisher;
    private String category; // PARSER, RANKING, SEARCH_PROVIDER, AI_MODEL
    private String license;
    private double rating;
    private int downloads;
    private List<String> requiredPermissions;
}
