package com.hegel.screenrecorder;

import android.content.Context;

public class MyDBManager {
    private static MyScreenDbHelper mMyScreenDbHelper;
    public static MyScreenDbHelper getInstance(Context context) {
        if (mMyScreenDbHelper == null) {
            mMyScreenDbHelper = new MyScreenDbHelper(context);
        }
        return mMyScreenDbHelper;
    }
}
