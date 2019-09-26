package com.example.mybookstore.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.mybookstore.Models.ModelOff_Only_MostVisit;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import jp.shts.android.library.TriangleLabelView;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.viewHolder> {

    private Context context;
    private List<ModelOff_Only_MostVisit> modelProduct;

    public AdapterProduct(Context context, List<ModelOff_Only_MostVisit> modelProduct) {
        this.context = context;
        this.modelProduct = modelProduct;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_product,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder viewHolder, final int i) {

        DecimalFormat decimalFormat = new DecimalFormat("###,###");

        if (Integer.valueOf(modelProduct.get(i).getLable()) > 0){
            viewHolder.txtPrice.setText(decimalFormat.format(Float.valueOf(modelProduct.get(i).getOffPrice()))+" "+"تومان");
            viewHolder.txtVisit.setText(modelProduct.get(i).getVisit());
            viewHolder.txtTitle.setText(modelProduct.get(i).getTitle());
            viewHolder.triangleLabelView.setSecondaryText(modelProduct.get(i).getLable());
            Picasso.with(context)
                    .load(modelProduct.get(i).getImage().replace(Links.LOCALHOST,Links.LINK_ADAPTER))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(viewHolder.imgOnly);
        }else {
            viewHolder.txtPrice.setText(decimalFormat.format(Integer.valueOf(modelProduct.get(i).getPrice()))+" "+"تومان");
            viewHolder.txtVisit.setText(modelProduct.get(i).getVisit());
            viewHolder.txtTitle.setText(modelProduct.get(i).getTitle());
            viewHolder.triangleLabelView.setVisibility(View.GONE);
            Picasso.with(context)
                    .load(modelProduct.get(i).getImage().replace(Links.LOCALHOST,Links.LINK_ADAPTER))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(viewHolder.imgOnly);
        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), ShowActivity.class);
                intent.putExtra(Put.id,modelProduct.get(i).getId()+"");
                intent.putExtra(Put.offPrice,modelProduct.get(i).getOffPrice());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelProduct.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TriangleLabelView triangleLabelView;
        ImageView imgOnly;
        TextView txtTitle,txtVisit,txtPrice;

        viewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.cardview_product);
            triangleLabelView = itemView.findViewById(R.id.triangle_product);
            imgOnly = itemView.findViewById(R.id.img_product);
            txtTitle = itemView.findViewById(R.id.txt_title_product);
            txtPrice = itemView.findViewById(R.id.txt_price_product);
            txtVisit = itemView.findViewById(R.id.txt_visit_product);
        }
    }
}
