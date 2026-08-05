package com.atlas.keywordsearch.code;

import com.atlas.domain.code.CodeRepository;
import com.atlas.domain.code.CodeSymbol;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeIndexBuilder {

    private final Map<String, CodeRepository> repositoryIndex = new ConcurrentHashMap<>();
    private final Map<String, List<CodeSymbol>> symbolIndex = new ConcurrentHashMap<>();

    private final AstSymbolExtractor symbolExtractor;

    @PostConstruct
    public void initSeedRepositories() {
        log.info("Initializing Code Index Builder with seed Git repositories...");

        String repoId = "repo-atlas";
        CodeRepository repo = CodeRepository.builder()
                .id(repoId)
                .name("AnoopNadagouda/Atlas")
                .url("https://github.com/AnoopNadagouda/Atlas")
                .defaultBranch("main")
                .commitHash("2d7326d")
                .fileCount(150)
                .symbolCount(450)
                .languageDistribution(Map.of("JAVA", 75.0, "TYPESCRIPT", 20.0, "PYTHON", 5.0))
                .build();

        indexRepository(repo);

        String javaCode = "public class HybridSearchService { public SearchResult executeSearch() {} }";
        List<CodeSymbol> symbols = symbolExtractor.extractSymbols(javaCode, "JAVA", repoId, "com/atlas/keywordsearch/hybrid/HybridSearchService.java");
        for (CodeSymbol sym : symbols) {
            indexSymbol(sym);
        }
    }

    public void indexRepository(CodeRepository repo) {
        if (repo == null || repo.getId() == null) return;
        repositoryIndex.put(repo.getId(), repo);
        log.info("[CodeIndexBuilder] Indexed Repository '{}' ({})", repo.getName(), repo.getUrl());
    }

    public void indexSymbol(CodeSymbol symbol) {
        if (symbol == null || symbol.getName() == null) return;
        symbolIndex.computeIfAbsent(symbol.getName().toLowerCase(), k -> new ArrayList<>()).add(symbol);
        log.info("[CodeIndexBuilder] Indexed Code Symbol '{}' ({}) in {}", symbol.getName(), symbol.getType(), symbol.getFilePath());
    }

    public List<CodeSymbol> searchSymbols(String query) {
        if (query == null || query.isBlank()) return Collections.emptyList();
        String lower = query.toLowerCase().trim();
        return symbolIndex.entrySet().stream()
                .filter(e -> e.getKey().contains(lower))
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList());
    }

    public List<CodeRepository> getAllRepositories() {
        return new ArrayList<>(repositoryIndex.values());
    }
}
