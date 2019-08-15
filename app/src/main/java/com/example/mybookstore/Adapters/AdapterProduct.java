package com.example.mybookstore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mybookstore.Models.ModelProduct;
import com.example.mybookstore.R;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.viewHolder> {

    List<ModelProduct> modelProducts;
    Context context;

    public AdapterProduct(List<ModelProduct> modelProducts, Context context) {
        this.modelProducts = modelProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_view_product,viewGroup,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return modelProducts.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
