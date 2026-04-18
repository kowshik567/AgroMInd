package com.agromind.model;

public class WeatherData {
    private double temperature;
    private double humidity;
    private double rainfall; // in mm

    public WeatherData(double temperature, double humidity, double rainfall) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.rainfall = rainfall;
    }

    public double getTemperature() { return temperature; }
    public double getHumidity() { return humidity; }
    public double getRainfall() { return rainfall; }
}
