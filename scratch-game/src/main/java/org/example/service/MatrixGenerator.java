package org.example.service;

import org.example.dto.CellProbability;
import org.example.dto.GameConfig;

import java.util.Map;
import java.util.Random;

public class MatrixGenerator {
    private final GameConfig config;
    private final Random random;

    public MatrixGenerator(GameConfig config) {
        this.config = config;
        this.random = new Random();
    }

    public String[][] generateMatrix() {
        String[][] matrix = new String[config.getRows()][config.getColumns()];
        CellProbability defaultProb = config.getProbabilities().getStandardSymbols().get(0);

        // Fill matrix with standard symbols
        for (int row = 0; row < config.getRows(); row++) {
            for (int col = 0; col < config.getColumns(); col++) {
                CellProbability prob = getProbabilityForCell(row, col, defaultProb);
                matrix[row][col] = selectSymbol(prob.getSymbols());
            }
        }

        // Randomly place one bonus symbol
        int bonusRow = random.nextInt(config.getRows());
        int bonusCol = random.nextInt(config.getColumns());
        Map<String, Integer> bonusProbs = config.getProbabilities().getBonusSymbols().getSymbols();
        String bonusSymbol = selectSymbol(bonusProbs);
        matrix[bonusRow][bonusCol] = bonusSymbol;

        return matrix;
    }

    private CellProbability getProbabilityForCell(int row, int col, CellProbability defaultProb) {
        for (CellProbability prob : config.getProbabilities().getStandardSymbols()) {
            if (prob.getRow() == row && prob.getColumn() == col) {
                return prob;
            }
        }
        return defaultProb;
    }

    private String selectSymbol(Map<String, Integer> symbolProbs) {
        int total = symbolProbs.values().stream().mapToInt(Integer::intValue).sum();
        int rand = random.nextInt(total);
        int current = 0;
        for (Map.Entry<String, Integer> entry : symbolProbs.entrySet()) {
            current += entry.getValue();
            if (rand < current) {
                return entry.getKey();
            }
        }
        return symbolProbs.keySet().iterator().next();
    }
}