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
import android.widget.Toast;

import com.example.mybookstore.Activities.BannerActivity;
import com.example.mybookstore.Models.ModelBanner;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterBanner extends RecyclerView.Adapter<AdapterBanner.viewHolder> {

    Context context;
    List<ModelBanner> modelBanners;

    public AdapterBanner(Context context, List<ModelBanner> modelBanners) {
        this.context = context;
        this.modelBanners = modelBanners;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_banner,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder viewHolder, final int i) {

        Picasso.with(context).load(modelBanners.get(i).getImage().replace(Links.LOCALHOST,Links.LINK_ADAPTER))
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.imgBanner);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), BannerActivity.class);
                intent.putExtra(Put.id,modelBanners.get(i).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelBanners.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        ImageView imgBanner;
        CardView cardView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            imgBanner = itemView.findViewById(R.id.img_banner);
            cardView = itemView.findViewById(R.id.card_banner);
        }
    }
}
