package com.atlas.reasoning.service;

import java.util.List;

public interface TaskDecomposer {
    List<String> decomposeGoal(String goalDescription, String reasoningMode);
}
