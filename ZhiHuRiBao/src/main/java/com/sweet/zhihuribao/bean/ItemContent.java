package com.sweet.zhihuribao.bean;

import java.util.List;

/**
 * Created by Sweet on 2016/4/15/0015.
 */
public class ItemContent {
    /**
     * image_source : Angel Abril Ruiz / CC BY
     * title : 卖衣服的新手段：把耐用品变成「不停买新的」
     * image : http://p4.zhimg.com/30/59/30594279d368534c6c2f91b2c00c7806.jpg
     * share_url : http://daily.zhihu.com/story/3892357
     * js : []
     * ga_prefix : 050615
     * images : ["http://p3.zhimg.com/69/d0/69d0ab1bde1988bd475bc7e0a25b713e.jpg"]
     * type : 0
     * id : 3892357
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String type;
    private String id;
    private List<String> images;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

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

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
//        @SerializedName("id")
//        public int id;
//        @SerializedName("type")
//        public int type;
//        @SerializedName("title")
//        public String title;
//        @SerializedName("image")
//        public String image;
//        @SerializedName("image_source")
//        public String imageSource;
//        @SerializedName("body")
//        public String body;
//        @SerializedName("share_url")
//        public String shareUrl;
//        @SerializedName("css")
//        public String[] css;
//        @SerializedName("js")
//        public String[] js;
//        @SerializedName("ga_prefix")
//        public String gaPrefix;


}
