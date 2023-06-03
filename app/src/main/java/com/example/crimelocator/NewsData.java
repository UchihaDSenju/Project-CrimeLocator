package com.example.crimelocator;

import android.graphics.Bitmap;

public class NewsData {
    private  String newsTitle;
    private String newsTime;
    private String newsDesc;
    private Bitmap[] newsImage;


    public NewsData(String newsTitle, String newsTime, Bitmap[] newsImage, String newsDesc) {
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

    public Bitmap getNewsImage() {
        return newsImage[0];
    }

    public void setNewsImage(Bitmap[] newsImage) {
        this.newsImage = newsImage;
    }
}
