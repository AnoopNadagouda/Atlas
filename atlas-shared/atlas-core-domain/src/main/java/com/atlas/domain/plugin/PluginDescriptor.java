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
public class PluginDescriptor {
    private String pluginId;
    private String name;
    private String version;
    private String author;
    private String description;
    private String mainClass;
    private String status; // INSTALLED, ENABLED, DISABLED
    private List<String> permissions;
    private List<String> capabilities;
}
