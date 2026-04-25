package io.github.neel1210.evaluators.imp;

import io.github.neel1210.evaluators.Evaluator;
import io.github.neel1210.evaluators.EvaluatorFactory;

import java.util.Map;

public class BothEvaluator implements Evaluator {

    @Override
    public Map<String, Object> evaluate(Map<String, Object> row) {
        Evaluator evaluator = EvaluatorFactory.get("CO");
        row = evaluator.evaluate(row);
        evaluator = EvaluatorFactory.get("PO");
        row = evaluator.evaluate(row);
        row.put("status", "CO + PO processed");
        return row;
    }
}