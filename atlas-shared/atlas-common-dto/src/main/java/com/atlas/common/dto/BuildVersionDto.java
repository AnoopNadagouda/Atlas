package com.atlas.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildVersionDto {
    private String serviceName;
    private String version;
    private String buildTime;
    private String gitCommit;
    private String branch;
    private String javaVersion;
    private String springBootVersion;
}
