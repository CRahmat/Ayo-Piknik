package com.github.myproject.model;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("lng")
    private double lng;

    @SerializedName("lat")
    private double lat;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return
                "Location{" +
                        "lng = '" + lng + '\'' +
                        ",lat = '" + lat + '\'' +
                        "}";
    }
}