package com.example.duckky.mm91.Entity;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by DuckKy on 1/29/2017.
 */

public class Place implements Serializable {
    private int id;
    private String placeName;
    private Bitmap placeImage;

    public Place(int id, String placeName, Bitmap placeImage) {
        this.id = id;
        this.placeName = placeName;
        this.placeImage = placeImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Bitmap getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(Bitmap placeImage) {
        this.placeImage = placeImage;
    }
}
