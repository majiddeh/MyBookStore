package com.example.mybookstore.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    UserSharedPrefrences userSharedPrefrences;
    ApiServices apiServices ;
    CardView cardOrder;
    public FragmentOrderConfirmaton() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_confirmation, container, false);

        cardOrder = view.findViewById(R.id.card_fragment_order);
        cardOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
        return view;
    }

}