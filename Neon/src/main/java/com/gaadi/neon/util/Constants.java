package com.gaadi.neon.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.scanlibrary.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lakshay
 *
 * @since 13-02-2015.
 */
public class Constants {
    public static final String Gallery_Params = "Gallery_Params";
    public static final String Camera_Params = "Camera_Params";
    public static final String BucketName = "BucketName";
    public static final String BucketId = "BucketId";

    public static final int destroyPreviousActivity = 300;

    public static final int TYPE_IMAGE = 1;
    public static final String APP_SHARED_PREFERENCE = "com.gcloud.gaadi.prefs";
    public static final String TAG = "Gallery";
    public static final String RESULT_IMAGES = "result_images";

    public static final String SINGLE_IMAGE_PATH = "singleImagePath";
    public static final String REVIEW_TITLE = "reviewTitle";

    public static final String IMAGES_SELECTED = "imagesSelected";
    public static final String IMAGE_PATH = "image_path";
    public static final int REQUEST_PERMISSION_CAMERA = 104;
    public static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 105;
    public static final String FLASH_MODE = "flashMode";
    public static final String IMAGE_TAGS_FOR_REVIEW = "imageTagsReview";
    public static final String IMAGE_MODEL_FOR__REVIEW = "imageModelReview";
    public static final String IMAGE_REVIEW_POSITION = "imageReviewPosition";
    public static final String SINGLE_TAG_SELECTION = "singleTagSelection";
    public static final String ALREADY_SELECTED_TAGS = "alreadySelectedTags";
    public static String FLAG = "Flag";
    public static final String CATEGORY = "category";
    public static final String SUB_CATEGORY = "subCategory";
    public static final String CAM_SCANNER_API_KEY = "camScannerApiKey";
    public static File getMediaOutputFile(Context context, int type, String folderName) {
        String appName = context.getString(R.string.app_name);
        if (appName.length() > 0) {
            appName = appName.replace(" ", "");
        }
        String path;
        if(folderName != null){
            path=Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+appName+File.separator+folderName;
        }else {
            path=Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+appName;
        }

        File mediaStorageDir = new File(path);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
            }
        }
        // Create a media file name
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss.SSS").format(new Date());
        File mediaFile;

        if (type == TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + System.currentTimeMillis() + ".jpg");
        } else
            return null;
        return mediaFile;
    }

    public static String getAppName(Context context){
        String appName = context.getString(R.string.app_name);
        if (appName.length() > 0) {
            appName = appName.replace(" ", "");
        }
        return appName;
    }
}
