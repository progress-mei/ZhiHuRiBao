package com.sweet.zhihuribao.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sweet on 2016/3/26/0026.
 */
public class ZhiM {
    public String date;
    //列表的bean
    public ArrayList<Item> stories;
    //顶部viewpager的bean
    public List<TopStoriesBean> top_stories;

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public class TopStoriesBean {
        public String title;
        public String image;
        public int id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

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
