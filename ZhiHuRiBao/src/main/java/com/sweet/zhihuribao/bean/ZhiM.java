package com.sweet.zhihuribao.bean;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sweet on 2016/3/26/0026.
 */
public class ZhiM {
    public String date;
    public ArrayList<Item> stories;

    public class Item {
        public String id;
        public String ga_prefix;
        public String title;
        public String[] images;

        @Override
        public String toString() {
            return "Item{" +
                    "id='" + id + '\'' +
                    ", images=" + Arrays.toString(images) +
                    ", title='" + title + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "ZhiM{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                '}';
    }
}
