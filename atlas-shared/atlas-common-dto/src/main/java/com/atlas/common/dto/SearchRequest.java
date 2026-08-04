package com.atlas.common.dto;

import com.atlas.domain.model.enums.QueryIntent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    @NotBlank(message = "Query text must not be blank")
    private String query;

    @Builder.Default
    private QueryIntent intent = QueryIntent.KEYWORD_SEARCH;

    @Builder.Default
    private int page = 0;

    @Builder.Default
    private int size = 10;

    private String domainFilter;
}
