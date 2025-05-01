package com.indranugraha.myapplication;

public class DataNews {
    private String title;
    private String content;
    private String imageUrl;
    private String linkNews;

    public DataNews(String title, String content, String imageUrl, String linkNews) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.linkNews = linkNews;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLinkNews() {
        return linkNews;
    }
}
