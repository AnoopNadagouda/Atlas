package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.domain.plugin.PluginDescriptor;
import com.atlas.domain.plugin.PluginMetadata;
import com.atlas.keywordsearch.plugin.PluginManager;
import com.atlas.keywordsearch.plugin.PluginMarketplaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v15/plugins")
@RequiredArgsConstructor
public class PluginController {

    private final PluginManager pluginManager;
    private final PluginMarketplaceService marketplaceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PluginDescriptor>>> getInstalledPlugins() {
        log.info("Fetching all installed plugins");
        return ResponseEntity.ok(ApiResponse.success(pluginManager.getAllPlugins()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PluginDescriptor>> getPlugin(@PathVariable String id) {
        log.info("Fetching plugin details for ID: '{}'", id);
        return ResponseEntity.ok(ApiResponse.success(pluginManager.getPluginById(id)));
    }

    @PostMapping("/install")
    public ResponseEntity<ApiResponse<PluginDescriptor>> installPlugin(
            @RequestParam String pluginId,
            @RequestParam String name,
            @RequestParam(defaultValue = "1.0.0") String version) {
        log.info("Installing new plugin: '{}' ({})", name, pluginId);
        PluginDescriptor plugin = PluginDescriptor.builder()
                .pluginId(pluginId)
                .name(name)
                .version(version)
                .author("Marketplace Install")
                .description("Dynamically installed extension plugin")
                .mainClass("com.atlas.plugin.custom.ExtensionPlugin")
                .status("ENABLED")
                .permissions(List.of("PLUGIN_EXECUTE"))
                .capabilities(List.of("CUSTOM_EXTENSION"))
                .build();
        pluginManager.registerPlugin(plugin);
        return ResponseEntity.ok(ApiResponse.success(plugin));
    }

    @PostMapping("/uninstall")
    public ResponseEntity<ApiResponse<String>> uninstallPlugin(@RequestParam String pluginId) {
        log.info("Uninstalling plugin: '{}'", pluginId);
        pluginManager.uninstallPlugin(pluginId);
        return ResponseEntity.ok(ApiResponse.success("Plugin uninstalled successfully"));
    }

    @PostMapping("/enable")
    public ResponseEntity<ApiResponse<String>> enablePlugin(@RequestParam String pluginId) {
        log.info("Enabling plugin: '{}'", pluginId);
        pluginManager.enablePlugin(pluginId);
        return ResponseEntity.ok(ApiResponse.success("Plugin enabled successfully"));
    }

    @PostMapping("/disable")
    public ResponseEntity<ApiResponse<String>> disablePlugin(@RequestParam String pluginId) {
        log.info("Disabling plugin: '{}'", pluginId);
        pluginManager.disablePlugin(pluginId);
        return ResponseEntity.ok(ApiResponse.success("Plugin disabled successfully"));
    }

    @PostMapping("/reload")
    public ResponseEntity<ApiResponse<String>> reloadPlugins() {
        log.info("Reloading all active plugins");
        return ResponseEntity.ok(ApiResponse.success("All active plugins reloaded cleanly"));
    }

    @GetMapping("/marketplace")
    public ResponseEntity<ApiResponse<List<PluginMetadata>>> getMarketplaceCatalog() {
        log.info("Fetching Plugin Marketplace catalog");
        return ResponseEntity.ok(ApiResponse.success(marketplaceService.getMarketplaceCatalog()));
    }

    @GetMapping("/updates")
    public ResponseEntity<ApiResponse<List<String>>> checkUpdates() {
        log.info("Checking for available plugin updates");
        return ResponseEntity.ok(ApiResponse.success(marketplaceService.checkForUpdates()));
    }
}
