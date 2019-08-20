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
import com.example.mybookstore.Models.ModelItemProduct;
import com.example.mybookstore.Models.ModelSearch;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.viewHolder> {
    Context context;
    List<ModelSearch> modelSearches;

    public AdapterSearch(Context context, List<ModelSearch> modelSearches) {
        this.context = context;
        this.modelSearches = modelSearches;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_search,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder viewHolder, int i) {

        final ModelSearch modelSearch =modelSearches.get(i);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");



        if (modelSearch.getLable() != null && modelSearch.getLable().equals("0")){
            viewHolder.txtOffPrice.setVisibility(View.GONE);
        }else {
            viewHolder.txtPrice.setTextColor(Color.RED);
            viewHolder.txtPrice.setPaintFlags(viewHolder.txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.txtOffPrice.setVisibility(View.VISIBLE);
            viewHolder.txtOffPrice.setText(decimalFormat.format(Integer.valueOf(modelSearch.getOffPrice()))+" "+"تومان");
        }

        viewHolder.txtVisit.setText(modelSearch.getVisit());
        viewHolder.txtTitle.setText(modelSearch.getTitle());
//        viewHolder.txtPublisher.setText(modelSearch.getPublisher());
//        viewHolder.txtAuthor.setText(modelSearch.getAuthor());
        viewHolder.txtPrice.setText(decimalFormat.format(Integer.valueOf(modelSearch.getPrice()))+" "+"تومان");

        viewHolder.txtDesc.setText(modelSearch.getDesc());

        Picasso.with(context).load(modelSearch.getImage().replace(Links.LOCALHOST,Links.Link))
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.imageViewSearch);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), ShowActivity.class);
                if (modelSearch.getOffPrice().equals(modelSearch.getPrice())){
                    intent.putExtra(Put.offPrice,"0");
                }else {
                    intent.putExtra(Put.offPrice,modelSearch.getOffPrice());
                }
                intent.putExtra(Put.id,modelSearch.getId()+"");

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelSearches.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(),Links.LINK_FONT_VAZIR);
        CardView cardView;
        ImageView imageViewSearch;
        TextView txtTitle,txtPrice,txtDesc,txtVisit,txtOffPrice;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.card_search);
            imageViewSearch=itemView.findViewById(R.id.img_item_search);
            txtTitle=itemView.findViewById(R.id.txttitle_search);
            txtPrice=itemView.findViewById(R.id.txtprice_search);
            txtDesc=itemView.findViewById(R.id.txtdescription_search);
            txtVisit=itemView.findViewById(R.id.txt_visit_item_search);
            txtOffPrice=itemView.findViewById(R.id.txtprice_off_item_search);
            txtOffPrice.setTypeface(typeface);
            txtDesc.setTypeface(typeface);
            txtVisit.setTypeface(typeface);
            txtPrice.setTypeface(typeface);
            txtTitle.setTypeface(typeface);

        }
    }
}
