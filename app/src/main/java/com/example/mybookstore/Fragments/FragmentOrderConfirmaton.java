package com.example.mybookstore.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mybookstore.Adapters.AdapterAddressFragment;
import com.example.mybookstore.Models.ModelAddress;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Put;
import com.example.mybookstore.Utils.UserSharedPrefrences;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOrderConfirmaton extends Fragment {

    RecyclerView recyclerView;
    ApiServices apiServices ;
    TextView tvAddressInfo;
    CardView cardOrder;
    String addressUser,nameUser,cityUser,cityCapUser,mobileUser,familyUser;
    public FragmentOrderConfirmaton() {
        // Required empty public constructor
    }

    public void AddressInfo(String name,String family,String address,String city,String cityCap,String mobile){
        this.nameUser=name;
        this.addressUser=address;
        this.cityUser=city;
        this.cityCapUser=cityCap;
        this.mobileUser=mobile;
        this.familyUser=family;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_confirmation, container, false);

        cardOrder = view.findViewById(R.id.card_fragment_order);
        tvAddressInfo = view.findViewById(R.id.tv_address_info);
        String text = "تمامی مرسوله های انتخابی " +nameUser+" "+familyUser+" به آدرس " + cityCapUser + " ،"
                +cityUser + " ،" +addressUser +" و به شماره تلفن " +mobileUser + " ارسال خواهد شد";

        tvAddressInfo.setText(text);

        cardOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
        return view;
    }

}
