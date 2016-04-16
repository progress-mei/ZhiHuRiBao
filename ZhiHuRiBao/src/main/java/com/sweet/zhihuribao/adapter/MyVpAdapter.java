package com.sweet.zhihuribao.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.sweet.zhihuribao.base.BasePager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sweet on 2016/4/6/0006.
 */
public class MyVpAdapter extends PagerAdapter {

    private ArrayList<BasePager> mPagers;
    private List<String> mTitleList;

    public MyVpAdapter(ArrayList<BasePager> pagers, List<String> mTitleList) {
        mPagers = pagers;
        this.mTitleList = mTitleList;
    }

    @Override
    public int getCount() {
        return mPagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager pager = mPagers.get(position);
        container.addView(pager.mRootView);
        // pager.initData();// 初始化数据.... 不要放在此处初始化数据, 否则会预加载下一个页面
        return pager.mRootView;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);//页卡标题
    }
}
