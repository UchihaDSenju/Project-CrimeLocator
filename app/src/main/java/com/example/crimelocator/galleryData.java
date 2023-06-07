package com.example.crimelocator;

import android.graphics.Bitmap;

public class galleryData {

    private Bitmap galleryImage;

    public galleryData(Bitmap galleryImage) {
        this.galleryImage = galleryImage;
    }

    public Bitmap getGalleryImage() {
        return galleryImage;
    }

    public void setGalleryImage(Bitmap galleryImage) {
        this.galleryImage = galleryImage;
    }
}
