package com.example.mycarfootprint;

// Class for storing information about a gas station visit
public class GasStationVisit {
    private String gasStationName;
    private String date;
    private String fuelType;
    private int amount;
    private double pricePerLiter;

    public GasStationVisit(String gasStationName, String date, String fuelType, int amount, double pricePerLiter) {
        this.gasStationName = gasStationName;
        this.date = date;
        this.fuelType = fuelType;
        this.amount = amount;
        this.pricePerLiter = pricePerLiter;
    }

    public String getGasStationName() {
        return gasStationName;
    }

    public String getDate() {
        return date;
    }

    public String getFuelType() {
        return fuelType;
    }

    public int getAmount() {
        return amount;
    }

    public double getPricePerLiter() {
        return pricePerLiter;
    }

    public double getFuelCost() {
        return amount * pricePerLiter;
    }

    public int getCarbonFootprint() {
        double carbon_footprint = 0.0;
        if (fuelType.equals("Gasoline")) {
            carbon_footprint = amount * 2.32;
        } else if (fuelType.equals("Diesel")) {
            carbon_footprint = amount * 2.69;
        }
        return (int) Math.round(carbon_footprint); // Round to the nearest integer
    }

    public void setGasStationName(String gasStationName) {
        this.gasStationName = gasStationName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPricePerLiter(double pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }
}

