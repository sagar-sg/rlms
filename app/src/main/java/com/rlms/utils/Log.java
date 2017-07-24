package com.rlms.utils;

/**
 * Created by Swapnil on 3/17/2017.
 */

public class Log {

    // Enable or disable logs while debugging
    public static boolean showLog = true;

    // Enable or disable uploading of API-logs to server using API
    private boolean isToUploadLogs = true;

    private String TAG = "LogFile";

    public Log() {
    }

    public void d(String TAG, String msg) {

        if (showLog) {
            android.util.Log.v(TAG, msg);
        }
    }

    public void err(String TAG, String msg) {

        if (showLog) {
            android.util.Log.e(TAG, msg);
        }
    }


}
