package com.atlas.indexbuilder.engine.index;

import com.atlas.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Component
public class SegmentReader {

    public SegmentWriter.SegmentMeta readSegmentMeta(String storagePath) throws IOException {
        File metaFile = new File(storagePath, "segment_meta.json");
        if (!metaFile.exists()) {
            throw new IOException("Segment metadata file not found at: " + metaFile.getAbsolutePath());
        }
        String json = Files.readString(metaFile.toPath());
        return JsonUtils.fromJson(json, SegmentWriter.SegmentMeta.class);
    }
}
