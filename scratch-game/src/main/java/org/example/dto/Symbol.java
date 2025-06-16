package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Symbol {
    private String type;

    @JsonProperty("reward_multiplier")
    private Double rewardMultiplier;
    private Double extra;
    private String impact;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Double getRewardMultiplier() { return rewardMultiplier; }
    public void setRewardMultiplier(Double rewardMultiplier) { this.rewardMultiplier = rewardMultiplier; }
    public Double getExtra() { return extra; }
    public void setExtra(Double extra) { this.extra = extra; }
    public String getImpact() { return impact; }
    public void setImpact(String impact) { this.impact = impact; }
}