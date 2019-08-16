package com.example.mybookstore.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybookstore.Models.ModelOff;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.Links;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import jp.shts.android.library.TriangleLabelView;

public class AdapterOff extends RecyclerView.Adapter<AdapterOff.viewHolder> {

    private List<ModelOff> modelOffs;
    private Context context;

    public AdapterOff(List<ModelOff> modelOffs, Context context) {
        this.modelOffs = modelOffs;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_show_off,viewGroup,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOff.viewHolder viewHolder, int i) {

        DecimalFormat decimalFormat = new DecimalFormat("###,###");

        viewHolder.txtPrice.setText(decimalFormat.format(Integer.valueOf(modelOffs.get(i).getPrice()))+" "+"تومان");
        viewHolder.txtVisit.setText(modelOffs.get(i).getVisit());
        viewHolder.txtTitle.setText(modelOffs.get(i).getTitle());
        viewHolder.triangleLabelView.setSecondaryText(modelOffs.get(i).getLable());
        Picasso.with(context)
                .load(modelOffs.get(i).getImage().replace(Links.LOCALHOST,Links.Link))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(viewHolder.imgOff);


    }

    @Override
    public int getItemCount() {
        return modelOffs.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TriangleLabelView triangleLabelView;
        ImageView imgOff;
        TextView txtTitle,txtVisit,txtPrice;
        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(),"Vazir-Medium-FD-WOL.ttf");

        viewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.cardview_off);
            triangleLabelView = itemView.findViewById(R.id.triangle_off);
            imgOff = itemView.findViewById(R.id.img_off);
            txtTitle = itemView.findViewById(R.id.product_off_name);
            txtTitle.setTypeface(typeface);
            txtPrice = itemView.findViewById(R.id.product_off_price);
            txtPrice.setTypeface(typeface);
            txtVisit = itemView.findViewById(R.id.product_off_visit);
            txtVisit.setTypeface(typeface);
        }
    }

}
