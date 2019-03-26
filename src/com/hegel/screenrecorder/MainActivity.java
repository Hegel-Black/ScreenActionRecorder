package com.hegel.screenrecorder;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
    private static final String TAG = "HegelTest";
    private TextView tv1;
    private Button allBtn;
    private Button screenOnBtn;
    private Button screenOffBtn;
    private Button screenUnlockBtn;

    private SQLiteDatabase readDB;
    private static final String QUERY_ROW_LIMIT = "10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "MainActivity onCreate");
        setContentView(R.layout.activity_main);

        startService(new Intent(this, ListenService.class));

        initView();

        readDB = MyDBManager.getInstance(this).getReadableDatabase();
    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.tv1);
        allBtn = (Button) findViewById(R.id.btn_all);
        screenOnBtn = (Button) findViewById(R.id.btn_screen_on);
        screenOffBtn = (Button) findViewById(R.id.btn_screen_off);
        screenUnlockBtn = (Button) findViewById(R.id.btn_screen_unlock);

        allBtn.setOnClickListener(this);
        screenOnBtn.setOnClickListener(this);
        screenOffBtn.setOnClickListener(this);
        screenUnlockBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_all:
            tv1.setText(getScreenText(null, null));
            break;
        case R.id.btn_screen_on:
            tv1.setText(getScreenText(DBConst.COLUMN_SCREEN_TYPE + "=?", new String[] { DBConst.SCREEN_TYPE_ON }));
            break;
        case R.id.btn_screen_off:
            tv1.setText(getScreenText(DBConst.COLUMN_SCREEN_TYPE + "=?", new String[] { DBConst.SCREEN_TYPE_OFF }));
            break;
        case R.id.btn_screen_unlock:
            tv1.setText(getScreenText(DBConst.COLUMN_SCREEN_TYPE + "=?", new String[] { DBConst.SCREEN_TYPE_UNLOCK }));
            break;
        default:
            break;
        }
    }

    private String getScreenText(String selection, String[] selectionArgs) {
        boolean distinct = false;
        String table = DBConst.TABLE_SCREEN_ACTION;
        String[] columns = { DBConst.COLUMN_MAIN, DBConst.COLUMN_SCREEN_TYPE, DBConst.COLUMN_DATE_TIME };
        String groupBy = null;
        String having = null;
        String orderBy = "id DESC";
        String limit = QUERY_ROW_LIMIT;
        Cursor cursor = readDB.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        StringBuilder result = new StringBuilder();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.append(cursor.getInt(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + "\n");
            }
        }
        cursor.close();
        return result.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "MainActivity onDestroy");
        readDB.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Toast.makeText(this, R.string.toast_about, Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
