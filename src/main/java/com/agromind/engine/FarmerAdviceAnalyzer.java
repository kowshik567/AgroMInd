package com.agromind.engine;

import java.util.ArrayList;
import java.util.List;

public class FarmerAdviceAnalyzer {

    public List<String> analyzeTrends() {
        List<String> advices = new ArrayList<>();
        
        // Mocking logic to analyze trends
        advices.add("Based on recent lower yields, consider testing soil pH levels before the next planting season.");
        advices.add("Rainfall forecasts are 20% below average this month; ensure drip irrigation systems are ready.");
        advices.add("Market prices for Maize are projected to rise. Consider allocating 15% more land to Maize if conditions allow.");
        advices.add("Utilize organic compost to improve soil moisture retention during the upcoming summer months.");
        
        return advices;
    }
}
