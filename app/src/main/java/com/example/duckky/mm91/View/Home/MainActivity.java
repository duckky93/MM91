package com.example.duckky.mm91.View.Home;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duckky.mm91.Animation.AnimUtils;
import com.example.duckky.mm91.Animation.Callback;
import com.example.duckky.mm91.Entity.Product;
import com.example.duckky.mm91.View.Home.Adapter.ProductAdapter;
import com.example.duckky.mm91.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.product_recyclerView)
    RecyclerView rvProducts;

    @BindView(R.id.filter_category_fab)
    FloatingActionButton fabFilterCategory;

    @BindView(R.id.filter_place_fab)
    FloatingActionButton fabFilterPlace;

    @BindView(R.id.add_product_fab)
    FloatingActionButton fabAddProduct;

    @BindView(R.id.list_menu_fab)
    FloatingActionButton fabListMenu;

    @BindView(R.id.filter_category_txt)
    TextView tvFilterCategory;

    @BindView(R.id.filter_place_txt)
    TextView tvFilterPlace;

    @BindView(R.id.add_product_txt)
    TextView tvAddProduct;

    @BindView(R.id.filter_category_container)
    LinearLayout llCategoryContainer;

    @BindView(R.id.add_product_container)
    LinearLayout llAddProductContainer;

    @BindView(R.id.filter_place_container)
    LinearLayout llPlaceContainer;

    @BindView(R.id.cancel_bt)
    ImageView btCancel;

    @BindView(R.id.search_edt)
    EditText edtSearch;

    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initAttribute();
        initView();
    }

    private void initAttribute() {
        productAdapter = new ProductAdapter(this, new ArrayList<Product>());
    }

    private void initView() {
        rvProducts.setAdapter(productAdapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
    }

    private void onShowMenu(boolean show) {
//        llCategoryContainer.setVisibility(show?View.VISIBLE:View.GONE);
//        llPlaceContainer.setVisibility(show?View.VISIBLE:View.GONE);
//        llAddProductContainer.setVisibility(show?View.VISIBLE:View.GONE);
        tvAddProduct.setVisibility(show ? View.VISIBLE : View.GONE);
        tvFilterCategory.setVisibility(show ? View.VISIBLE : View.GONE);
        tvFilterPlace.setVisibility(show ? View.VISIBLE : View.GONE);
        fabAddProduct.setVisibility(show ? View.VISIBLE : View.GONE);
        fabFilterCategory.setVisibility(show ? View.VISIBLE : View.GONE);
        fabFilterPlace.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    boolean show = false;

    @OnTextChanged(R.id.search_edt)
    protected void onTextChanged(CharSequence text) {
        btCancel.setVisibility(text.length() > 0 ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.cancel_bt)
    public void onCancelSearchClick() {
        edtSearch.setText("");
    }

    @OnClick(R.id.list_menu_fab)
    public void onListMenuClick() {
        show = !show;
        if (show) {
            AnimUtils.with(this).load(R.anim.scale_visible).startAnimationWith(tvAddProduct);
            AnimUtils.with(this).load(R.anim.scale_visible).startAnimationWith(tvFilterCategory);
            AnimUtils.with(this).load(R.anim.scale_visible).startAnimationWith(tvFilterPlace);
            AnimUtils.with(this).load(R.anim.scale_visible).startAnimationWith(fabAddProduct);
            AnimUtils.with(this).load(R.anim.scale_visible).startAnimationWith(fabFilterCategory);
            AnimUtils.with(this).load(R.anim.scale_visible).addOnAnimationStart(new Callback.OnAnimationStart() {
                @Override
                public void onAnimationStart(Animation animation) {
                    onShowMenu(true);
                }
            }).startAnimationWith(fabFilterPlace);
        } else {
            AnimUtils.with(this).load(R.anim.scale_gone).startAnimationWith(tvAddProduct);
            AnimUtils.with(this).load(R.anim.scale_gone).startAnimationWith(tvFilterCategory);
            AnimUtils.with(this).load(R.anim.scale_gone).startAnimationWith(tvFilterPlace);
            AnimUtils.with(this).load(R.anim.scale_gone).startAnimationWith(fabAddProduct);
            AnimUtils.with(this).load(R.anim.scale_gone).startAnimationWith(fabFilterCategory);
            AnimUtils.with(this).load(R.anim.scale_gone).addOnAnimationEnd(new Callback.OnAnimationEnd() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    onShowMenu(false);
                }
            }).startAnimationWith(fabFilterPlace);
        }
    }

    @OnClick(R.id.filter_place_fab)
    public void fabFilterPlaceClick() {
        Toast.makeText(this, "Place", Toast.LENGTH_SHORT).show();
    }

}
