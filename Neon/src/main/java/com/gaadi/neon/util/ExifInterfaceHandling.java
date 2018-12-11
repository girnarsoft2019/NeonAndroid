package com.gaadi.neon.util;

import android.location.Location;
import android.support.media.ExifInterface;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Pavan
 * @version 1.0
 * @since 14/7/17
 */

public class ExifInterfaceHandling extends ExifInterface {

    // ------------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------------

    public ExifInterfaceHandling(File file) throws IOException {
       super(file.getCanonicalPath());
    }

    // ------------------------------------------------------------------------
    // Public Methods
    // ------------------------------------------------------------------------

    public void setLocation(Location location, String appName) {
        try {

            //setLatLong(location.getLatitude(),location.getLongitude());
            Date date = new Date(location.getTime());
//            String dateStamp = new SimpleDateFormat("yyyy:MM:dd").format(date);
//            String timeStamp = new SimpleDateFormat("hh:mm:ss").format(date);
            this.setAttribute(TAG_GPS_LATITUDE_REF , String.valueOf(location.getLatitude()));
            this.setAttribute(TAG_GPS_LONGITUDE_REF, String.valueOf(location.getLongitude()));
//            this.setAttribute(TAG_GPS_DATESTAMP , dateStamp);
//            this.setAttribute(TAG_GPS_TIMESTAMP, timeStamp);
            this.setAttribute(TAG_DATETIME, String.valueOf(date.getTime()) );
            this.setAttribute(TAG_ARTIST , appName);

            saveAttributes();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setAppName(String appName) {
        try {

            this.setAttribute(TAG_ARTIST , appName);

            saveAttributes();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}