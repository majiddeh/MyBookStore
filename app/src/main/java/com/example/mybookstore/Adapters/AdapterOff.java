package com.example.mybookstore.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.mybookstore.Models.ModelOff_Only_MostVisit;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import jp.shts.android.library.TriangleLabelView;

public class AdapterOff extends RecyclerView.Adapter<AdapterOff.viewHolder> {

    private List<ModelOff_Only_MostVisit> modelOffOnlies;
    private Context context;

    public AdapterOff(List<ModelOff_Only_MostVisit> modelOffOnlies, Context context) {
        this.modelOffOnlies = modelOffOnlies;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_show_off,viewGroup,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterOff.viewHolder viewHolder, final int i) {

        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        viewHolder.txtPrice.setText(decimalFormat.format(Float.valueOf(modelOffOnlies.get(i).getOffPrice()))+" "+"تومان");

        viewHolder.txtVisit.setText(modelOffOnlies.get(i).getVisit());
        viewHolder.txtTitle.setText(modelOffOnlies.get(i).getTitle());
        viewHolder.txtoffPrice.setText(decimalFormat.format(Integer.valueOf(modelOffOnlies.get(i).getPrice()))+" "+"تومان");
        viewHolder.txtoffPrice.setPaintFlags(viewHolder.txtoffPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.triangleLabelView.setSecondaryText(modelOffOnlies.get(i).getLable());
        Picasso.with(context)
                .load(modelOffOnlies.get(i).getImage().replace(Links.LOCALHOST,Links.LINK_ADAPTER))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(viewHolder.imgOff);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), ShowActivity.class);
                intent.putExtra(Put.id,modelOffOnlies.get(i).getId()+"");
                intent.putExtra(Put.offPrice,modelOffOnlies.get(i).getOffPrice());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return modelOffOnlies.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TriangleLabelView triangleLabelView;
        ImageView imgOff;
        TextView txtTitle,txtVisit,txtPrice,txtoffPrice;

        viewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.cardview_product);
            triangleLabelView = itemView.findViewById(R.id.triangle_product);
            imgOff = itemView.findViewById(R.id.img_product);
            txtTitle = itemView.findViewById(R.id.txt_title_product);
            txtPrice = itemView.findViewById(R.id.txt_price_product);
            txtVisit = itemView.findViewById(R.id.txt_visit_product);
            txtoffPrice = itemView.findViewById(R.id.txt_price_off);
        }
    }

}
