package com.hegel.screenrecorder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyScreenDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "HegelTest";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "my_screen_action.db";
    
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + DBConst.TABLE_SCREEN_ACTION + " ("
            + DBConst.COLUMN_MAIN + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DBConst.COLUMN_SCREEN_TYPE + " VARCHAR(50),"
            + DBConst.COLUMN_DATE_TIME + " VARCHAR(50))";
    
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBConst.TABLE_SCREEN_ACTION;

    public MyScreenDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, "MyScreenDbHelper onCreate");
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, "MyScreenDbHelper onUpgrade " + oldVersion + " --> " + newVersion);
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
