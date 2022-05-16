package com.example.imat.models;

public class LocationInfo {
    private String prettyGoBack;
    private String location;
    private String breadcrumb;

    public LocationInfo(String location, String prettyPrint, String breadcrumb) {
        this.prettyGoBack = prettyPrint;
        this.location = location;
        this.breadcrumb = breadcrumb;
    }

    public String getPrettyGoBack() {
        return prettyGoBack;
    }

    public String getLocation() {
        return location;
    }

    public String getBreadcrumb() {
        return breadcrumb;
    }
}
