package com.sweet.zhihuribao.utils.support;

import java.util.ArrayList;
import java.util.List;

public final class DailyNews {
    private boolean isMulti;
    private String questionTitle;
    private String questionUrl;
    private String dailyTitle;
    private List<String> questionTitleList = new ArrayList<>();
    private List<String> questionUrlList = new ArrayList<>();
    private String thumbnailUrl;
    private String date;

    public DailyNews() {

    }

    //Copy constructor
    public DailyNews(DailyNews dailyNews) {
        this.isMulti = dailyNews.isMulti;
        this.questionTitle = dailyNews.questionTitle;
        this.questionUrl = dailyNews.questionUrl;
        this.dailyTitle = dailyNews.dailyTitle;

        List<String> tempQuestionTitleList = new ArrayList<>();

        for (String aQuestionTitleList : dailyNews.questionTitleList) {
            tempQuestionTitleList.add(aQuestionTitleList);
        }
        this.questionTitleList = tempQuestionTitleList;

        List<String> tempQuestionUrlList = new ArrayList<>();

        for (String aQuestionUrlList : dailyNews.questionUrlList) {
            tempQuestionUrlList.add(aQuestionUrlList);
        }
        this.questionUrlList = tempQuestionUrlList;

        this.thumbnailUrl = dailyNews.thumbnailUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyNews dailyNews = (DailyNews) o;

        return dailyTitle.equals(dailyNews.dailyTitle);
    }

    @Override
    public int hashCode() {
        return dailyTitle.hashCode();
    }

    public String getQuestionUrl() {
        return questionUrl;
    }

    public void setQuestionUrl(String questionUrl) {
        this.questionUrl = questionUrl;
    }

    public boolean isMulti() {
        return isMulti;
    }

    public void setMulti(boolean isMulti) {
        this.isMulti = isMulti;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getDailyTitle() {
        return dailyTitle;
    }

    public void setDailyTitle(String dailyTitle) {
        this.dailyTitle = dailyTitle;
    }

    public List<String> getQuestionTitleList() {
        return questionTitleList;
    }

    public List<String> getQuestionUrlList() {
        return questionUrlList;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void addQuestionTitle(String questionTitle) {
        questionTitleList.add(questionTitle);
    }

    public void addQuestionUrl(String questionUrl) {
        questionUrlList.add(questionUrl);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
