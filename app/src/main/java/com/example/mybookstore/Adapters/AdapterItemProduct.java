package com.example.mybookstore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybookstore.Activities.ShowActivity;
import com.example.mybookstore.Models.ModelItemProduct;
import com.example.mybookstore.Models.ModelOff_Only_MostVisit;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterItemProduct extends RecyclerView.Adapter<AdapterItemProduct.viewHolder> {
    Context context;
    List<ModelItemProduct> modelItemProducts;

    public AdapterItemProduct(Context context, List<ModelItemProduct> modelItemProducts) {
        this.context = context;
        this.modelItemProducts = modelItemProducts;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_item_product,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder viewHolder, int i) {
        final ModelItemProduct modelItemProduct =modelItemProducts.get(i);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");



        if (modelItemProduct.getLable().equals("0")){
            viewHolder.txtOffPrice.setVisibility(View.GONE);
        }else {
            viewHolder.txtPrice.setTextColor(Color.RED);
            viewHolder.txtPrice.setPaintFlags(viewHolder.txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.txtOffPrice.setVisibility(View.VISIBLE);
            viewHolder.txtOffPrice.setText(decimalFormat.format(Integer.valueOf(modelItemProduct.getOffPrice()))+" "+"تومان");
        }

        viewHolder.txtVisit.setText(modelItemProduct.getVisit());
        viewHolder.txtTitle.setText(modelItemProduct.getTitle());
        viewHolder.txtPublisher.setText(modelItemProduct.getPublisher());
        viewHolder.txtAuthor.setText(modelItemProduct.getAuthor());
        viewHolder.txtPrice.setText(decimalFormat.format(Integer.valueOf(modelItemProduct.getPrice()))+" "+"تومان");

        viewHolder.txtDesc.setText(modelItemProduct.getDesc());

        Picasso.with(context).load(modelItemProduct.getImage().replace(Links.LOCALHOST,Links.LINK_ADAPTER))
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.imgProduct);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), ShowActivity.class);
                if (modelItemProduct.getOffPrice().equals(modelItemProduct.getPrice())){
                    intent.putExtra(Put.offPrice,"0");
                }else {
                    intent.putExtra(Put.offPrice,modelItemProduct.getOffPrice());
                }
                intent.putExtra(Put.id,modelItemProduct.getId()+"");

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelItemProducts.size();
    }

    class viewHolder extends  RecyclerView.ViewHolder{

        TextView txtVisit,txtTitle,txtPrice,txtOffPrice,txtDesc,txtAuthor,txtPublisher;
        CardView cardView;
        ImageView imgProduct;
        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(),Links.LINK_FONT_VAZIR);

        viewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_item_product);
            txtTitle=itemView.findViewById(R.id.txt_title_item_product);
            txtTitle.setTypeface(typeface);
            txtOffPrice=itemView.findViewById(R.id.txtprice_off_item_product);
            txtOffPrice.setTypeface(typeface);
            txtPrice=itemView.findViewById(R.id.txtprice_item_product);
            txtPrice.setTypeface(typeface);
            txtDesc=itemView.findViewById(R.id.txt_description_item_product);
            txtDesc.setTypeface(typeface);
            txtVisit=itemView.findViewById(R.id.txt_visit_item_product);
            txtVisit.setTypeface(typeface);
            imgProduct=itemView.findViewById(R.id.img_item_product);
            txtAuthor=itemView.findViewById(R.id.txt_authur_item_product);
            txtPublisher=itemView.findViewById(R.id.txt_publisher_item_product);
            txtAuthor.setTypeface(typeface);
            txtPublisher.setTypeface(typeface);
        }
    }
}
