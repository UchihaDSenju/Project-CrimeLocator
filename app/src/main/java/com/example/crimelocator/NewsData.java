package com.example.crimelocator;

import android.graphics.Bitmap;

public class NewsData {
    private String newsId;
    private  String newsTitle;
    private String newsTime;
    private String newsDesc;
    private Bitmap[] newsImage;
    private String email;
    private boolean isAdmin;


    public NewsData(String newsTitle, String newsTime, Bitmap[] newsImage, String newsDesc, String newsId, String email, boolean isAdmin) {
        this.newsTitle = newsTitle;
        this.newsTime = newsTime;
        this.newsImage = newsImage;
        this.newsDesc = newsDesc;
        this.newsId = newsId;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public String getNewsDesc() {
        return newsDesc;
    }

    public String getEmail() {
        return email;
    }

    public String getNewsId() {
        return newsId;
    }

    public Bitmap getNewsImage() {
        return newsImage[0];
    }

}
