package com.example.duckky.mm91.View.Category.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duckky.mm91.Entity.Category;
import com.example.duckky.mm91.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DuckKy on 2/4/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER_VIEW = 0;
    private static final int NORMAL_VIEW = 1;
    private static final int FOOTER_VIEW = 2;

    Context context;
    List<Category> categories;
    LayoutInflater inflater;
    List<Category> chosenCategories;
    OnAddNewListener onAddNewListener;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
        this.inflater = LayoutInflater.from(context);
        this.chosenCategories = new ArrayList<>();
    }

    public void setCategories(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        this.notifyDataSetChanged();
    }

    public void setOnAddNewListener(OnAddNewListener onAddNewListener) {
        this.onAddNewListener = onAddNewListener;
    }

    public void addNewCategory(Category category) {
        categories.add(category);
        notifyDataSetChanged();
    }

    public void chooseAll() {
        this.chosenCategories.clear();
        this.chosenCategories.addAll(this.categories);
    }

    public List<Category> getChosenCategories() {
        return this.chosenCategories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case HEADER_VIEW:
                view = inflater.inflate(R.layout.layout_empty_data, parent, false);
                HeaderView headerView = new HeaderView(view);
                Log.d(TAG, "onCreateViewHolder: headerview");
                return headerView;
            case NORMAL_VIEW:
                view = inflater.inflate(R.layout.layout_category_filter_item, parent, false);
                Log.d(TAG, "onCreateViewHolder: normalview");
                return new NormalView(view);
            case FOOTER_VIEW:
                view = inflater.inflate(R.layout.layout_add_new, parent, false);
                Log.d(TAG, "onCreateViewHolder: footerview");
                return new FooterView(view);
            default:
                Log.d(TAG, "onCreateViewHolder: default");
                return null;
        }
    }

    private final String TAG = "CategoryAdapter";

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        }
        if (position == categories.size() + 1) {
            return FOOTER_VIEW;
        }
        return NORMAL_VIEW;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int i) {
        final int position = i - 1;
        if (holder instanceof NormalView) {
            try {
                final NormalView mHolder = (NormalView) holder;
                final Category category = categories.get(position);
                Picasso.with(context).load(category.getCategoryImage()).centerCrop().into(mHolder.imvCategory);
                mHolder.tvCategoryName.setText(categories.get(position).getCategoryName());
                mHolder.cbCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        onChooseCategory(isChecked, position);
                    }
                });
                mHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Category Item Clicked
                        mHolder.cbCategory.setChecked(!mHolder.cbCategory.isChecked());
                        onChooseCategory(mHolder.cbCategory.isChecked(), position);
                    }
                });
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        if (holder instanceof FooterView) {
            final FooterView mHolder = (FooterView) holder;
            mHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Add New Clicked
                    if (onAddNewListener != null) {
                        onAddNewListener.onAddNewListener();
                    }
                }
            });
        }
    }

    private void onChooseCategory(boolean isChoose, int position) {
        if (isChoose) {
            chosenCategories.add(categories.get(position));
        } else {
            if (chosenCategories.contains(categories.get(position))) {
                chosenCategories.remove(categories.get(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return categories.size() + 2;
    }

    public class NormalView extends RecyclerView.ViewHolder {
        @BindView(R.id.category_imv)
        ImageView imvCategory;
        @BindView(R.id.category_name_tv)
        TextView tvCategoryName;
        @BindView(R.id.category_cb)
        CheckBox cbCategory;
        View view;

        public NormalView(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }

    public class HeaderView extends RecyclerView.ViewHolder {
        View view;

        public HeaderView(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public class FooterView extends RecyclerView.ViewHolder {
        View view;

        public FooterView(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }

    public interface OnAddNewListener {
        void onAddNewListener();
    }
}
