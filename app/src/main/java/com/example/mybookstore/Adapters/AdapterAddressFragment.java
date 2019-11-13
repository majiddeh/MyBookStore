package com.example.mybookstore.Adapters;

import android.app.Activity;
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
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybookstore.Models.ModelAddress;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;

import java.util.List;

public class AdapterAddressFragment extends RecyclerView.Adapter<AdapterAddressFragment.viewHolder> {

    int[] idRadioButton;
    private RadioButton lastCheckedRB = null;
    Context context;
    List<ModelAddress> modelAddresses;

    public AdapterAddressFragment(Context context, List<ModelAddress> modelAddresses) {
        this.context = context;
        this.modelAddresses = modelAddresses;
        idRadioButton=new int[modelAddresses.size()];
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_address,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder viewHolder, final int position) {

        viewHolder.radioButton.setVisibility(View.VISIBLE);

        final int id = View.generateViewId();
        viewHolder.radioButton.setId(id);
        idRadioButton[position]=id;

        final ModelAddress modelAddress=modelAddresses.get(position);
        viewHolder.tvNameFamily.setText(modelAddress.getName() + " " + modelAddress.getFamily());
        viewHolder.tvMobile.setText("شماره تلفن ضروری : "+modelAddress.getMobile());
        viewHolder.tvPhone.setText("شماره تلفن ثابت : "+modelAddress.getPhone());
        viewHolder.tvAddress.setText("آدرس : "+modelAddress.getAddress());
        viewHolder.tvCity.setText(" شهر: ‌"+modelAddress.getCity());
        viewHolder.tvCityCap.setText(" استان : "+modelAddress.getCityCap());
        viewHolder.tvPostalCode.setText(" کدپستی : "+modelAddress.getPostalcode());

//        viewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                for (int i = 0; i <idRadioButton.length ; i++) {
//                    RadioButton radioButtonset = ((Activity)context).findViewById(idRadioButton[i]);
//                    if (view.getId() == idRadioButton[i]){
//                        radioButtonset.setChecked(true);
//                        Toast.makeText(context, "true", Toast.LENGTH_SHORT).show();
//                    }else {
//                        radioButtonset.setChecked(false);
//                        Toast.makeText(context, "false", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });

        viewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton checked_rb = (RadioButton) view;
                if(lastCheckedRB != null){
                    lastCheckedRB.setChecked(false);
                }
                lastCheckedRB = checked_rb;
            }
        });




    }

    @Override
    public int getItemCount() {
        return modelAddresses.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        RadioButton radioButton;
        TextView tvNameFamily,tvCityCap,tvCity,tvPhone,tvMobile,tvAddress,tvPostalCode;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton =itemView.findViewById(R.id.rb_select);
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
