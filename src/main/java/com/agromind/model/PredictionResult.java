package com.agromind.model;

public class PredictionResult {
    private Crop crop;
    private double confidenceScore; // e.g., 85.5
    private String explanation;

    public PredictionResult(Crop crop, double confidenceScore, String explanation) {
        this.crop = crop;
        this.confidenceScore = confidenceScore;
        this.explanation = explanation;
    }

    public Crop getCrop() { return crop; }
    public double getConfidenceScore() { return confidenceScore; }
    public String getExplanation() { return explanation; }
}
