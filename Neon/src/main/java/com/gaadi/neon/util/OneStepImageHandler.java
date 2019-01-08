package com.gaadi.neon.util;

import com.gaadi.neon.model.NeonResponse;

import java.util.ArrayList;



/**
 * @author lakshaygirdhar
 * @version 1.0
 * @since 20/4/17
 */

public class OneStepImageHandler {
    private static OneStepImageHandler mInstance;
    private OneStepActionListener mOneStepActionListener;

    public static OneStepImageHandler getInstance() {
        if (mInstance == null) {
            mInstance = new OneStepImageHandler();
            }
        return mInstance;
    }

    public void setOneStepImagesActionListener(OneStepActionListener oneStepActionListener) {
        mOneStepActionListener = oneStepActionListener;
    }

    public OneStepActionListener getOneStepImagesActionListener() {
        return mOneStepActionListener;
    }

    public interface OneStepActionListener {
        void imageCollection(NeonResponse response);

    }
}
