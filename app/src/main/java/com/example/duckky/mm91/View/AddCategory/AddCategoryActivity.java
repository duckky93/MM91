package com.example.duckky.mm91.View.AddCategory;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duckky.mm91.Database.SQLiteHelper;
import com.example.duckky.mm91.Entity.Category;
import com.example.duckky.mm91.R;
import com.example.duckky.mm91.Utils.Constance;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCategoryActivity extends AppCompatActivity {

    private final String TAG = "AddCategoryActivity";

    @BindView(R.id.category_name_edt)
    EditText edtCategoryName;

    @BindView(R.id.category_image_imv)
    ImageView imvCategoryImage;

    String selectedImagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constance.REQUEST_CODE_SELECT_PICTURE:
                    Uri selectedImageUri = data.getData();
                    selectedImagePath = getPath(selectedImageUri);
                    Log.d(TAG, "onActivityResult: " + selectedImagePath);
                    Picasso.with(AddCategoryActivity.this).load(selectedImageUri).centerCrop().fit().into(imvCategoryImage);
                    break;
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    @OnClick(R.id.category_image_imv)
    public void onCategoryImageClick() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constance.REQUEST_CODE_SELECT_PICTURE);
    }

    @OnClick(R.id.ok_bt)
    public void onOKClick() {
        String categoryName = edtCategoryName.getText().toString().trim();
        SQLiteHelper.getInstance(this).insertCategory(categoryName, selectedImagePath);
        setResult(RESULT_OK);
        finish();
    }

    @OnClick(R.id.cancel_bt)
    public void onCancelClick() {
        finish();
    }
}
