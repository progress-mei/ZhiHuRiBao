package com.sweet.zhihuribao.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.sweet.zhihuribao.bean.ZhiM;

import java.util.List;

/**
 * Created by Sweet on 2016/4/22/0022.
 */
public class HeadTopViewPager extends PagerAdapter {
    private final Context mContent;
    private  List<ZhiM.TopStoriesBean> top_stories;

    private OnItemClickListener mOnItemClickListener;

    public HeadTopViewPager(List<ZhiM.TopStoriesBean> top_stories, Context mContent) {
        this.top_stories = top_stories;
        this.mContent = mContent;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ZhiM.TopStoriesBean topStoriesBean = top_stories.get(position);
        final ImageView imageView = new ImageView(mContent);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        BitmapUtils bitmapUtils = new BitmapUtils(mContent);
        bitmapUtils.display(imageView,topStoriesBean.image);
        container.addView(imageView);

        if (mOnItemClickListener != null){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = position;
                    mOnItemClickListener.onItemClick(imageView, pos);
                }
            });
        }


        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return top_stories.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
