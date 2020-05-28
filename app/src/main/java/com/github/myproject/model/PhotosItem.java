package com.github.myproject.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotosItem {

    @SerializedName("photo_reference")
    private String photoReference;

    @SerializedName("width")
    private int width;

    @SerializedName("html_attributions")
    private List<String> htmlAttributions;

    @SerializedName("height")
    private int height;

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return
                "PhotosItem{" +
                        "photo_reference = '" + photoReference + '\'' +
                        ",width = '" + width + '\'' +
                        ",html_attributions = '" + htmlAttributions + '\'' +
                        ",height = '" + height + '\'' +
                        "}";
    }
}