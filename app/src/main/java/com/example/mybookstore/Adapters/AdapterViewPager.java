package com.example.mybookstore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybookstore.Models.ModelCategory;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;

import java.util.List;

public class AdapterViewPager extends PagerAdapter {

    Context context;
    List<String> strings;
    RecyclerView recyclerView;
    AdapterItemCat adapterItemCat;


    public AdapterViewPager(Context context, List<String> strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final View view = LayoutInflater.from(context).inflate(R.layout.view_pager_cat_layout,null);

        ApiServices apiServices = new ApiServices(context);
        apiServices.ItemcategoryReceive(String.valueOf(position), new ApiServices.OnItemCategoryReceived() {
            @Override
            public void onItemReceived(List<ModelCategory> modelCategories) {
                recyclerView = view.findViewById(R.id.recyc_view_pager);
                recyclerView.setLayoutManager(new GridLayoutManager(context,2));
                adapterItemCat = new AdapterItemCat(context,modelCategories);
                recyclerView.setAdapter(adapterItemCat);
            }
        });

        container.addView(view);

        return view;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView((View)object);
    }
}
