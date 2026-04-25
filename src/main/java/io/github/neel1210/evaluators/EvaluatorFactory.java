package io.github.neel1210.evaluators;

import io.github.neel1210.evaluators.imp.BothEvaluator;
import io.github.neel1210.evaluators.imp.CoEvaluator;
import io.github.neel1210.evaluators.imp.PoEvaluator;

public class EvaluatorFactory {

    public static Evaluator get(String functionality) {

        return switch (functionality.toUpperCase()) {

            case "CO" -> new CoEvaluator();
            case "PO" -> new PoEvaluator();
            case "BOTH" -> new BothEvaluator();

            default -> throw new IllegalArgumentException(
                    "Invalid functionality value"
            );
        };
    }
}