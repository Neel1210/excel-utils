package io.github.neel1210.bussinessLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

public class SumPair {

    static final int MIN = 7, MAX = 10;
    static final int MIN_SUM = 2 * MIN;
    static final int MAX_SUM = 2 * MAX;

    private final int[][][] pairs = new int[MAX_SUM + 1][][];

    public SumPair() {

        @SuppressWarnings("unchecked")
        List<int[]>[] temp = new List[MAX_SUM + 1];

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

    public int[] randomPair(int sum, SplittableRandom rng) {
        if (sum < MIN_SUM || sum > MAX_SUM) return null;

        int[][] p = pairs[sum];
        if (p.length == 0) return null;

        return p[rng.nextInt(p.length)];
    }

    public static void main(String[] args) {
        SumPair picker = new SumPair();
        SplittableRandom rng = new SplittableRandom();

        for (int i = 0; i < 100; i++) {
            int[] res = picker.randomPair(16, rng);
            System.out.println("X: " + res[0] + ", Y: " + res[1]);
        }
    }
}