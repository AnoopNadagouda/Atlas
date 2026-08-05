package com.atlas.keywordsearch.code;

import com.atlas.domain.code.CodeSymbol;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AstSymbolExtractor {

    private static final Pattern JAVA_CLASS = Pattern.compile("public\\s+(?:final\\s+)?class\\s+([A-Za-z0-9_]+)");
    private static final Pattern JAVA_METHOD = Pattern.compile("public\\s+([A-Za-z0-9_<>]+)\\s+([A-Za-z0-9_]+)\\s*\\(");
    private static final Pattern PYTHON_DEF = Pattern.compile("def\\s+([A-Za-z0-9_]+)\\s*\\(");

    public List<CodeSymbol> extractSymbols(String sourceCode, String language, String repoId, String filePath) {
        List<CodeSymbol> symbols = new ArrayList<>();
        if (sourceCode == null || sourceCode.isBlank()) return symbols;

        if ("JAVA".equalsIgnoreCase(language)) {
            Matcher classMatcher = JAVA_CLASS.matcher(sourceCode);
            while (classMatcher.find()) {
                symbols.add(CodeSymbol.builder()
                        .id(UUID.randomUUID().toString())
                        .name(classMatcher.group(1))
                        .type("CLASS")
                        .language("JAVA")
                        .repositoryId(repoId)
                        .filePath(filePath)
                        .lineNumber(1)
                        .signature("public class " + classMatcher.group(1))
                        .docComment("Class definition")
                        .build());
            }

            Matcher methodMatcher = JAVA_METHOD.matcher(sourceCode);
            while (methodMatcher.find()) {
                symbols.add(CodeSymbol.builder()
                        .id(UUID.randomUUID().toString())
                        .name(methodMatcher.group(2))
                        .type("METHOD")
                        .language("JAVA")
                        .repositoryId(repoId)
                        .filePath(filePath)
                        .lineNumber(10)
                        .signature("public " + methodMatcher.group(1) + " " + methodMatcher.group(2) + "(...)")
                        .docComment("Method definition")
                        .build());
            }
        } else if ("PYTHON".equalsIgnoreCase(language)) {
            Matcher pyMatcher = PYTHON_DEF.matcher(sourceCode);
            while (pyMatcher.find()) {
                symbols.add(CodeSymbol.builder()
                        .id(UUID.randomUUID().toString())
                        .name(pyMatcher.group(1))
                        .type("FUNCTION")
                        .language("PYTHON")
                        .repositoryId(repoId)
                        .filePath(filePath)
                        .lineNumber(5)
                        .signature("def " + pyMatcher.group(1) + "(...)")
                        .docComment("Python function")
                        .build());
            }
        }
        return symbols;
    }
}
