package com.saikat.cloudpulse.utils;

import com.saikat.cloudpulse.models.City;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuzzySearchUtil {
    public static List<City> getFuzzyCityNames(List<City> cities, String input, int limit) {
        Map<City, Integer> distanceMap = new HashMap<>();

        for (City city : cities) {
            int distance = levenshteinDistance(input.toLowerCase(), city.getName().toLowerCase());
            distanceMap.put(city, distance);
        }

        return distanceMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).limit(limit).map(Map.Entry::getKey)
                .toList();
    }

    public static List<City> getFuzzyCityNames(List<City> cities, String input){
        return getFuzzyCityNames(cities, input, 6);
    }

    // Levenshtein distance (edit distance)
    private static int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1])
                    );
                }
            }
        }

        return dp[a.length()][b.length()];
    }
}
