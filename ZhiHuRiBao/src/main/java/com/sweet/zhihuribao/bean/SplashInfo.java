package com.sweet.zhihuribao.bean;

/**
 * Created by Sweet on 2016/3/26/0026.
 */
public class SplashInfo {
    private String text;
    private String img;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "SplashInfo{" +
                "img='" + img + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public SplashInfo(String img, String text) {
        this.img = img;
        this.text = text;
    }

}
