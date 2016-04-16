package com.sweet.zhihuribao.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.sweet.zhihuribao.activity.MainActivity;


/**
 * Created by Sweet on 2016/3/25/0025.
 */
public abstract class BaseFragment extends Fragment {

    public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView();
        return view;
    }

    public abstract View initView();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initEvent();
    }

    public void initEvent() {

    }

    public void initData() {

    }

    public abstract boolean onCreateOptionsMenu(Menu menu);
}
