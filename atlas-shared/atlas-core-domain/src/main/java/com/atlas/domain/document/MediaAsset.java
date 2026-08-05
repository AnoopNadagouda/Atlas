package com.atlas.domain.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaAsset {
    private String assetId;
    private String mediaType; // IMAGE, AUDIO, VIDEO
    private int width;
    private int height;
    private double durationSeconds;
    private String codec;
    private int bitrate;
    private Map<String, String> exifMetadata;
    private String gpsCoordinates;
}
