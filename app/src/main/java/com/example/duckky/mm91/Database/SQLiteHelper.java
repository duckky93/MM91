package com.example.duckky.mm91.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duckky.mm91.Entity.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DuckKy on 1/29/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    //=====TABLE CATEGORY===========================================================================
    private static final String TABLE_CATEGORY = "table_category";

    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_CATEGORY_NAME = "category_name";
    private static final String KEY_CATEGORY_IMAGE = "category_image";

    //=====TABLE PRODUCT============================================================================
    private static final String TABLE_PRODUCT = "table_product";

    private static final String KEY_PRODUCT_ID = "product_id";
    private static final String KEY_PRODUCT_NAME = "product_name";
    private static final String KEY_PRODUCT_DESCRIPTION = "product_description";
    private static final String KEY_PRODUCT_LOCAL_PRICE = "product_name_local_price";
    private static final String KEY_PRODUCT_TOURIST_PRICE = "product_name_tourist_price";
    private static final String KEY_PRODUCT_IMAGE = "product_image";
    private static final String KEY_PRODUCT_CATEGORY_ID = "product_category_id";

    //=====TABLE PRODUCT PLACE======================================================================
    private static final String TABLE_PRODUCT_PLACE = "table_product_place";

    private static final String KEY_PRODUCT_PLACE_ID = "key_product_place_id";
    private static final String KEY_PRODUCT_PLACE_PRODUCT_ID = "key_product_place_product_id";
    private static final String KEY_PRODUCT_PLACE_PLACE_ID = "key_product_place_place_id";

    //=====TABLE PLACE==============================================================================
    private static final String TABLE_PLACE = "table_place";

    private static final String KEY_PLACE_ID = "place_id";
    private static final String KEY_PLACE_NAME = "place_name";
    private static final String KEY_PLACE_IMAGE = "place_image";

    //=====SQL EXECUTE STRING=======================================================================
    private static final String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "(" +
            KEY_PRODUCT_ID + " INTEGER PRIMARY KEY," +
            KEY_PRODUCT_NAME + " TEXT, " +
            KEY_PRODUCT_DESCRIPTION + " TEXT, " +
            KEY_PRODUCT_LOCAL_PRICE + " DOUBLE," +
            KEY_PRODUCT_TOURIST_PRICE + " DOUBLE, " +
            KEY_PRODUCT_IMAGE + " BLOB, " +
            KEY_PRODUCT_CATEGORY_ID + " INTEGER NOT NULL"
            + ");";

    private static final String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "(" +
            KEY_CATEGORY_ID + " INTEGER PRIMARY KEY," +
            KEY_CATEGORY_NAME + " TEXT, " +
            KEY_CATEGORY_IMAGE + " BLOB"
            + ");";

    private static final String CREATE_PLACE_TABLE = "CREATE TABLE " + TABLE_PLACE + "(" +
            KEY_PLACE_ID + " INTEGER PRIMARY KEY," +
            KEY_PLACE_NAME + " TEXT, " +
            KEY_PLACE_IMAGE + " BLOB"
            + ");";

    private static final String CREATE_PLACE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT_PLACE + "(" +
            KEY_PRODUCT_PLACE_ID + " INTEGER PRIMARY KEY," +
            KEY_PRODUCT_PLACE_PRODUCT_ID + " INTEGER NOT NULL, " +
            KEY_PRODUCT_PLACE_PLACE_ID + " INTEGER NOT NULL"
            + ");";

    private static final int DATABASE_VERSION = 1;

    public static SQLiteHelper instance;

    public static SQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SQLiteHelper(context, TABLE_PRODUCT, null, DATABASE_VERSION);
        }
        return instance;
    }

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_PLACE_TABLE);
        db.execSQL(CREATE_PLACE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_PLACE);
    }

    public void insertCategory(String categoryName, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_NAME, categoryName);
        values.put(KEY_CATEGORY_IMAGE, imagePath);
        db.insert(TABLE_CATEGORY, null, values);
    }

    public List<Category> getCategories(){
        List<Category> categories = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String categoryName = cursor.getString(1);
                String categoryPath = cursor.getString(2);
                Category category = new Category(id, categoryName, categoryPath);
                categories.add(category);
            } while (cursor.moveToNext());
        }
        return categories;
    }
}
