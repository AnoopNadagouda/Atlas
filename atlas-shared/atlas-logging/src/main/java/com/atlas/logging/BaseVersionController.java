package com.atlas.logging;

import com.atlas.common.dto.ApiResponse;
import com.atlas.common.dto.BuildVersionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class BaseVersionController {

    @Value("${spring.application.name:atlas-service}")
    private String serviceName;

    @Value("${atlas.build.version:1.0.0-SNAPSHOT}")
    private String version;

    @GetMapping("/version")
    public ResponseEntity<ApiResponse<BuildVersionDto>> getVersion() {
        BuildVersionDto dto = BuildVersionDto.builder()
                .serviceName(serviceName)
                .version(version)
                .buildTime(Instant.now().toString())
                .gitCommit("git-" + Integer.toHexString(serviceName.hashCode()).substring(0, 7))
                .branch("main")
                .javaVersion(System.getProperty("java.version"))
                .springBootVersion("3.2.5")
                .build();

        return ResponseEntity.ok(ApiResponse.success(dto));
    }
}
