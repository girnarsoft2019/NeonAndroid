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
    private boolean showTagImage;

    private CustomParameters(CustomParametersBuilder builder) {
        this.hideCameraButtonInNeutral = builder.hideCameraButtonInNeutral;
        this.hideGalleryButtonInNeutral = builder.hideGalleryButtonInNeutral;
        this.locationRestrictive = builder.locationRestrictive;
        this.showTagImage = builder.showTagImage;
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

    public boolean showTagImage() {
        return showTagImage;
    }



    public static class CustomParametersBuilder {

        private boolean hideCameraButtonInNeutral;
        private boolean hideGalleryButtonInNeutral;
        private boolean locationRestrictive = true;
        private boolean showTagImage;

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

        public CustomParametersBuilder showTagImagePreview(boolean tagShow) {
            this.showTagImage = tagShow;
            return this;
        }

        public CustomParameters build() {
            CustomParameters user = new CustomParameters(this);
            return user;
        }

    }
}
