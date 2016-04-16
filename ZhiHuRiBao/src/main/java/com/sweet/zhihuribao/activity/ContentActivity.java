package com.sweet.zhihuribao.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sweet.zhihuribao.R;
import com.sweet.zhihuribao.base.BaseActivity;
import com.sweet.zhihuribao.bean.ItemContent;
import com.sweet.zhihuribao.utils.Image_sp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sweet on 2016/4/15/0015.
 */
public class ContentActivity extends BaseActivity {

    private WebView webView;
    private ItemContent content;
    private String body;
    private ImageView headPic;
    private TextView headTitle;
    private TextView headSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        layoutResID = R.layout.activity_content;
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        initData();
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.wv_web);
        headPic = (ImageView) findViewById(R.id.iv_img);
        headTitle = (TextView) findViewById(R.id.tv_Title);
        headSource = (TextView) findViewById(R.id.tv_Source);
    }

    private void initData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        getDataFromSever(id);
    }

    public void getDataFromSever(String id) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, Image_sp.CONTENT_URL + id, new RequestCallBack<String>() {

            private String date;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                date = responseInfo.result;
                System.out.println(date);
                if (date != null) {
                    praseData(date);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

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
        content = gson.fromJson(result, ItemContent.class);
        body = content.getBody();
        initWeb();
        headTitle.setText(content.getTitle());
        headSource.setText(content.getImage_source());
        BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
        bitmapUtils.display(headPic, content.getImage());
    }

    private void initWeb() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        webView.loadDataWithBaseURL(null, body, "text/html", "utf8", null);
    }
}
