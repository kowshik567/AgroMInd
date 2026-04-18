package com.agromind.engine;

import com.agromind.model.Crop;
import com.agromind.model.PredictionResult;
import com.agromind.model.WeatherData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecommendationEngine {
    private List<Crop> cropDatabase;

    public RecommendationEngine() {
        cropDatabase = new ArrayList<>();
        // Mocking a database of crops
        cropDatabase.add(new Crop("Wheat", "Loamy", "Winter", "15-20°C", "Moderate", "/images/wheat.jpg"));
        cropDatabase.add(new Crop("Rice", "Clay", "Monsoon", "20-30°C", "High", "/images/rice.jpg"));
        cropDatabase.add(new Crop("Sugarcane", "Alluvial", "Summer", "25-35°C", "High", "/images/sugarcane.jpg"));
        cropDatabase.add(new Crop("Cotton", "Black", "Summer", "20-30°C", "Moderate", "/images/cotton.jpg"));
        cropDatabase.add(new Crop("Maize", "Loamy", "Monsoon", "21-27°C", "Moderate", "/images/maize.jpg"));
        cropDatabase.add(new Crop("Mustard", "Sandy", "Winter", "10-25°C", "Low", "/images/mustard.png"));
        cropDatabase.add(new Crop("Potato", "Sandy Loam", "Winter", "15-20°C", "Moderate", "/images/potato.png"));
        cropDatabase.add(new Crop("Tomato", "Loamy", "Summer", "20-27°C", "Moderate", "/images/tomato.png"));
        cropDatabase.add(new Crop("Groundnut", "Sandy", "Summer", "25-30°C", "Low", "/images/groundnut.png"));
        cropDatabase.add(new Crop("Soybean", "Clay", "Monsoon", "20-30°C", "Moderate", "/images/soybean.png"));
        cropDatabase.add(new Crop("Pulses", "Alluvial", "Summer", "20-25°C", "Low", "/images/pulses.png"));
    }

    public List<PredictionResult> recommendCrops(String soilType, String season, WeatherData weather) {
        List<PredictionResult> results = new ArrayList<>();

        for (Crop crop : cropDatabase) {
            double score = 50.0; // Base score
            StringBuilder explanation = new StringBuilder();

            // Soil matching
            if (crop.getIdealSoilType().equalsIgnoreCase(soilType)) {
                score += 20;
                explanation.append("Excellent soil match. ");
            } else {
                score -= 10;
                explanation.append("Sub-optimal soil type. ");
            }

            // Season matching
            if (crop.getSeason().equalsIgnoreCase(season)) {
                score += 30;
                explanation.append("Ideal season. ");
            } else {
                score -= 40; // Heavy penalty for wrong season
                explanation.append("Not recommended for this season. ");
            }

            // Weather matching (simplistic logic)
            // e.g. for Rice it needs high rain.
            if ("High".equals(crop.getWaterRequirement()) && weather.getRainfall() > 100) {
                score += 10;
                explanation.append("Good rainfall detected. ");
            } else if ("Low".equals(crop.getWaterRequirement()) && weather.getRainfall() < 50) {
                score += 10;
                explanation.append("Favorable dry conditions. ");
            }

            // Temperature (rough parsing)
            try {
                String[] temps = crop.getIdealTemperature().replace("°C", "").split("-");
                if (temps.length == 2) {
                    double minT = Double.parseDouble(temps[0]);
                    double maxT = Double.parseDouble(temps[1]);
                    if (weather.getTemperature() >= minT && weather.getTemperature() <= maxT) {
                        score += 5;
                        explanation.append("Optimal temperature.");
                    } else {
                        score -= 5;
                        explanation.append("Temperature slightly off.");
                    }
                }
            } catch (Exception e) {
                // ignore parsing errors
            }

            // Caps
            if (score > 99.9) score = 99.9;
            if (score < 10.0) score = 10.0;

            results.add(new PredictionResult(crop, score, explanation.toString().trim()));
        }

        // Sort by confidence score descending
        return results.stream()
                .sorted(Comparator.comparingDouble(PredictionResult::getConfidenceScore).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }
}
