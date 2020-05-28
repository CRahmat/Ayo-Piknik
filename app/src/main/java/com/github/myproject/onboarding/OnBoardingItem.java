package com.github.myproject.onboarding;

public class OnBoardingItem {
    String onBoardingTitle;
    String onBoardingDescription;
    int onBoardingImage;
    int onBoardingBackground;

    public OnBoardingItem(String onBoardingTitle, String onBoardingDescription, int onBoardingImage, int onBoardingBackground) {
        this.onBoardingTitle = onBoardingTitle;
        this.onBoardingDescription = onBoardingDescription;
        this.onBoardingImage = onBoardingImage;
        this.onBoardingBackground = onBoardingBackground;
    }

    public String getOnBoardingTitle() {
        return onBoardingTitle;
    }

    public void setOnBoardingTitle(String onBoardingTitle) {
        this.onBoardingTitle = onBoardingTitle;
    }

    public String getOnBoardingDescription() {
        return onBoardingDescription;
    }

    public void setOnBoardingDescription(String onBoardingDescription) {
        this.onBoardingDescription = onBoardingDescription;
    }

    public int getOnBoardingImage() {
        return onBoardingImage;
    }

    public void setOnBoardingImage(int onBoardingImage) {
        this.onBoardingImage = onBoardingImage;
    }

    public int getOnBoardingBackground() {
        return onBoardingBackground;
    }

    public void setOnBoardingBackground(int onBoardingBackground) {
        this.onBoardingBackground = onBoardingBackground;
    }
}
