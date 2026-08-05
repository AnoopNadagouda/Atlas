package com.atlas.domain.code;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeSymbol {
    private String id;
    private String name;
    private String type; // CLASS, METHOD, FUNCTION, INTERFACE, IMPORT, FIELD
    private String language;
    private String repositoryId;
    private String filePath;
    private int lineNumber;
    private String signature;
    private String docComment;
}
