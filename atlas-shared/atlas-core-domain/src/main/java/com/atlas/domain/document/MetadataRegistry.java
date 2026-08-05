package com.atlas.domain.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unified metadata registry holding technical EXIF/IPTC/XMP properties,
 * camera info, GPS data, file hashes, and audio/video technical specifications.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetadataRegistry {
    @Builder.Default
    private Map<String, String> exifMetadata = new HashMap<>();

    @Builder.Default
    private Map<String, String> iptcMetadata = new HashMap<>();

    @Builder.Default
    private Map<String, String> xmpMetadata = new HashMap<>();

    private int width;
    private int height;
    private String colorDepth;
    private List<String> dominantColors;
    private String md5Hash;
    private String sha256Hash;

    private String cameraMake;
    private String cameraModel;
    private String lensInfo;

    private String gpsLatitude;
    private String gpsLongitude;
    private Double gpsAltitude;

    // Audio & Video Specs
    private Double durationSeconds;
    private String codec;
    private Integer bitrate;
    private Double frameRate;
    private String language;
    private List<String> subtitles;
    
    @Builder.Default
    private Map<String, Object> customAttributes = new HashMap<>();
}
