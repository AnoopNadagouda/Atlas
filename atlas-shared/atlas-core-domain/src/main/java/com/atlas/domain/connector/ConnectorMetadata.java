package com.atlas.domain.connector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Technical metadata describing a connector adapter.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectorMetadata {
    private String connectorId;
    private String name;
    private String version;
    private String vendor;
    private String category; // CODE_REPOSITORY, WIKI_DOCUMENTATION, CLOUD_STORAGE, CHAT_COLLABORATION, DB_OBJECT
    private String iconUrl;
    private String documentationUrl;

    @Builder.Default
    private List<String> supportedObjectTypes = new ArrayList<>();

    @Builder.Default
    private List<String> permissions = new ArrayList<>();
}
