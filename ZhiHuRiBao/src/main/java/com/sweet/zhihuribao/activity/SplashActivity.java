package com.sweet.zhihuribao.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sweet.zhihuribao.R;
import com.sweet.zhihuribao.bean.SplashInfo;

/**
 * 闪屏页
 * Created by Sweet on 2016/3/26/0026.
 */
public class SplashActivity extends Activity {

    private static final int ANIMATION_DURATION = 2000;
    private static final float SCALE_END = 1.13F;

    private Drawable splashIv;
    private ImageView iv_splash;
    private TextView tv_anthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iv_splash = (ImageView) findViewById(R.id.iv_splash);
        tv_anthor = (TextView) findViewById(R.id.tv_author);

        //随机选择背景图片
//        Random r = new Random(SystemClock.elapsedRealtime());
//        iv_splash.setImageResource(Image_sp.SPLASH_ARRAY[r.nextInt(Image_sp.SPLASH_ARRAY.length)]);
//        getDataFromSever();
        animateImage();

    }

    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(iv_splash, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(iv_splash, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    /**
     * 从服务器获取闪屏页
     */
    public void getDataFromSever() {

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, "http://news-at.zhihu.com/api/4/start-image/1080*1776",
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;

                        parseData(result);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                    }

                });

    }

    /**
     * 解析闪屏页 并设置数据
     *
     * @param result
     */
    public void parseData(String result) {

        Gson gson = new Gson();
        SplashInfo Splashdata = gson.fromJson(result, SplashInfo.class);
        tv_anthor.setText(Splashdata.getText());
        BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
        bitmapUtils.display(iv_splash, Splashdata.getImg());
    }
}
