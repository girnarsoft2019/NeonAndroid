package com.gaadi.neon.model;

public class ImageInfo {

    private String id;
    private String imageName;
    private String imageType;
    private String imageCreationDate;
    private String date_time;
    private String vc_id;
    private String vccId;
    private String imgSrc;
    private String tag_id;
    private String tag_name;
    private String mandatory;
    private String imagePath;
    private String status;
    private String totalImages;
    private String reg_No;
    private String car_doc;
    private String inspectionRequesterLatitude;
    private String inspectionRequesterLongitude;
    private String carImageStatusDocInsertTimeStamp;
    private String upload_start_time;
    private boolean image_exist;
    private int priority;
    private String folderName;
    private String certification_ID;
    private String subBookingID;
    private boolean onlyImage;
    private String[] pendingReqTag;
    private boolean dummyEntry;

    public boolean isDummyEntry() {
        return dummyEntry;
    }

    public void setDummyEntry(boolean dummyEntry) {
        this.dummyEntry = dummyEntry;
    }

    public String[] getPendingReqTag() {
        return pendingReqTag;
    }

    public void setPendingReqTag(String[] pendingReqTag) {
        this.pendingReqTag = pendingReqTag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageCreationDate() {
        return imageCreationDate;
    }

    public void setImageCreationDate(String imageCreationDate) {
        this.imageCreationDate = imageCreationDate;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getVc_id() {
        return vc_id;
    }

    public void setVc_id(String vc_id) {
        this.vc_id = vc_id;
    }

    public String getVccId() {
        return vccId;
    }

    public void setVccId(String vccId) {
        this.vccId = vccId;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalImages() {
        return totalImages;
    }

    public void setTotalImages(String totalImages) {
        this.totalImages = totalImages;
    }

    public String getReg_No() {
        return reg_No;
    }

    public void setReg_No(String reg_No) {
        this.reg_No = reg_No;
    }

    public String getCar_doc() {
        return car_doc;
    }

    public void setCar_doc(String car_doc) {
        this.car_doc = car_doc;
    }

    public String getInspectionRequesterLatitude() {
        return inspectionRequesterLatitude;
    }

    public void setInspectionRequesterLatitude(String inspectionRequesterLatitude) {
        this.inspectionRequesterLatitude = inspectionRequesterLatitude;
    }

    public String getInspectionRequesterLongitude() {
        return inspectionRequesterLongitude;
    }

    public void setInspectionRequesterLongitude(String inspectionRequesterLongitude) {
        this.inspectionRequesterLongitude = inspectionRequesterLongitude;
    }

    public String getCarImageStatusDocInsertTimeStamp() {
        return carImageStatusDocInsertTimeStamp;
    }

    public void setCarImageStatusDocInsertTimeStamp(String carImageStatusDocInsertTimeStamp) {
        this.carImageStatusDocInsertTimeStamp = carImageStatusDocInsertTimeStamp;
    }

    public String getUpload_start_time() {
        return upload_start_time;
    }

    public void setUpload_start_time(String upload_start_time) {
        this.upload_start_time = upload_start_time;
    }

    public boolean isImage_exist() {
        return image_exist;
    }

    public void setImage_exist(boolean image_exist) {
        this.image_exist = image_exist;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getCertification_ID() {
        return certification_ID;
    }

    public void setCertification_ID(String certification_ID) {
        this.certification_ID = certification_ID;
    }

    public String getSubBookingID() {
        return subBookingID;
    }

    public void setSubBookingID(String subBookingID) {
        this.subBookingID = subBookingID;
    }

    public boolean isOnlyImage() {
        return onlyImage;
    }

    public void setOnlyImage(boolean onlyImage) {
        this.onlyImage = onlyImage;
    }
}
