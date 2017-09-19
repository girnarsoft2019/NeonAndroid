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

    private CustomParameters(CustomParametersBuilder builder) {
        this.hideCameraButtonInNeutral = builder.hideCameraButtonInNeutral;
        this.hideGalleryButtonInNeutral = builder.hideGalleryButtonInNeutral;
        this.locationRestrictive = builder.locationRestrictive;
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


    public static class CustomParametersBuilder {

        private boolean hideCameraButtonInNeutral;
        private boolean hideGalleryButtonInNeutral;
        private boolean locationRestrictive = true;


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

        public CustomParameters build() {
            CustomParameters user = new CustomParameters(this);
            return user;
        }

    }
}
