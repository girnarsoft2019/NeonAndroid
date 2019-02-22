package com.gaadi.neon.util;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import java.util.ArrayList;

/**
 * Created by Lakshay
 * @since 12-03-2015.
 *
 */
public class ApplicationController extends Application {


    public static ArrayList<String> selectedFiles = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
