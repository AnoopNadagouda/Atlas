package com.atlas.keywordsearch.plugin;

import com.atlas.domain.plugin.PluginMetadata;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class PluginMarketplaceService {

    private final Map<String, PluginMetadata> catalog = new ConcurrentHashMap<>();

    @PostConstruct
    public void initCatalog() {
        log.info("Initializing Plugin Marketplace Catalog...");

        PluginMetadata geminiPlugin = PluginMetadata.builder()
                .pluginId("plugin-gemini-llm")
                .name("Google Gemini 1.5 Pro Provider")
                .latestVersion("3.0.0")
                .publisher("Google AI Team")
                .category("AI_MODEL")
                .license("Apache-2.0")
                .rating(4.9)
                .downloads(14200)
                .requiredPermissions(List.of("NETWORK_OUTBOUND", "LLM_INFERENCE"))
                .build();

        PluginMetadata pdfParserPlugin = PluginMetadata.builder()
                .pluginId("plugin-pdf-parser")
                .name("PDF & OCR Document Parser")
                .latestVersion("2.1.4")
                .publisher("Atlas Community")
                .category("PARSER")
                .license("MIT")
                .rating(4.8)
                .downloads(8900)
                .requiredPermissions(List.of("FILESYSTEM_READ"))
                .build();

        catalog.put(geminiPlugin.getPluginId(), geminiPlugin);
        catalog.put(pdfParserPlugin.getPluginId(), pdfParserPlugin);
    }

    public List<PluginMetadata> getMarketplaceCatalog() {
        return new ArrayList<>(catalog.values());
    }

    public List<String> checkForUpdates() {
        return List.of("plugin-markdown-parser -> v1.3.0 available", "plugin-ddg-search -> v2.1.0 available");
    }
}
