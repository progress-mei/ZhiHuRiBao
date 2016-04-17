package com.sweet.suibi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.sweet.suibi.R;
import com.sweet.suibi.bean.NoteContext;

import java.util.ArrayList;

/**
 * Created by Sweet on 2016/4/14/0014.
 */
public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<NoteContext> noteContexts;
    private View view;
    private ViewHolder viewHolder;
    private ViewHolder holder;

    public MyAdapter(Context mContext, ArrayList<NoteContext> lists) {
        this.mContext = mContext;
        noteContexts = lists;
    }


    @Override
    public int getCount() {
        if (noteContexts == null) {
            return 0;
        } else {
            return noteContexts.size();
        }
    }

    @Override
    public NoteContext getItem(int position) {
        return noteContexts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.item, null);
            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.tv_title);
            holder.date = (TextView) view.findViewById(R.id.tv_date);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.title.setText(getItem(position).getBody());
        holder.date.setText(getItem(position).getDate());
        return view;
    }

    class ViewHolder {
        TextView title;
        TextView date;
    }
}
