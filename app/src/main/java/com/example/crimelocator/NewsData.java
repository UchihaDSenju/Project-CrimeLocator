package com.example.crimelocator;

public class NewsData {
    private  String newsTitle;
    private String newsTime;
    private String newsDesc;
    private int newsImage;

    public NewsData(String newsTitle, String newsTime, int newsImage, String newsDesc) {
        this.newsTitle = newsTitle;
        this.newsTime = newsTime;
        this.newsImage = newsImage;
        this.newsDesc = newsDesc;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public String getNewsDesc() {
        return newsDesc;
    }

    public void setNewsDesc(String newsDesc) {
        this.newsDesc = newsDesc;
    }

    public int getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(int newsImage) {
        this.newsImage = newsImage;
    }
}
