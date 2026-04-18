package com.agromind.model;

public class Crop {
    private String name;
    private String idealSoilType;
    private String season;
    private String idealTemperature;
    private String waterRequirement;
    private String imagePath;
    
    public Crop(String name, String idealSoilType, String season, String idealTemperature, String waterRequirement, String imagePath) {
        this.name = name;
        this.idealSoilType = idealSoilType;
        this.season = season;
        this.idealTemperature = idealTemperature;
        this.waterRequirement = waterRequirement;
        this.imagePath = imagePath;
    }

    public String getName() { return name; }
    public String getIdealSoilType() { return idealSoilType; }
    public String getSeason() { return season; }
    public String getIdealTemperature() { return idealTemperature; }
    public String getWaterRequirement() { return waterRequirement; }
    public String getImagePath() { return imagePath; }

    @Override
    public String toString() {
        return name;
    }
}
