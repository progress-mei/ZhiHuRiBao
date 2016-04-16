package com.sweet.zhihuribao.pager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sweet.zhihuribao.activity.ContentActivity;
import com.sweet.zhihuribao.adapter.MyAdapter;
import com.sweet.zhihuribao.base.BasePager;
import com.sweet.zhihuribao.bean.ZhiM;
import com.sweet.zhihuribao.utils.Image_sp;
import com.sweet.zhihuribao.utils.PrefUtils;

/**
 * Created by Sweet on 2016/4/6/0006.
 */
public class Pager1 extends BasePager {

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

    @Override
    public void initData() {
        getDataFromSever();
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
