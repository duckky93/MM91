package com.example.duckky.mm91.View.AddCategory;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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

import java.io.FileNotFoundException;
import java.io.InputStream;

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

    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        ButterKnife.bind(this);
        alert = new AlertDialog.Builder(this).setTitle("Select").setNegativeButton(
                "Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, Constance.REQUEST_CODE_SELECT_PICTURE);
                    }
                }
        ).setPositiveButton(
                "Take Image", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, Constance.REQUEST_CODE_TAKE_IMAGE);
                        }
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = null;
            switch (requestCode) {
                case Constance.REQUEST_CODE_SELECT_PICTURE:
                    selectedImageUri = data.getData();
                    selectedImagePath = getRealPathFromURI(selectedImageUri);
                    Log.d(TAG, "onActivityResult: " + selectedImagePath);
                    Picasso.with(AddCategoryActivity.this).load(selectedImageUri).centerCrop().fit().into(imvCategoryImage);
                    break;
                case Constance.REQUEST_CODE_TAKE_IMAGE:
                    selectedImageUri = data.getData();
                    try {
                        selectedImagePath = getRealPathFromURI(selectedImageUri);
                        Log.d(TAG, "onActivityResult: " + selectedImagePath);
                        Picasso.with(AddCategoryActivity.this).load(selectedImageUri).centerCrop().fit().into(imvCategoryImage);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        final Uri imageUri = data.getData();
                        final InputStream imageStream;
                        Bundle extras = data.getExtras();
                        final Bitmap selectedImage = (Bitmap) extras.get("data");
                        imvCategoryImage.setImageBitmap(selectedImage);
                    }
                    break;
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @OnClick(R.id.category_image_imv)
    public void onCategoryImageClick() {
        alert.show();
    }

    @OnClick(R.id.ok_bt)
    public void onOKClick() {
        String categoryName = edtCategoryName.getText().toString().trim();
        Log.d(TAG, "selectedImagePath: " + selectedImagePath);
        SQLiteHelper.getInstance(this).insertCategory(categoryName, selectedImagePath);
        Intent resultIntent = new Intent();
        Category category = SQLiteHelper.getInstance(this).getLastCategory();
        category.setChosen(true);
        resultIntent.putExtra(Constance.KEY_CATEGORY, category);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @OnClick(R.id.cancel_bt)
    public void onCancelClick() {
        finish();
    }

}
