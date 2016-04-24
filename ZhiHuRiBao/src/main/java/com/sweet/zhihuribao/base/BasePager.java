package com.sweet.zhihuribao.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sweet.zhihuribao.R;
import com.sweet.zhihuribao.activity.ContentActivity;
import com.sweet.zhihuribao.adapter.MyAdapter;
import com.sweet.zhihuribao.bean.ZhiM;
import com.sweet.zhihuribao.utils.Image_sp;
import com.sweet.zhihuribao.utils.PrefUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Sweet on 2016/4/6/0006.
 */
public abstract class BasePager {

    public String titleTiem;
    public String titleDate;
    public Context mContent;
    public View mRootView;
    public ZhiM zhData;
    public ArrayList<ZhiM.Item> stories;
    public RecyclerView mList;
    public LinearLayoutManager layoutManager;
    public MyAdapter mAdapter;
    public SwipeRefreshLayout swipeLayout;
    public boolean isRefrush = false;
    private ViewPager viewById;

    public BasePager(Context context) {
        mContent = context;
        initViews();
    }

    /**
     * 获取当前时间
     */
    public void initDate(int i) {
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, i);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        titleTiem = format.format(calendar.getTime());
        SimpleDateFormat title = new SimpleDateFormat("yyyy年-MM月-dd日");
        titleDate = title.format(calendar.getTime());
    }

    /**
     * 初始化界面
     */
    public void initViews() {
        mRootView = View.inflate(mContent, R.layout.list_item, null);
        mList = (RecyclerView) mRootView.findViewById(R.id.rv_list);

        layoutManager = new LinearLayoutManager(mContent);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mList.setLayoutManager(layoutManager);



        swipeLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.sr_refresh);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefrush) {
                    initData();
                    isRefrush = true;
                }
            }
        });

    }

    /**
     * 初始化数据
     */
    public abstract void initData();


    /**
     * 初始化事件
     */
    public void initEvent() {

    }

    public void showSnackbar(String des) {
        Snackbar.make(mRootView, des, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 设置阅读后的条目颜色
     *
     * @param view
     */
    public void setReadItemColor(View view) {
        TextView title = (TextView) view.findViewById(R.id.tv_head);
        title.setTextColor(Color.GRAY);
    }

    /**
     * 从网络获取数据
     */
    public void getDataFromSever(String date) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, Image_sp.oldUrl + date, new RequestCallBack<String>() {
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
                }
                showSnackbar("解析数据异常，请检查网络连接...");
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

        mAdapter.setmOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(mContent, ContentActivity.class);
                intent.putExtra("id", zhData.stories.get(position).id + "");
                mContent.startActivity(intent);

                String ids = PrefUtils.getString(mContent, "read_ids", "");
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
