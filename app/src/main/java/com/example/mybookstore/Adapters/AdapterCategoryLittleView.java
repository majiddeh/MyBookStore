package com.example.mybookstore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybookstore.Activities.CatActivity_ViewPager;
import com.example.mybookstore.Activities.ItemCatActivity;
import com.example.mybookstore.Models.ModelCategory;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCategoryLittleView extends RecyclerView.Adapter<AdapterCategoryLittleView.viewHolder> {
    Context context;
    List<ModelCategory> modelCategories;

    public AdapterCategoryLittleView(Context context, List<ModelCategory> modelCategories) {
        this.context = context;
        this.modelCategories = modelCategories;
    }

    @NonNull
    @Override
    public AdapterCategoryLittleView.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_cat_littleview,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterCategoryLittleView.viewHolder viewHolder, final int i) {
        final ModelCategory modelCategory = modelCategories.get(i);

        viewHolder.txtCat.setText(modelCategory.getTitle_category());

        viewHolder.cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO id ke ferestade mishe be safhe baad bayad daghighan mahsoolat hamoon id ro neshoon bede vali nemide ...
                Intent intent = new Intent(viewHolder.itemView.getContext(), ItemCatActivity.class);
                intent.putExtra(Put.id,modelCategories.get(i).getId()+"");
                String title = modelCategory.getTitle_category();
                intent.putExtra(Put.name,title);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelCategories.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder{
        CardView cardCategory;
        TextView txtCat;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            cardCategory=itemView.findViewById(R.id.card_cat_little_view);
            txtCat=itemView.findViewById(R.id.txt_cat_little_view);
        }
    }

}
