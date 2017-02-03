package com.example.duckky.mm91.Database;

import android.graphics.Bitmap;

/**
 * Created by DuckKy on 1/29/2017.
 */

public class Category {
    private int id;
    private String categoryName;
    private Bitmap categoryImage;

    public Category(int id, String categoryName, Bitmap categoryImage) {
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

    public Bitmap getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(Bitmap categoryImage) {
        this.categoryImage = categoryImage;
    }
}
