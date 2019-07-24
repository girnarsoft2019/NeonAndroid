package com.gaadi.neon.model;

import android.location.Location;

import java.io.Serializable;

/**
 * @author lakshaygirdhar
 * @version 1.0
 * @since 15/11/16
 */

public class ImageTagModel implements Serializable {
    private String tagName;
    private boolean mandatory;
    private String tagId;
    private int numberOfPhotos;
    private Location location;
    private String tagPreviewUrl;

    public ImageTagModel() {
    }

    public ImageTagModel(String _tagName, String _tagId, boolean _mandatory) {
        tagName = _tagName;
        tagId = _tagId;
        mandatory = _mandatory;
    }

    public ImageTagModel(String _tagName, String _tagId, boolean _mandatory,int _numberOfPhotos) {
        tagName = _tagName;
        tagId = _tagId;
        mandatory = _mandatory;
        numberOfPhotos = _numberOfPhotos;
    }
    public ImageTagModel(String _tagName, String _tagId, boolean _mandatory,int _numberOfPhotos, String imageUrl) {
        tagName = _tagName;
        tagId = _tagId;
        mandatory = _mandatory;
        numberOfPhotos = _numberOfPhotos;
        this.tagPreviewUrl = imageUrl;
    }


    public ImageTagModel(String _tagName, String _tagId, boolean _mandatory,int _numberOfPhotos,Location _location) {
        tagName = _tagName;
        tagId = _tagId;
        mandatory = _mandatory;
        numberOfPhotos = _numberOfPhotos;
        location=_location;
    }

    public ImageTagModel(String _tagName, String _tagId, boolean _mandatory,int _numberOfPhotos,Location _location, String imageUrl) {
        this.tagName = _tagName;
        this.mandatory = _mandatory;
        this.tagId = _tagId;
        this.numberOfPhotos = _numberOfPhotos;
        this.location = _location;
        this.tagPreviewUrl = imageUrl;
    }

    public String getTagPreviewUrl() {
        return tagPreviewUrl;
    }

    public void setTagPreviewUrl(String tagPreviewUrl) {
        this.tagPreviewUrl = tagPreviewUrl;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public int getNumberOfPhotos() {
        return numberOfPhotos;
    }

    public void setNumberOfPhotos(int numberOfPhotos) {
        this.numberOfPhotos = numberOfPhotos;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
