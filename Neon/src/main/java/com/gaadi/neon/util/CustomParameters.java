package com.gaadi.neon.util;

/**
 * @author princebatra
 * @version 1.0
 * @since 17/7/17
 */
public class CustomParameters {

    private boolean hideCameraButtonInNeutral = false;
    private boolean hideGalleryButtonInNeutral = false;
    private boolean locationRestrictive = true;
    private boolean camScannerActive = false;
    private String camScannerAPIKey = "";

    private CustomParameters(CustomParametersBuilder builder) {
        this.hideCameraButtonInNeutral = builder.hideCameraButtonInNeutral;
        this.hideGalleryButtonInNeutral = builder.hideGalleryButtonInNeutral;
        this.locationRestrictive = builder.locationRestrictive;
        this.camScannerActive = builder.camScannerActive;
        this.camScannerAPIKey = builder.camScannerAPIKey;
    }

    public boolean gethideCameraButtonInNeutral() {
        return hideCameraButtonInNeutral;
    }

    public boolean getHideGalleryButtonInNeutral() {
        return hideGalleryButtonInNeutral;
    }

    public boolean getLocationRestrictive() {
        return locationRestrictive;
    }

    public boolean isCamScannerActive() {
        return camScannerActive;
    }

    public String getCamScannerAPIKey() {
        return camScannerAPIKey;
    }


    public static class CustomParametersBuilder {

        private boolean hideCameraButtonInNeutral;
        private boolean hideGalleryButtonInNeutral;
        private boolean locationRestrictive = true;
        private boolean camScannerActive;
        private String camScannerAPIKey = "";


        public CustomParametersBuilder sethideCameraButtonInNeutral(boolean hide) {
            this.hideCameraButtonInNeutral = hide;
            return this;
        }

        public CustomParametersBuilder setHideGalleryButtonInNeutral(boolean hide) {
            this.hideGalleryButtonInNeutral = hide;
            return this;
        }

        public CustomParametersBuilder setLocationRestrictive(boolean locationRestrictive) {
            this.locationRestrictive = locationRestrictive;
            return this;
        }

        public CustomParametersBuilder setCamScannerActive(boolean camScannerActive) {
            this.camScannerActive = camScannerActive;
            return this;
        }

        public CustomParametersBuilder setCamScannerAPIKey(String camScannerAPIKey) {
            this.camScannerAPIKey = camScannerAPIKey;
            return this;
        }

        public CustomParameters build() {
            CustomParameters user = new CustomParameters(this);
            return user;
        }

    }
}
