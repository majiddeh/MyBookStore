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
import com.example.mybookstore.Models.ModelLikes;
import com.example.mybookstore.Models.ModelOff_Only_MostVisit;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import jp.shts.android.library.TriangleLabelView;

public class AdapterLikes extends RecyclerView.Adapter<AdapterLikes.viewHolder> {

    private Context context;
    private List<ModelLikes> modelLikes;

    public AdapterLikes(Context context, List<ModelLikes> modelLikes) {
        this.context = context;
        this.modelLikes = modelLikes;
    }

    @NonNull
    @Override
    public AdapterLikes.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_product,viewGroup,false);
        return new AdapterLikes.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterLikes.viewHolder viewHolder, final int i) {

        DecimalFormat decimalFormat = new DecimalFormat("###,###");

        if (Integer.valueOf(modelLikes.get(i).getLable()) > 0){
//            viewHolder.txtPrice.setText(decimalFormat.format(Integer.valueOf(modelLikes.get(i).getOffPrice()))+" "+"تومان");
            viewHolder.txtVisit.setText(modelLikes.get(i).getVisit());
            viewHolder.txtTitle.setText(modelLikes.get(i).getTitle());
            viewHolder.triangleLabelView.setSecondaryText(modelLikes.get(i).getLable());
            Picasso.with(context)
                    .load(modelLikes.get(i).getImage().replace(Links.LOCALHOST,Links.LINK_ADAPTER))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(viewHolder.imgOnly);
        }else {
            viewHolder.txtPrice.setText(decimalFormat.format(Integer.valueOf(modelLikes.get(i).getPrice()))+" "+"تومان");
            viewHolder.txtVisit.setText(modelLikes.get(i).getVisit());
            viewHolder.txtTitle.setText(modelLikes.get(i).getTitle());
            viewHolder.triangleLabelView.setVisibility(View.GONE);
            Picasso.with(context)
                    .load(modelLikes.get(i).getImage().replace(Links.LOCALHOST,Links.LINK_ADAPTER))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(viewHolder.imgOnly);
        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), ShowActivity.class);
                intent.putExtra(Put.id, modelLikes.get(i).getId()+"");
//                intent.putExtra(Put.offPrice, modelLikes.get(i).getOffPrice());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelLikes.size();
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