package io.github.neel1210.evaluators;

import java.util.Map;

public interface Evaluator {
    Map<String, Object> evaluate(Map<String, Object> row);
}
