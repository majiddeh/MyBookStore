package com.example.mybookstore.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybookstore.Models.ModelAddress;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterAddress extends RecyclerView.Adapter<AdapterAddress.viewHolder> {

    Context context;
    List<ModelAddress> modelAddresses;

    public AdapterAddress(Context context, List<ModelAddress> modelAddresses) {
        this.context = context;
        this.modelAddresses = modelAddresses;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_address,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, final int i) {

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dilaog_default);
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);
                TextView tvTitle,tvText,tvNegativ,tvPositive;
                tvNegativ=dialog.findViewById(R.id.tv_dialog_negative);
                tvPositive=dialog.findViewById(R.id.tv_dialog_positive);
                tvText=dialog.findViewById(R.id.tv_dialog_text);
                tvTitle=dialog.findViewById(R.id.tv_dialog_title);
                tvNegativ.setText("حذف");
                tvPositive.setText("ویرایش");

                tvNegativ.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ApiServices apiServices = new ApiServices(context);
                                apiServices.DelAddress(String.valueOf(modelAddresses.get(i).getIdAddress()), new ApiServices.OnAddressDeleted() {
                                    @Override
                                    public void onDeleted(boolean succeed) {
                                        if (succeed){
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    modelAddresses.remove(i);
                                                    notifyItemRemoved(i);
                                                    notifyItemRangeRemoved(i,modelAddresses.size());
                                                }
                                            },20);
                                            dialog.dismiss();
                                            Toast.makeText(context, "آدرس مورد نظر با موفقیت حذف شد", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(context, "عملیات حذف با خطا مواجه شد لطفا دوباره سعی کنید", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        },500);

                    }
                });

                tvPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Handler handlerr = new Handler();
                        handlerr.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            }
                        },500);

                    }
                });

                tvTitle.setText("حذف یا ویرایش آدرس");
                tvText.setText("آدرس خود را ویرایش یا حذف کنید!");
            }
        });

        final ModelAddress modelAddress=modelAddresses.get(i);
        viewHolder.tvNameFamily.setText(modelAddress.getName() + " " + modelAddress.getFamily());
        viewHolder.tvMobile.setText("شماره تلفن ضروری : "+modelAddress.getMobile());
        viewHolder.tvPhone.setText("شماره تلفن ثابت : "+modelAddress.getPhone());
        viewHolder.tvAddress.setText("آدرس : "+modelAddress.getAddress());
        viewHolder.tvCity.setText(" شهر: ‌"+modelAddress.getCity());
        viewHolder.tvCityCap.setText(" استان : "+modelAddress.getCityCap());
        viewHolder.tvPostalCode.setText(" کدپستی : "+modelAddress.getPostalcode());



    }

    @Override
    public int getItemCount() {
        return modelAddresses.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvNameFamily,tvCityCap,tvCity,tvPhone,tvMobile,tvAddress,tvPostalCode;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameFamily=itemView.findViewById(R.id.Tv_name_user);
            tvCityCap=itemView.findViewById(R.id.Tv_ostan_user);
            tvCity=itemView.findViewById(R.id.Tv_city);
            tvAddress=itemView.findViewById(R.id.Tv_address);
            tvPostalCode=itemView.findViewById(R.id.Tv_postalcode);
            tvPhone=itemView.findViewById(R.id.Tv_phone_home);
            tvMobile=itemView.findViewById(R.id.Tv_mobile);
            cardView = itemView.findViewById(R.id.card_address);
        }
    }
}
