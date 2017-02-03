package com.example.duckky.mm91;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.duckky.mm91.Database.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.product_recyclerView)
    RecyclerView rvProducts;

    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initAttribute();
        initView();
    }

    private void initAttribute(){
        productAdapter = new ProductAdapter(this,new ArrayList<Product>());
    }

    private void initView(){
        rvProducts.setAdapter(productAdapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
    }
}
