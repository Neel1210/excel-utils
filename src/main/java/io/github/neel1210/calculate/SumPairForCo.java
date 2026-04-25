package io.github.neel1210.calculate;

import io.github.neel1210.config.AppConfig;
import io.github.neel1210.config.ConfigLoader;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SumPairForCo {
    private final int MIN;
    private final int MAX;
    private final int DIGITS;
    private final int TARGET_MIN;
    private final int TARGET_MAX;
    private final Map<Integer, List<List<Integer>>> SUM_MAP;
    private final AppConfig appConfig;

    public SumPairForCo() {
        this.appConfig = ConfigLoader.getAppConfig();
        this.MIN = appConfig.getReader().getRange().getCO().getMin();
        this.MAX = appConfig.getReader().getRange().getCO().getMax();
        this.DIGITS = appConfig.getReader().getRange().getCO().getDigit();
        this.TARGET_MIN = appConfig.getReader().getRange().getCO().getMin();
        this.TARGET_MAX = appConfig.getReader().getRange().getCO().getTargetMax();
        this.SUM_MAP = buildMap();
    }

    public static void main(String[] args) {
        AppConfig.MinMax minMax = ConfigLoader.getAppConfig().getReader().getRange().getCO();
        SumPairForCo obj = ConfigLoader.getSumPairForCo();
        for (int i = 0; i < 100; i++) {
            int target = ThreadLocalRandom.current().nextInt(minMax.getTargetMin(), minMax.getTargetMax() + 1);
            System.out.println("Sum " + target + " -> " + obj.randomCombination(target)
            );
        }
    }

    private Map<Integer, List<List<Integer>>> buildMap() {
        Map<Integer, List<List<Integer>>> map = new HashMap<>();
        for (int sum = TARGET_MIN; sum <= TARGET_MAX; sum++) {
            map.put(sum, new ArrayList<>());
        }
        generate(0, 0, new int[DIGITS], map);
        return Collections.unmodifiableMap(map);
    }

    private void generate(int index, int currentSum, int[] arr, Map<Integer, List<List<Integer>>> map) {
        if (index == DIGITS) {
            if (currentSum >= TARGET_MIN && currentSum <= TARGET_MAX) {
                List<Integer> result = new ArrayList<>(DIGITS);
                for (int val : arr) {
                    result.add(val);
                }
                map.get(currentSum).add(result);
            }
            return;
        }

        int remaining = DIGITS - index - 1;
        for (int val = MIN; val <= MAX; val++) {
            int newSum = currentSum + val;
            int minPossible = newSum + (remaining * MIN);
            int maxPossible = newSum + (remaining * MAX);
            if (maxPossible < TARGET_MIN) continue;
            if (minPossible > TARGET_MAX) continue;
            arr[index] = val;
            generate(index + 1, newSum, arr, map);
        }
    }

    public List<Integer> randomCombination(int sum) {
        List<List<Integer>> list = SUM_MAP.get(sum);
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        int index = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(index);
    }
}