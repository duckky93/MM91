package com.example.duckky.mm91;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duckky.mm91.Database.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DuckKy on 1/31/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    List<Product> products;
    LayoutInflater inflater;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
        this.inflater = LayoutInflater.from(context);
    }

    public void setProducts(List<Product> products){
        this.products.clear();
        this.products.addAll(products);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_product_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productImv.setImageBitmap(product.getProductImage());
        holder.productName.setText(product.getProductName());
        holder.description.setText(product.getProductDescription());
        holder.localPrice.setText(String.format(context.getString(R.string.price_type),product.getProductLocalPrice()));
        holder.touristPrice.setText(String.format(context.getString(R.string.price_type),product.getProductTouristPrice()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.product_imv) ImageView productImv;
        @BindView(R.id.name_txt) TextView productName;
        @BindView(R.id.description_txt) TextView description;
        @BindView(R.id.local_price_txt) TextView localPrice;
        @BindView(R.id.tourist_price_txt) TextView touristPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
