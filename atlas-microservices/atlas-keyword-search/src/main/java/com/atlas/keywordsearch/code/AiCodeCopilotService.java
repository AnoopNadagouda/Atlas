package com.atlas.keywordsearch.code;

import com.atlas.domain.code.CodeSymbol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiCodeCopilotService {

    private final CodeIndexBuilder codeIndexBuilder;

    public String explainCodeSymbol(String symbolName) {
        List<CodeSymbol> symbols = codeIndexBuilder.searchSymbols(symbolName);
        if (symbols.isEmpty()) {
            return "No matching symbol found in indexed repositories for query '" + symbolName + "'.";
        }
        CodeSymbol sym = symbols.get(0);
        return String.format("Grounded AI Code Explanation: Symbol '%s' is a %s defined in file '%s' (%s). Signature: %s.",
                sym.getName(), sym.getType(), sym.getFilePath(), sym.getLanguage(), sym.getSignature());
    }
}
