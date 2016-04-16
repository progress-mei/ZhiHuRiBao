package com.sweet.zhihuribao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.sweet.zhihuribao.R;
import com.sweet.zhihuribao.activity.MainActivity;
import com.sweet.zhihuribao.bean.ZhiM;
import com.sweet.zhihuribao.utils.PrefUtils;

import java.util.ArrayList;

/**
 * Created by Sweet on 2016/4/6/0006.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public ArrayList<ZhiM.Item> stories;
    public Context mAcivity;
    private MyViewHolder holder;

    public MyAdapter(Context context, ArrayList<ZhiM.Item> stories) {
        this.mAcivity = context;
        this.stories = stories;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_item, null);
        holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        ZhiM.Item item = stories.get(position);
        BitmapUtils bitmapUtils = new BitmapUtils(mAcivity);
        bitmapUtils.display(holder.img, item.images[0]);
        holder.title.setText(item.title);

        //记录listview是否被点击并设置颜色
        String read_ids = PrefUtils.getString(mAcivity, "read_ids", "");
        if (read_ids.contains(item.id)) {
            holder.title.setTextColor(Color.GRAY);
        } else {
            holder.title.setTextColor(Color.BLACK);
        }

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return stories.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.iv_news);
            title = (TextView) itemView.findViewById(R.id.tv_head);

        }
    }

}
