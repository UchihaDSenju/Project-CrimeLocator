package com.example.crimelocator;

import android.graphics.Bitmap;

public class NewsData {
    private String newsId;
    private  String newsTitle;
    private String newsTime;
    private String newsDesc;
    private Bitmap[] newsImage;
    private String email;


    public NewsData(String newsTitle, String newsTime, Bitmap[] newsImage, String newsDesc, String newsId, String email) {
        this.newsTitle = newsTitle;
        this.newsTime = newsTime;
        this.newsImage = newsImage;
        this.newsDesc = newsDesc;
        this.newsId = newsId;
        this.email = email;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

//    public void setNewsTitle(String newsTitle) {
//        this.newsTitle = newsTitle;
//    }

    public String getNewsTime() {
        return newsTime;
    }

//    public void setNewsTime(String newsTime) {
//        this.newsTime = newsTime;
//    }

    public String getNewsDesc() {
        return newsDesc;
    }

    public String getEmail() {
        return email;
    }

    public String getNewsId() {
        return newsId;
    }

//    public void setNewsId(String newsId) {
//        this.newsId = newsId;
//    }
//
//    public void setNewsDesc(String newsDesc) {
//        this.newsDesc = newsDesc;
//    }

    public Bitmap getNewsImage() {
        return newsImage[0];
    }

//    public void setNewsImage(Bitmap[] newsImage) {
//        this.newsImage = newsImage;
//    }
}
