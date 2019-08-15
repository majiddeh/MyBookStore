package com.example.mybookstore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybookstore.Models.ModelCategory;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.Links;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.viewHolder> {
    Context context;
    List<ModelCategory> modelCategories;

    public AdapterCategory(Context context, List<ModelCategory> modelCategories) {
        this.context = context;
        this.modelCategories = modelCategories;
    }

    @NonNull
    @Override
    public AdapterCategory.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view_category,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategory.viewHolder viewHolder, int i) {
        final ModelCategory modelCategory = modelCategories.get(i);

        viewHolder.txtCat.setText(modelCategory.getTitle_category());
        Picasso.with(context).load(modelCategory.getImage().replace("localhost", Links.Link))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(viewHolder.imgCat);

        viewHolder.cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, modelCategory.getId()+"", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelCategories.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder{
        CardView cardCategory;
        ImageView imgCat;
        TextView txtCat;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            cardCategory=itemView.findViewById(R.id.card_category);
            imgCat=itemView.findViewById(R.id.img_category);
            txtCat=itemView.findViewById(R.id.txt_title_category);
        }
    }

}