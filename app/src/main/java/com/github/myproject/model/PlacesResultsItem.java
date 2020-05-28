package com.github.myproject.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlacesResultsItem {

    @SerializedName("types")
    private List<String> types;

    @SerializedName("business_status")
    private String businessStatus;

    @SerializedName("icon")
    private String icon;

    @SerializedName("rating")
    private double rating;

    @SerializedName("photos")
    private List<PhotosItem> photos;

    @SerializedName("reference")
    private String reference;

    @SerializedName("user_ratings_total")
    private int userRatingsTotal;

    @SerializedName("price_level")
    private int priceLevel;

    @SerializedName("scope")
    private String scope;

    @SerializedName("name")
    private String name;

    @SerializedName("opening_hours")
    private OpeningHours openingHours = new OpeningHours();

    @SerializedName("geometry")
    private Geometry geometry;

    @SerializedName("vicinity")
    private String vicinity;

    @SerializedName("id")
    private String id;

    @SerializedName("plus_code")
    private PlusCode plusCode = new PlusCode();

    @SerializedName("place_id")
    private String placeId;

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<PhotosItem> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotosItem> photos) {
        this.photos = photos;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getUserRatingsTotal() {
        return userRatingsTotal;
    }

    public void setUserRatingsTotal(int userRatingsTotal) {
        this.userRatingsTotal = userRatingsTotal;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlusCode getPlusCode() {
        return plusCode;
    }

    public void setPlusCode(PlusCode plusCode) {
        this.plusCode = plusCode;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Override
    public String toString() {
        return
                "PlacesResultsItem{" +
                        "types = '" + types + '\'' +
                        ",business_status = '" + businessStatus + '\'' +
                        ",icon = '" + icon + '\'' +
                        ",rating = '" + rating + '\'' +
                        ",photos = '" + photos + '\'' +
                        ",reference = '" + reference + '\'' +
                        ",user_ratings_total = '" + userRatingsTotal + '\'' +
                        ",price_level = '" + priceLevel + '\'' +
                        ",scope = '" + scope + '\'' +
                        ",name = '" + name + '\'' +
                        ",opening_hours = '" + openingHours + '\'' +
                        ",geometry = '" + geometry + '\'' +
                        ",vicinity = '" + vicinity + '\'' +
                        ",id = '" + id + '\'' +
                        ",plus_code = '" + plusCode + '\'' +
                        ",place_id = '" + placeId + '\'' +
                        "}";
    }
}