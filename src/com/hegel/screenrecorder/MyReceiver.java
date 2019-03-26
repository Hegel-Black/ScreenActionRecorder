package com.hegel.screenrecorder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "HegelTest";
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		SQLiteDatabase writeDB = MyDBManager.getInstance(context).getWritableDatabase();
		ContentValues values = new ContentValues();
		String type = DBConst.SCREEN_TYPE_UNKNOWN;
		String time = format.format(new Date());

		if (action.equals(Intent.ACTION_SCREEN_ON)) {
			Log.d(TAG, "Screen On: "+time);
			type = DBConst.SCREEN_TYPE_ON;
			
		} else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
			Log.d(TAG, "Screen Off: "+time);
			type = DBConst.SCREEN_TYPE_OFF;
			
		} else if (action.equals(Intent.ACTION_USER_PRESENT)) {
			Log.d(TAG, "Unlock: "+time);
			type = DBConst.SCREEN_TYPE_UNLOCK;
			
		}
		
		values.put(DBConst.COLUMN_SCREEN_TYPE, type);
        values.put(DBConst.COLUMN_DATE_TIME, time);
		writeDB.insert(DBConst.TABLE_SCREEN_ACTION, null, values);
	}

}
