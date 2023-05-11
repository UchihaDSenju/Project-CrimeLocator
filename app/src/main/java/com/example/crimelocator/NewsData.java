package com.example.crimelocator;

public class NewsData {
    private  String newsDescription;
    private String newsTime;
    private int newsImage;

    public NewsData(String newsDescription, String newsTime, int newsImage) {
        this.newsDescription = newsDescription;
        this.newsTime = newsTime;
        this.newsImage = newsImage;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public int getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(int newsImage) {
        this.newsImage = newsImage;
    }
}
