package com.sweet.zhihuribao.pager;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;

import com.sweet.zhihuribao.base.BasePager;

/**
 * Created by Sweet on 2016/4/6/0006.
 */
public class Pager2 extends BasePager {

    public Pager2(Context context) {
        super(context);
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initData() {
        initDate(0);
        getDataFromSever(titleTiem);
    }

    @Override
    public void initEvent() {

    }
}
