package com.sweet.zhihuribao.pager;

import android.app.Activity;
import android.content.Context;

import com.sweet.zhihuribao.base.BasePager;

/**
 * Created by Sweet on 2016/4/6/0006.
 */
public class Pager3 extends BasePager {

    public Pager3(Context context) {
        super(context);
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initData() {
        initDate(-1);
        getCache(titleTiem);
        getDataFromSever(titleTiem);
    }

    @Override
    public void initEvent() {

    }
}
