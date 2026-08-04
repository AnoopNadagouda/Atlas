package com.atlas.keywordsearch.pipeline;

public interface SearchPipelineStage {
    void execute(SearchPipelineContext context);
    String getStageName();
}
