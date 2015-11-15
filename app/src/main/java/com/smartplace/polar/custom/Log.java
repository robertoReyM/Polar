package com.smartplace.polar.custom;


import com.smartplace.polar.BuildConfig;

public class Log {

    public static void d(String TAG, String message) {

        if(BuildConfig.DEBUG) {

            int maxLogSize = 2000;
            for (int i = 0; i <= message.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > message.length() ? message.length() : end;
                android.util.Log.d(TAG, message.substring(start, end));
            }
        }
    }

}