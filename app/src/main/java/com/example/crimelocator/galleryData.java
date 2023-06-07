package com.example.crimelocator;

import android.graphics.Bitmap;

public class galleryData {

    private Bitmap galleryImage;
    private String desc;

    public galleryData(Bitmap galleryImage, String desc) {
        this.galleryImage = galleryImage;
        this.desc = desc;
    }

    public Bitmap getGalleryImage() {
        return galleryImage;
    }

    public String getDesc() {
        return desc;
    }
}
