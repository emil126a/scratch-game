package org.example.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Probabilities {
    @JsonProperty("standard_symbols")
    private List<CellProbability> standardSymbols;

    @JsonProperty("bonus_symbols")
    private BonusSymbols bonusSymbols;

    public List<CellProbability> getStandardSymbols() { return standardSymbols; }
    public void setStandardSymbols(List<CellProbability> standardSymbols) { this.standardSymbols = standardSymbols; }
    public BonusSymbols getBonusSymbols() { return bonusSymbols; }
    public void setBonusSymbols(BonusSymbols bonusSymbols) { this.bonusSymbols = bonusSymbols; }
}