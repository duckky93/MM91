package com.example.duckky.mm91.View.Category.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duckky.mm91.Entity.Category;
import com.example.duckky.mm91.R;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by DuckKy on 2/4/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int FOOTER_VIEW = 1;

    Context context;
    List<Category> categories;
    LayoutInflater inflater;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
        this.inflater = LayoutInflater.from(context);
    }

    public void setCategories(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case FOOTER_VIEW:
                view = inflater.inflate(R.layout.layout_empty_data, parent, false);
                FooterView footerView = new FooterView(view);
                return footerView;
            default:
                view = inflater.inflate(R.layout.layout_category_filter, parent, false);
                return new FooterView(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (categories == null) {
            return 0;
        }

        if (categories.size() == 0) {
            return 1;
        }

        return categories.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FooterView extends RecyclerView.ViewHolder {
        public FooterView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
