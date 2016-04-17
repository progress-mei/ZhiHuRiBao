package com.sweet.suibi.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sweet.suibi.R;
import com.sweet.suibi.db.MyDatabaseHelper;
import com.sweet.suibi.utils.MD5Encoder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sweet on 2016/4/14/0014.
 */
public class ContextActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private EditText content;
    private Button save;
    private String currentDate;
    private ContentValues values;
    private String body;
    private SQLiteDatabase db;
    private Intent intent;
    private String key;
    private String md5;
    private String getDate;
    private String getMd5;
    private String getBody;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {
        setContentView(R.layout.activity_content);
        content = (EditText) findViewById(R.id.et_content);
        Toolbar mToolBar = (Toolbar) findViewById(R.id.tl_toolbar);
        mToolBar.setTitle("随笔·");
        mToolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        save = (Button) findViewById(R.id.bt_save);
    }

    public void save(View view) {

        body = content.getText().toString();
        String millis = String.valueOf(System.currentTimeMillis());
        try {
            md5 = MD5Encoder.encode(body + millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (key) {
            case "0":
                values.put("body", body);
                values.put("date", initDate());
                values.put("md5", md5);
                db.update("Note", values, "md5 = ?", new String[]{getMd5});
                Toast.makeText(this, key, Toast.LENGTH_SHORT).show();
                break;
            case "1":
                values.put("body", body);
                values.put("date", initDate());
                values.put("md5", md5);
                db.insert("Note", null, values);
                break;
        }
        Toast.makeText(this, "保存成功啦", Toast.LENGTH_SHORT).show();
        db.close();
        finish();
    }

    public void delete(View view) {
        db.delete("Note", "md5 = ?", new String[]{getMd5});
        Toast.makeText(this, "删除成功啦", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void initData() {
        intent = getIntent();
        getBody = intent.getStringExtra("body");
        getDate = intent.getStringExtra("date");
        getMd5 = intent.getStringExtra("md5");
        key = intent.getStringExtra("key");
        content.setText(getBody);
        dbHelper = new MyDatabaseHelper(this, "NoteMessage.db", null, 1);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();
    }

    /**
     * 获取当前时间
     */
    private String initDate() {
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        SimpleDateFormat title = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//        SimpleDateFormat title = new SimpleDateFormat("yyyy年-MM月-dd日");
        String time = String.valueOf(calendar.getTime());
        currentDate = title.format(calendar.getTime());

        return currentDate;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
