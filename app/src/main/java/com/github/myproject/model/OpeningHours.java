package com.github.myproject.model;

import com.google.gson.annotations.SerializedName;

public class OpeningHours {

    @SerializedName("open_now")
    private boolean openNow;

    public boolean isOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    @Override
    public String toString() {
        return
                "OpeningHours{" +
                        "open_now = '" + openNow + '\'' +
                        "}";
    }
}