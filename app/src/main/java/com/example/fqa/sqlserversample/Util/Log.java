package com.example.fqa.sqlserversample.Util;

import android.app.Activity;

public class Log {
    private Activity activity;
    private static String TAG;

    public Log(Activity activity) {
        this.activity = activity;
        TAG = "MainActivity";
    }

    public void logInfo(String info){
        android.util.Log.i(TAG, info);
    }
    public void logErro(String erro, Throwable ex){
        android.util.Log.e(TAG, erro, ex);
    }
    public void logErro(String erro){
        android.util.Log.e(TAG, erro);
    }
}
