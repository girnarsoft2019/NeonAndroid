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
    private int setCompressBy;
    private boolean folderRestrictive;
    private String folderName;
    private String titleName;
    private String vccIdAvailable;
    private int clickMinimumNumberOfImages;
    private boolean showTagImage;
    private String camScannerAPIKey;
    private boolean showPreviewForEachImage;

    private CustomParameters(CustomParametersBuilder builder) {
        this.hideCameraButtonInNeutral = builder.hideCameraButtonInNeutral;
        this.hideGalleryButtonInNeutral = builder.hideGalleryButtonInNeutral;
        this.locationRestrictive = builder.locationRestrictive;
        this.setCompressBy = builder.setCompressBy;
        this.folderRestrictive = builder.folderRestrictive;
        this.folderName = builder.folderName;
        this.titleName = builder.titleName;
        this.vccIdAvailable = builder.vccIdAvailable;
        this.clickMinimumNumberOfImages = builder.clickMinimumNumberOfImages;
        this.showTagImage = builder.showTagImage;
        this.camScannerAPIKey = builder.camScannerAPIKey;
        this.showPreviewForEachImage = builder.showPreviewForEachImage;
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

    public int getCompressBy() {
        return setCompressBy;
    }

    public boolean getFolderRestrictive() {
        return folderRestrictive;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getCamScannerAPIKey() {
        return camScannerAPIKey;
    }

    public void setCamScannerAPIKey(String camScannerAPIKey) {
        this.camScannerAPIKey = camScannerAPIKey;
    }

    public String getTitleName() {
        return titleName;
    }

    public String getVccIdAvailable() {
        return vccIdAvailable;
    }

    public int getClickMinimumNumberOfImages() {
        return clickMinimumNumberOfImages;
    }

    public boolean showTagImage() {
        return showTagImage;
    }

    public boolean isShowPreviewForEachImage() {
        return showPreviewForEachImage;
    }


    public static class CustomParametersBuilder {

        private boolean hideCameraButtonInNeutral;
        private boolean hideGalleryButtonInNeutral;
        private boolean locationRestrictive = true;
        private int setCompressBy;
        private boolean folderRestrictive;
        private String folderName;
        private String titleName;
        private String vccIdAvailable;
        private int clickMinimumNumberOfImages;
        private boolean showTagImage;
        private String camScannerAPIKey = "";
        private boolean showPreviewForEachImage;

        public CustomParametersBuilder sethideCameraButtonInNeutral(boolean hide) {
            this.hideCameraButtonInNeutral = hide;
            return this;
        }

        public CustomParametersBuilder setTitleName(String title) {
            this.titleName = title;
            return this;
        }

        public CustomParametersBuilder showTagImagePreview(boolean tagShow) {
            this.showTagImage = tagShow;
            return this;
        }

        public CustomParametersBuilder setVccIdAvailable(String vccId) {
            this.vccIdAvailable = vccId;
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

        public CustomParametersBuilder setCompressBy(int setCompressBy) {
            this.setCompressBy = setCompressBy;
            return this;
        }

        public CustomParametersBuilder setFolderRestrictive(boolean folderRestrictive) {
            this.folderRestrictive = folderRestrictive;
            return this;
        }

        public CustomParametersBuilder setMinimumNumberOfImages(int imagesCount) {
            this.clickMinimumNumberOfImages = imagesCount;
            return this;
        }

        public CustomParametersBuilder setFolderName(String folderName) {
            this.folderName = folderName;
            return this;
        }

        public CustomParametersBuilder setCamScannerAPIKey(String camScannerAPIKey){
            this.camScannerAPIKey = camScannerAPIKey;
            return  this;
        }

        public CustomParametersBuilder setShowPreviewForEachImage(boolean showPreviewForEachImage) {
            this.showPreviewForEachImage = showPreviewForEachImage;
            return this;
        }

        public CustomParameters build() {
            CustomParameters user = new CustomParameters(this);
            return user;
        }

    }
}
