package com.example.duckky.mm91.Entity;

/**
 * Created by DuckKy on 1/29/2017.
 */

public class Product_Place {
    private int id;
    private int productID;
    private int placeID;

    public Product_Place(int id, int productID, int placeID) {
        this.id = id;
        this.productID = productID;
        this.placeID = placeID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getPlaceID() {
        return placeID;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }
}
