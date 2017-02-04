package com.example.duckky.mm91.Entity;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by DuckKy on 1/29/2017.
 */

public class Category implements Serializable {
    private int id;
    private String categoryName;
    private String categoryImage;

    public Category(int id, String categoryName, String categoryImage) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
}
