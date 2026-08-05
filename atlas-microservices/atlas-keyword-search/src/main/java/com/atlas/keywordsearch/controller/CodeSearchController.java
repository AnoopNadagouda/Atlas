package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.domain.code.CodeRepository;
import com.atlas.domain.code.CodeSymbol;
import com.atlas.keywordsearch.code.AiCodeCopilotService;
import com.atlas.keywordsearch.code.CodeIndexBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v11/code")
@RequiredArgsConstructor
public class CodeSearchController {

    private final CodeIndexBuilder indexBuilder;
    private final AiCodeCopilotService codeCopilotService;

    @PostMapping("/index")
    public ResponseEntity<ApiResponse<CodeRepository>> indexRepository(
            @RequestParam String name,
            @RequestParam String url,
            @RequestParam(defaultValue = "main") String branch) {
        log.info("Triggering indexing for Git repository: '{}' ({})", name, url);
        CodeRepository repo = CodeRepository.builder()
                .id("repo-" + name.replace("/", "-").toLowerCase())
                .name(name)
                .url(url)
                .defaultBranch(branch)
                .commitHash("HEAD")
                .fileCount(120)
                .symbolCount(380)
                .languageDistribution(Map.of("JAVA", 80.0, "TYPESCRIPT", 20.0))
                .build();
        indexBuilder.indexRepository(repo);
        return ResponseEntity.ok(ApiResponse.success(repo));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CodeSymbol>>> searchSymbols(@RequestParam String query) {
        log.info("Searching code symbols for query '{}'", query);
        return ResponseEntity.ok(ApiResponse.success(indexBuilder.searchSymbols(query)));
    }

    @GetMapping("/symbol/{name}")
    public ResponseEntity<ApiResponse<String>> explainSymbol(@PathVariable String name) {
        log.info("Explaining code symbol using AI Code Copilot: '{}'", name);
        String explanation = codeCopilotService.explainCodeSymbol(name);
        return ResponseEntity.ok(ApiResponse.success(explanation));
    }

    @GetMapping("/repository/{id}")
    public ResponseEntity<ApiResponse<CodeRepository>> getRepository(@PathVariable String id) {
        log.info("Fetching repository details for ID: '{}'", id);
        CodeRepository repo = indexBuilder.getAllRepositories().stream()
                .filter(r -> r.getId().equalsIgnoreCase(id))
                .findFirst().orElse(null);
        return ResponseEntity.ok(ApiResponse.success(repo));
    }

    @GetMapping("/dependencies")
    public ResponseEntity<ApiResponse<List<String>>> getDependencies() {
        log.info("Fetching code dependency graph");
        List<String> deps = List.of(
                "org.springframework.boot:spring-boot-starter-web",
                "org.apache.kafka:kafka-clients",
                "org.postgresql:postgresql",
                "org.springframework.boot:spring-boot-starter-data-redis"
        );
        return ResponseEntity.ok(ApiResponse.success(deps));
    }

    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCodeStatistics() {
        log.info("Fetching code search statistics");
        Map<String, Object> stats = Map.of(
                "totalRepositories", indexBuilder.getAllRepositories().size(),
                "totalFilesIndexed", 150,
                "totalSymbolsIndexed", 450,
                "supportedLanguages", List.of("JAVA", "PYTHON", "JAVASCRIPT", "TYPESCRIPT")
        );
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
}
