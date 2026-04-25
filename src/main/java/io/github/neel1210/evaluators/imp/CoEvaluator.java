package io.github.neel1210.evaluators.imp;

import io.github.neel1210.config.ConfigLoader;
import io.github.neel1210.evaluators.Evaluator;

import java.util.List;
import java.util.Map;

public class CoEvaluator implements Evaluator {

    private static final String CO_KEY_PATTERN = "CO %d";

    @Override
    public Map<String, Object> evaluate(Map<String, Object> row) {
        int total = Integer.parseInt(row.getOrDefault("total", "0").toString());
        List<Integer> list = ConfigLoader.getSumPairForCo().randomCombination(total);
        int i = 1;
        for (int val : list) {
            String key = String.format(CO_KEY_PATTERN, i++);
            row.put(key, val);
        }
        row.put("status", "CO processed");
        return row;
    }
}