package com.sweet.zhihuribao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sweet.zhihuribao.R;
import com.sweet.zhihuribao.adapter.MyAdapter;
import com.sweet.zhihuribao.base.BaseActivity;
import com.sweet.zhihuribao.bean.ZhiM;
import com.sweet.zhihuribao.utils.CacheUtils;
import com.sweet.zhihuribao.utils.Image_sp;
import com.sweet.zhihuribao.utils.PrefUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Sweet on 2016/4/6/0006.
 */
public class ChooseItemActivity extends BaseActivity {

    private RecyclerView mList;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeLayout;
    private boolean isRefrush = false;
    private ZhiM zhData;
    private ArrayList<ZhiM.Item> stories;
    private MyAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        layoutResID = R.layout.choosed_item;
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        initData();

    }

    private void initView() {


        mList = (RecyclerView) findViewById(R.id.rv_list);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mList.setLayoutManager(layoutManager);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.sr_refresh);

        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                isRefrush = true;
            }
        });
    }

    private void initData() {
        String chooseDate = getIntent().getStringExtra("Choose_Date");

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        String currentDate = format.format(calendar.getTime());
        System.out.println("Choose_Date" + chooseDate + "Current_date" + currentDate);
        if (!TextUtils.isEmpty(chooseDate)) {
            if (!(chooseDate.equals(currentDate))) {
                getCache(chooseDate);
//                getDataFromSever(chooseDate);
            } else {
                String cache = CacheUtils.getCache(Image_sp.zhihuItem, getApplicationContext());
                if (!TextUtils.isEmpty(cache)){
                    praseData(cache);
                }
                getDataFromSever();
            }
        }
    }

    public void getCache(String date) {
        String cache = CacheUtils.getCache(Image_sp.oldUrl + date, getApplicationContext());
        if (!TextUtils.isEmpty(cache)) {
            praseData(cache);
        }
        getDataFromSever(date);
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
    public void getDataFromSever(final String date) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, Image_sp.oldUrl + date, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                praseData(result);
                CacheUtils.setCache(Image_sp.oldUrl + date, result, getApplicationContext());
                if (isRefrush) {
                    showSnackbar("刷新成功啦...");
                    swipeLayout.setRefreshing(false);
                    isRefrush = false;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                System.out.println(e + s);
                if (isRefrush) {
                    Toast.makeText(ChooseItemActivity.this, "刷新成功啦...", Toast.LENGTH_SHORT).show();
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
        mAdapter = new MyAdapter(ChooseItemActivity.this, stories);
        mList.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                String ids = PrefUtils.getString(ChooseItemActivity.this, "read_ids", "");

                if (!ids.contains(zhData.stories.get(position).id)) {
                    String read_ids = ids + zhData.stories.get(position).id + ",";
                    PrefUtils.setString(ChooseItemActivity.this, "read_ids", read_ids);
                }
                setReadItemColor(view);
                //跳转内容详情页
                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                intent.putExtra("id", zhData.stories.get(position).id + "");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
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
                CacheUtils.setCache(Image_sp.zhihuItem, result, getApplicationContext());
                if (isRefrush) {
                    showSnackbar("刷新成功啦...");
                    swipeLayout.setRefreshing(false);
                    isRefrush = false;
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                showSnackbar("解析数据异常，请检查网络连接...");
            }
        });
    }

    public void showSnackbar(String des) {

        Snackbar.make(mList, des, Snackbar.LENGTH_SHORT).show();
    }
}
