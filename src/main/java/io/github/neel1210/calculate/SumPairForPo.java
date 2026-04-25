package io.github.neel1210.calculate;

import io.github.neel1210.config.AppConfig;
import io.github.neel1210.config.ConfigLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;

public class SumPairForPo {
    private final int MIN, MAX;
    private final int MIN_SUM;
    private final int MAX_SUM;
    private final int[][][] pairs;
    private final SplittableRandom rng = new SplittableRandom();
    private final AppConfig appConfig;

    public SumPairForPo() {
        this.appConfig = ConfigLoader.getAppConfig();
        this.MIN = appConfig.getReader().getRange().getPO().getMin();
        this.MAX = appConfig.getReader().getRange().getPO().getMax();
        MAX_SUM = this.MAX * 2;
        MIN_SUM = this.MIN * 2;
        pairs = new int[MAX_SUM + 1][][];
        this.init();
    }

    public static void main(String[] args) {
        SumPairForPo picker = ConfigLoader.getSumPairForPo();
        for (int i = 0; i < 100; i++) {
            int target = ThreadLocalRandom.current().nextInt(14, 20 + 1);
            int[] res = picker.randomPair(target);
            System.out.println("Target : " + target + " -> X: " + res[0] + ", Y: " + res[1]);
        }
    }

    private void init() {
        @SuppressWarnings("unchecked") List<int[]>[] temp = new List[MAX_SUM + 1];
        for (int i = MIN_SUM; i <= MAX_SUM; i++) {
            temp[i] = new ArrayList<>();
        }
        for (int x = MIN; x <= MAX; x++) {
            for (int y = MIN; y <= MAX; y++) {
                temp[x + y].add(new int[]{x, y});
            }
        }
        for (int s = MIN_SUM; s <= MAX_SUM; s++) {
            pairs[s] = temp[s].toArray(new int[0][]);
        }
    }

    public int[] randomPair(int sum) {
        if (sum == 0) return new int[]{0, 0};
        sum = getAvgSumForPair(sum);
        if (sum < MIN_SUM || sum > MAX_SUM) return null;
        int[][] p = pairs[sum];
        if (p.length == 0) return null;
        return p[rng.nextInt(p.length)];
    }

    private int getAvgSumForPair(int value) {
        return switch (value) {
            case 10 -> rng.nextInt(19, 21);
            case 9 -> rng.nextInt(17, 19);
            case 8 -> rng.nextInt(15, 17);
            case 7 -> rng.nextInt(13, 15);
            case 6 -> rng.nextInt(11, 13);
            default -> rng.nextInt(MIN_SUM, MAX_SUM);
        };
    }
}