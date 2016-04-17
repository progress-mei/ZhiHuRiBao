package com.sweet.suibi.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.sweet.suibi.R;
import com.sweet.suibi.adapter.MyAdapter;
import com.sweet.suibi.bean.NoteContext;
import com.sweet.suibi.db.MyDatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private MyAdapter mAdapter;
    private MyDatabaseHelper dbHelper;
    private Context mContext;
    private ArrayList<NoteContext> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        initView();
        initData();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        setContentView(R.layout.activity_main);
        Toolbar mToolBar = (Toolbar) findViewById(R.id.tl_toolbar);
        mToolBar.setTitle("随笔·");
        mToolBar.setTitleTextColor(Color.WHITE);
        lv = (ListView) findViewById(R.id.lv_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickIntent = new Intent(MainActivity.this, ContextActivity.class);
                clickIntent.putExtra("body", lists.get(position).getBody());
                clickIntent.putExtra("date", lists.get(position).getDate());
                clickIntent.putExtra("md5", lists.get(position).getMd5());
                clickIntent.putExtra("key", "0");
                startActivity(clickIntent);
            }
        });
    }

    public void go(View view) {
        Intent intent = new Intent(this, ContextActivity.class);
        intent.putExtra("key", "1");
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        lists = new ArrayList<>();
        dbHelper = new MyDatabaseHelper(this, "NoteMessage.db", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Note", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String body = cursor.getString(cursor.getColumnIndex("body"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String md5 = cursor.getString(cursor.getColumnIndex("md5"));
                NoteContext noteContext = new NoteContext();
                noteContext.setBody(body);
                noteContext.setDate(date);
                noteContext.setMd5(md5);
                lists.add(noteContext);
            } while (cursor.moveToNext());
        }
        cursor.close();
        mAdapter = new MyAdapter(mContext, lists);
        lv.setAdapter(mAdapter);
    }
}
