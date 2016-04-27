package com.sweet.zhihuribao.base;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.sweet.zhihuribao.R;

/**
 * Created by Sweet on 2016/4/6/0006.
 */
public class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolBar;
    protected int layoutResID = R.layout.activity_base;
    private LinearLayout lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layoutResID);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        lv = (LinearLayout) findViewById(R.id.lv_content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {//back key
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showSnackbar(int resId) {
        Snackbar.make(lv, resId, Snackbar.LENGTH_SHORT).show();
    }
}
