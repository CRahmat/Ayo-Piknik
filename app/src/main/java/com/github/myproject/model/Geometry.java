package com.github.myproject.model;

import com.google.gson.annotations.SerializedName;

public class Geometry {

    @SerializedName("viewport")
    private Viewport viewport;

    @SerializedName("location")
    private Location location;

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return
                "Geometry{" +
                        "viewport = '" + viewport + '\'' +
                        ",location = '" + location + '\'' +
                        "}";
    }
}