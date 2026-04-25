package io.github.neel1210.evaluators.imp;

import io.github.neel1210.config.ConfigLoader;
import io.github.neel1210.evaluators.Evaluator;

import java.util.List;
import java.util.Map;


public class PoEvaluator implements Evaluator {

    private final String CO_KEY_PATTERN = "CO %d";
    private final String PO_KEY_PATTERN = "P%d";

    @Override
    public Map<String, Object> evaluate(Map<String, Object> row) {

        int digit = ConfigLoader.getAppConfig().getReader().getRange().getCO().getDigit();
        for (int i = 1; i <= digit; i++) {
            String coKey = String.format(CO_KEY_PATTERN, i);
            String value = row.getOrDefault(coKey, "0").toString().trim();
            int total = Integer.parseInt(value.isEmpty() ? "0" : value);
            int[] pair = ConfigLoader.getSumPairForPo().randomPair(total);
            List<String> keys = getKeys(i);
            row.put(keys.get(0), pair[0]);
            row.put(keys.get(1), pair[1]);
        }
        row.put("status", "PO processed");
        return row;
    }

    private List<String> getKeys(int i) {
        return List.of(
                String.format(PO_KEY_PATTERN, (i * 2) - 1), String.format(PO_KEY_PATTERN, (i * 2)));
    }
}