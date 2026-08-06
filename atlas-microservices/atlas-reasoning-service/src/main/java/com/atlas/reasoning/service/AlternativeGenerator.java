package com.atlas.reasoning.service;

import java.util.List;

public interface AlternativeGenerator {
    List<String> generateAlternatives(String goalId, String currentContext);
}
