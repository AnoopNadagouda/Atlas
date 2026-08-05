package com.atlas.keywordsearch.plugin;

import com.atlas.domain.plugin.PluginDescriptor;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class PluginManager {

    private final Map<String, PluginDescriptor> plugins = new ConcurrentHashMap<>();

    @PostConstruct
    public void initSeedPlugins() {
        log.info("Initializing Enterprise Plugin SDK & Lifecycle Manager...");

        PluginDescriptor markdownParser = PluginDescriptor.builder()
                .pluginId("plugin-markdown-parser")
                .name("Markdown Document Parser")
                .version("1.2.0")
                .author("Atlas Search Core Team")
                .description("Language-aware markdown AST and frontmatter parser plugin")
                .mainClass("com.atlas.plugin.parser.MarkdownParserPlugin")
                .status("ENABLED")
                .permissions(List.of("FILESYSTEM_READ", "PARSER_REGISTER"))
                .capabilities(List.of("DOCUMENT_PARSER", "MARKDOWN_AST"))
                .build();

        PluginDescriptor duckDuckGoProvider = PluginDescriptor.builder()
                .pluginId("plugin-ddg-search")
                .name("DuckDuckGo External Web Search Provider")
                .version("2.0.1")
                .author("Community Plugins")
                .description("External web search federation provider plugin via DuckDuckGo API")
                .mainClass("com.atlas.plugin.search.DuckDuckGoSearchPlugin")
                .status("ENABLED")
                .permissions(List.of("NETWORK_CONNECT", "SEARCH_FEDERATION"))
                .capabilities(List.of("EXTERNAL_RETRIEVAL", "FEDERATION"))
                .build();

        registerPlugin(markdownParser);
        registerPlugin(duckDuckGoProvider);
    }

    public synchronized void registerPlugin(PluginDescriptor plugin) {
        if (plugin == null || plugin.getPluginId() == null) return;
        plugins.put(plugin.getPluginId(), plugin);
        log.info("[PluginManager] Loaded Plugin '{}' (Version: {}, Status: {})", plugin.getName(), plugin.getVersion(), plugin.getStatus());
    }

    public List<PluginDescriptor> getAllPlugins() {
        return new ArrayList<>(plugins.values());
    }

    public PluginDescriptor getPluginById(String pluginId) {
        return pluginId != null ? plugins.get(pluginId) : null;
    }

    public synchronized void enablePlugin(String pluginId) {
        PluginDescriptor plugin = plugins.get(pluginId);
        if (plugin != null) {
            plugin.setStatus("ENABLED");
            log.info("[PluginManager] Enabled Plugin '{}'", pluginId);
        }
    }

    public synchronized void disablePlugin(String pluginId) {
        PluginDescriptor plugin = plugins.get(pluginId);
        if (plugin != null) {
            plugin.setStatus("DISABLED");
            log.info("[PluginManager] Disabled Plugin '{}'", pluginId);
        }
    }

    public synchronized void uninstallPlugin(String pluginId) {
        plugins.remove(pluginId);
        log.info("[PluginManager] Uninstalled Plugin '{}'", pluginId);
    }
}
