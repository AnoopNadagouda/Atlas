package com.atlas.keywordsearch.plugin;

import com.atlas.domain.plugin.PluginDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PluginManagerTest {

    private PluginManager pluginManager;

    @BeforeEach
    void setUp() {
        pluginManager = new PluginManager();
        pluginManager.initSeedPlugins();
    }

    @Test
    void testGetAllPlugins() {
        List<PluginDescriptor> list = pluginManager.getAllPlugins();
        assertNotNull(list);
        assertTrue(list.size() >= 2);
    }

    @Test
    void testEnableAndDisablePlugin() {
        pluginManager.disablePlugin("plugin-markdown-parser");
        assertEquals("DISABLED", pluginManager.getPluginById("plugin-markdown-parser").getStatus());

        pluginManager.enablePlugin("plugin-markdown-parser");
        assertEquals("ENABLED", pluginManager.getPluginById("plugin-markdown-parser").getStatus());
    }
}
