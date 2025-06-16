package org.example.service;

import org.example.dto.GameConfig;
import org.example.dto.GameResult;
import org.example.dto.Symbol;
import org.example.dto.WinCombination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameEvaluator {
    private final GameConfig config;
    private final double betAmount;

    public GameEvaluator(GameConfig config, double betAmount) {
        this.config = config;
        this.betAmount = betAmount;
    }

    public GameResult evaluate(String[][] matrix) {
        GameResult result = new GameResult();
        result.setMatrix(matrix);
        result.setAppliedWinningCombinations(new HashMap<>());

        // Count standard symbols
        Map<String, Integer> symbolCounts = new HashMap<>();
        String bonusSymbol = null;
        for (String[] row : matrix) {
            for (String symbol : row) {
                if (config.getSymbols().containsKey(symbol) && config.getSymbols().get(symbol).getType().equals("bonus")) {
                    bonusSymbol = symbol;
                } else {
                    symbolCounts.merge(symbol, 1, Integer::sum);
                }
            }
        }

        // Check win combinations
        Map<String, List<String>> appliedCombinations = new HashMap<>();
        Map<String, Double> symbolRewards = new HashMap<>();
        for (Map.Entry<String, WinCombination> entry : config.getWinCombinations().entrySet()) {
            WinCombination win = entry.getValue();
            String winName = entry.getKey();

            if (win.getWhen().equals("same_symbols")) {
                for (Map.Entry<String, Integer> symbolEntry : symbolCounts.entrySet()) {
                    String symbol = symbolEntry.getKey();
                    int count = symbolEntry.getValue();
                    if (count >= win.getCount()) {
                        appliedCombinations.computeIfAbsent(symbol, k -> new ArrayList<>()).add(winName);
                        double reward = betAmount * config.getSymbols().get(symbol).getRewardMultiplier() * win.getRewardMultiplier();
                        symbolRewards.merge(symbol, reward, Double::sum);
                    }
                }
            } else if (win.getWhen().equals("linear_symbols")) {
                for (List<String> area : win.getCoveredAreas()) {
                    String firstSymbol = null;
                    boolean same = true;
                    for (String pos : area) {
                        String[] coords = pos.split(":");
                        int row = Integer.parseInt(coords[0]);
                        int col = Integer.parseInt(coords[1]);
                        String symbol = matrix[row][col]; // Fixed: Changed 'lamp' to 'symbol'
                        if (config.getSymbols().get(symbol).getType().equals("bonus")) {
                            continue;
                        }
                        if (firstSymbol == null) {
                            firstSymbol = symbol;
                        } else if (!firstSymbol.equals(symbol)) {
                            same = false;
                            break;
                        }
                    }
                    if (same && firstSymbol != null) {
                        appliedCombinations.computeIfAbsent(firstSymbol, k -> new ArrayList<>()).add(winName);
                        double reward = betAmount * config.getSymbols().get(firstSymbol).getRewardMultiplier() * win.getRewardMultiplier();
                        symbolRewards.merge(firstSymbol, reward, Double::sum);
                    }
                }
            }
        }

        // Calculate total reward
        double totalReward = symbolRewards.values().stream().mapToDouble(Double::doubleValue).sum();

        // Apply bonus symbol if reward exists
        if (totalReward > 0 && bonusSymbol != null) {
            Symbol bonus = config.getSymbols().get(bonusSymbol);
            if (bonus.getImpact().equals("multiply_reward")) {
                totalReward *= bonus.getRewardMultiplier();
            } else if (bonus.getImpact().equals("extra_bonus")) {
                totalReward += bonus.getExtra();
            }
            if (!bonus.getImpact().equals("miss")) {
                result.setAppliedBonusSymbol(bonusSymbol);
            }
        }

        result.setReward(totalReward);
        result.setAppliedWinningCombinations(appliedCombinations);
        return result;
    }
}