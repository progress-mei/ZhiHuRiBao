package com.sweet.zhihuribao.pager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sweet.zhihuribao.R;
import com.sweet.zhihuribao.activity.ContentActivity;
import com.sweet.zhihuribao.adapter.HeadTopViewPager;
import com.sweet.zhihuribao.adapter.MyAdapter;
import com.sweet.zhihuribao.base.BasePager;
import com.sweet.zhihuribao.bean.ZhiM;
import com.sweet.zhihuribao.utils.Image_sp;
import com.sweet.zhihuribao.utils.PrefUtils;

import java.util.List;

/**
 * Created by Sweet on 2016/4/6/0006.
 */
public class Pager1 extends BasePager {

    private TextView viewById;
    private LinearLayout dot_layout;
    private ViewPager mHeadViewPager;

    public Pager1(Context context) {
        super(context);
    }

    @Override
    public void initViews() {
        super.initViews();
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefrush) {
                    isRefrush = true;
                    getDataFromSever();
                }
            }
        });
    }

    public void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(mContent).inflate(R.layout.head_vp, view, false);
        viewById = (TextView) header.findViewById(R.id.tv_desc);
        dot_layout = (LinearLayout) header.findViewById(R.id.ll_dot);
        mHeadViewPager = (ViewPager) header.findViewById(R.id.vp);
        final List<ZhiM.TopStoriesBean> top_stories = zhData.top_stories;
        HeadTopViewPager viewPager = new HeadTopViewPager(top_stories, mContent);
        mHeadViewPager.setAdapter(viewPager);
        viewPager.setmOnItemClickListener(new HeadTopViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                System.out.println(position + "+++");
                Intent intent = new Intent(mContent, ContentActivity.class);
                intent.putExtra("id", top_stories.get(position).id + "");
                mContent.startActivity(intent);

            }
        });
        mHeadViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewById.setText(top_stories.get(position).title);
                updateIntroAndDot();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mAdapter.setHeaderView(header);
    }

    @Override
    public void initData() {
        getDataFromSever();
    }

    /**
     * 初始化dot
     */
    private void initDots() {
        for (int i = 0; i < zhData.top_stories.size(); i++) {
            View view = new View(mContent);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            if (i != 0) {
                params.leftMargin = 8;
            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.selector_dot);
            dot_layout.addView(view);
        }
    }
    /**
     * 更新选择器
     */
    private void updateIntroAndDot(){
        int currentPage = mHeadViewPager.getCurrentItem()%zhData.top_stories.size();
        for (int i = 0; i < dot_layout.getChildCount(); i++) {
            dot_layout.getChildAt(i).setEnabled(i==currentPage);
        }
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    /**
     * 从网络获取数据
     */
    public void getDataFromSever() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, Image_sp.zhihuItem, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                praseData(result);
                if (isRefrush) {
                    showSnackbar("刷新成功啦...");
                    swipeLayout.setRefreshing(false);
                    isRefrush = false;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                if (isRefrush) {
                    swipeLayout.setRefreshing(false);
                    isRefrush = false;
                    showSnackbar("解析数据异常，请检查网络连接...");
                } else {
                    Toast.makeText(mContent, "解析数据异常，请检查网络连接...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 解析数据
     *
     * @param result
     */
    public void praseData(String result) {
        Gson gson = new Gson();
        zhData = gson.fromJson(result, ZhiM.class);
        stories = zhData.stories;

        mAdapter = new MyAdapter(mContent, stories);
        mList.setAdapter(mAdapter);
        setHeader(mList);
        viewById.setText(zhData.top_stories.get(0).title);
        initDots();
        updateIntroAndDot();

        mAdapter.setmOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContent, ContentActivity.class);
                intent.putExtra("id", zhData.stories.get(position -1).id + "");
                mContent.startActivity(intent);
                String ids = PrefUtils.getString(mContent, "read_ids", "");

                //  此处position要校正 - 1 ，因为多了一个headview
                if (!ids.contains(zhData.stories.get(position).id)) {
                    String read_ids = ids + zhData.stories.get(position).id + ",";
                    PrefUtils.setString(mContent, "read_ids", read_ids);
                }

                setReadItemColor(view);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
}
