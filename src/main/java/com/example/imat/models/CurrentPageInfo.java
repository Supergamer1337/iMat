package com.example.imat.models;

public class CurrentPageInfo {
    private String lastLocation;
    private String currentLocation;

    public CurrentPageInfo(String lastLocation, String currentLocation) {
        this.lastLocation = lastLocation;
        this.currentLocation = currentLocation;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
}
