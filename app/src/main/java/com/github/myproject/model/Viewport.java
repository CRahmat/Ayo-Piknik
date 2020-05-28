package com.github.myproject.model;

import com.google.gson.annotations.SerializedName;

public class Viewport {

    @SerializedName("southwest")
    private Southwest southwest;

    @SerializedName("northeast")
    private Northeast northeast;

    public Southwest getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }

    public Northeast getNortheast() {
        return northeast;
    }

    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    @Override
    public String toString() {
        return
                "Viewport{" +
                        "southwest = '" + southwest + '\'' +
                        ",northeast = '" + northeast + '\'' +
                        "}";
    }
}