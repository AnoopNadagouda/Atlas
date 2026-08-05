package com.atlas.keywordsearch.plugin;

import com.atlas.domain.plugin.PluginMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PluginMarketplaceServiceTest {

    private PluginMarketplaceService marketplaceService;

    @BeforeEach
    void setUp() {
        marketplaceService = new PluginMarketplaceService();
        marketplaceService.initCatalog();
    }

    @Test
    void testGetMarketplaceCatalog() {
        List<PluginMetadata> catalog = marketplaceService.getMarketplaceCatalog();
        assertNotNull(catalog);
        assertTrue(catalog.size() >= 2);
    }
}
