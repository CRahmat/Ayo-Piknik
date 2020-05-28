package com.github.myproject.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlacesResponse {

    @SerializedName("next_page_token")
    private String nextPageToken;

    @SerializedName("html_attributions")
    private List<Object> htmlAttributions;

    @SerializedName("results")
    private List<PlacesResultsItem> results;

    @SerializedName("status")
    private String status;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public List<PlacesResultsItem> getResults() {
        return results;
    }

    public void setResults(List<PlacesResultsItem> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return
                "PlacesResponse{" +
                        "next_page_token = '" + nextPageToken + '\'' +
                        ",html_attributions = '" + htmlAttributions + '\'' +
                        ",results = '" + results + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}