package com.example.mybookstore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybookstore.Activities.ShowActivity;
import com.example.mybookstore.Models.ModelFav;
import com.example.mybookstore.Models.ModelItemProduct;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterFav extends RecyclerView.Adapter<AdapterFav.viewHolder> {
    Context context;
    List<ModelFav> modelFavs;

    public AdapterFav(Context context, List<ModelFav> modelFavs) {
        this.context = context;
        this.modelFavs = modelFavs;
    }

    @NonNull
    @Override
    public AdapterFav.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_item_product,viewGroup,false);
        return new AdapterFav.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterFav.viewHolder viewHolder, int i) {
        final ModelFav modelFav = modelFavs.get(i);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");



        if (modelFav.getLable().equals("0")){
            viewHolder.txtOffPrice.setVisibility(View.GONE);
        }else {
            viewHolder.txtPrice.setTextColor(Color.RED);
            viewHolder.txtPrice.setPaintFlags(viewHolder.txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.txtOffPrice.setVisibility(View.VISIBLE);
            viewHolder.txtOffPrice.setText(decimalFormat.format(Integer.valueOf(modelFav.getOffPrice()))+" "+"تومان");
        }

        viewHolder.txtVisit.setText(modelFav.getVisit());
        viewHolder.txtTitle.setText(modelFav.getTitle());
        viewHolder.txtPublisher.setText(modelFav.getPublisher());
        viewHolder.txtAuthor.setText(modelFav.getAuthor());
        viewHolder.txtPrice.setText(decimalFormat.format(Integer.valueOf(modelFav.getPrice()))+" "+"تومان");

        viewHolder.txtDesc.setText(modelFav.getDesc());

        Picasso.with(context).load(modelFav.getImage().replace(Links.LOCALHOST,Links.LINK_ADAPTER))
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.imgProduct);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), ShowActivity.class);
                if (modelFav.getOffPrice().equals(modelFav.getPrice())){
                    intent.putExtra(Put.offPrice,"0");
                }else {
                    intent.putExtra(Put.offPrice,modelFav.getOffPrice());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                intent.putExtra(Put.id,modelFav.getId()+"");
                //TODO In DBSQLITE add a column name cat to store cat for likes products
//                intent.putExtra(Put.cat,modelFav.getcat());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelFavs.size();
    }

    class viewHolder extends  RecyclerView.ViewHolder{

        TextView txtVisit,txtTitle,txtPrice,txtOffPrice,txtDesc,txtAuthor,txtPublisher;
        CardView cardView;
        ImageView imgProduct;

        viewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_item_product);
            txtTitle=itemView.findViewById(R.id.txt_title_item_product);
            txtOffPrice=itemView.findViewById(R.id.txtprice_off_item_product);
            txtPrice=itemView.findViewById(R.id.txtprice_item_product);
            txtDesc=itemView.findViewById(R.id.txt_description_item_product);
            txtVisit=itemView.findViewById(R.id.txt_visit_item_product);
            imgProduct=itemView.findViewById(R.id.img_item_product);
            txtAuthor=itemView.findViewById(R.id.txt_authur_item_product);
            txtPublisher=itemView.findViewById(R.id.txt_publisher_item_product);
        }
    }
}
