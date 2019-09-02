package com.example.a125688.americano;

import android.media.MediaMetadataRetriever;

/**
 * Created by Cyprion Puli on 3/12/2018.
 */

public class Product {
    String description;
    Integer no;

    public Product() {
    }

    public Product(String description, Integer no) {
        this.description = description;
        this.no = no;
    }

    public String getDescription() {
        return description;
    }

    public Integer getNo() {
        return no;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNo(Integer no) {
        this.no = no;
    }
}
