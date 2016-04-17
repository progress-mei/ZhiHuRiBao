package com.sweet.suibi.bean;

/**
 * Created by Sweet on 2016/4/15/0015.
 */
public class NoteContext {
    private String body;

    private String date;

    private String md5;

    @Override
    public String toString() {
        return "NoteContext{" +
                "body='" + body + '\'' +
                ", date='" + date + '\'' +
                ", md5='" + md5 + '\'' +
                '}';
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
