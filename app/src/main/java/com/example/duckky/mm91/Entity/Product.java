package com.example.duckky.mm91.Entity;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by DuckKy on 1/29/2017.
 */

public class Product implements Serializable {

    private int id;
    private String productName;
    private String productDescription;
    private double productLocalPrice;
    private double productTouristPrice;
    private Bitmap productImage;
    private int categoryID;

    public Product(int id, String productName, String productDescription, double productLocalPrice, double productTouristPrice, Bitmap productImage, int categoryID) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productLocalPrice = productLocalPrice;
        this.productTouristPrice = productTouristPrice;
        this.productImage = productImage;
        this.categoryID = categoryID;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductLocalPrice() {
        return productLocalPrice;
    }

    public void setProductLocalPrice(double productLocalPrice) {
        this.productLocalPrice = productLocalPrice;
    }

    public double getProductTouristPrice() {
        return productTouristPrice;
    }

    public void setProductTouristPrice(double productTouristPrice) {
        this.productTouristPrice = productTouristPrice;
    }

    public Bitmap getProductImage() {
        return productImage;
    }

    public void setProductImage(Bitmap productImage) {
        this.productImage = productImage;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
