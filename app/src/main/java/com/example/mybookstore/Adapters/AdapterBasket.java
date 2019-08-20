package com.example.mybookstore.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mybookstore.Models.ModelBasket;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.MySingleton;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterBasket extends RecyclerView.Adapter<AdapterBasket.ViewHolder> {

    Context context;
    List<ModelBasket> list;

    private OnloadPrice onloadPrice;

    public AdapterBasket(Context context, List<ModelBasket> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnloadPrice(OnloadPrice onloadPrice) {
        this.onloadPrice = onloadPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(context).inflate(R.layout.item_show_basket,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        DecimalFormat decimalFormat = new DecimalFormat("###,###");

        viewHolder.txtTotalPrice.setText(decimalFormat.format(Integer.valueOf(list.get(i).getAllPrice()))+" "+"تومان");
        Picasso.with(context).load(list.get(i).getImage().replace(Links.LOCALHOST, Links.Link))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(viewHolder.imageBasket);
        viewHolder.txtNumber.setText(list.get(i).getNumber()+" "+"عدد");
        viewHolder.txtTitile.setText(list.get(i).getTitle());
        viewHolder.txtPrice.setText(decimalFormat.format(Integer.valueOf(list.get(i).getPrice()))+" "+"تومان");
        viewHolder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onloadPrice !=null ){

                    onloadPrice.onloadPrice();
                    ApiServices apiServices = new ApiServices(context);
                    apiServices.DeleteFromCart(list.get(i).getId());
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            list.remove(i);
                            notifyItemRemoved(i);
                            notifyItemRangeRemoved(i,list.size());
                        }
                    },2);


                }


            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnloadPrice {
        void onloadPrice();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageBasket;
        TextView txtTitile,txtNumber,txtPrice,txtTotalPrice,txtDelete;
        Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(),"Vazir-Medium-FD-WOL.ttf");

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDelete=itemView.findViewById(R.id.txtdeletefrombasket);
            txtDelete.setTypeface(typeface);
            txtNumber=itemView.findViewById(R.id.txtnumberbasket);
            txtNumber.setTypeface(typeface);
            txtPrice=itemView.findViewById(R.id.txtpricebasket);
            txtPrice.setTypeface(typeface);
            txtTitile=itemView.findViewById(R.id.txttitlebasket);
            txtTitile.setTypeface(typeface);
            txtTotalPrice=itemView.findViewById(R.id.txtpricetotalbasket);
            txtTotalPrice.setTypeface(typeface);
            imageBasket=itemView.findViewById(R.id.imgbasket);
        }
    }




}
