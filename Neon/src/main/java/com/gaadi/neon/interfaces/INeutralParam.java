package com.gaadi.neon.interfaces;

/**
 * @author princebatra
 * @version 1.0
 * @since 2/2/17
 */
public abstract class INeutralParam implements ICameraParam, IGalleryParam {

    public boolean hasOnlyProfileTag() {
        return false;
    }

    public String getProfileTagName() {
        return "Profile Image";
    }
}
