package com.example.duckky.mm91.View.Category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.duckky.mm91.Database.SQLiteHelper;
import com.example.duckky.mm91.Entity.Category;
import com.example.duckky.mm91.R;
import com.example.duckky.mm91.Utils.Constance;
import com.example.duckky.mm91.View.AddCategory.AddCategoryActivity;
import com.example.duckky.mm91.View.Category.Adapter.CategoryAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryFilterActivity extends AppCompatActivity implements CategoryAdapter.OnAddNewListener {

    @BindView(R.id.ok_bt)
    TextView btOK;
    @BindView(R.id.cancel_bt)
    TextView btCancel;
    @BindView(R.id.category_rv)
    RecyclerView rvCategory;

    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_filter);
        ButterKnife.bind(this);
        initAttributes();
        initViews();
    }

    private void initAttributes() {
        categoryAdapter = new CategoryAdapter(this, new ArrayList<Category>());
    }

    private void initViews() {
        categoryAdapter.setOnAddNewListener(this);
        rvCategory.setAdapter(categoryAdapter);
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter.setCategories(SQLiteHelper.getInstance(this).getCategoriesWithChosenAlready());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constance.REQUEST_CODE_ADD_CATEGORY:
                    Category category = (Category) data.getSerializableExtra(Constance.KEY_CATEGORY);
                    categoryAdapter.addNewCategory(category);
                    break;
            }
        }
    }

    @Override
    public void onAddNewListener() {
        Intent intent = new Intent(CategoryFilterActivity.this, AddCategoryActivity.class);
        startActivityForResult(intent, Constance.REQUEST_CODE_ADD_CATEGORY);
    }

    @OnClick(R.id.ok_bt)
    public void onOKClick() {

    }

    @OnClick(R.id.cancel_bt)
    public void onCancelClick() {
        finish();
    }
}
