package com.example.mybookstore.Fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.mybookstore.Activities.BasketActivity;
import com.example.mybookstore.Activities.EditInformation;
import com.example.mybookstore.Adapters.AdapterAddressFragment;
import com.example.mybookstore.Adapters.AdapterBasket;
import com.example.mybookstore.Models.ModelAddress;
import com.example.mybookstore.Models.ModelBasket;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;
import com.example.mybookstore.Utils.UserSharedPrefrences;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOrder extends Fragment {

    RecyclerView recyclerViewAddress,recyclerViewProducts;
    ProgressWheel progressWheel;
    ApiServices apiServices ;
    CheckBox checkBoxFactor;
    CardView cardOrder,cardPlaceHolder;
    String nameUser,addressUser,cityUser,cityCapUser,mobileUser,familyUser;
    public FragmentOrder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_order, container, false);
        String token = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(Put.token);
        recyclerViewAddress = view.findViewById(R.id.recycler_fragment_order_address);
        recyclerViewProducts = view.findViewById(R.id.recycler_fragment_order_products);
        cardPlaceHolder = view.findViewById(R.id.card_placeholder);
        checkBoxFactor = view.findViewById(R.id.chk_factor);
        checkBoxFactor.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), Links.LINK_FONT_VAZIR));
        progressWheel = view.findViewById(R.id.progress_wheel);
        apiServices = new ApiServices(getActivity());
        apiServices.GetAddress(token, new ApiServices.OnAddressReceived() {
            @Override
            public void onReceived(List<ModelAddress> modelAddresses) {
                AdapterAddressFragment adapterAddress = new AdapterAddressFragment(getActivity(), modelAddresses, new AdapterAddressFragment.GetAddressUser() {
                    @Override
                    public void onAddressUser(String name,String family ,String address, String city, String cityCap, String mobile) {
                        nameUser=name;
                        addressUser=address;
                        cityUser=city;
                        cityCapUser=cityCap;
                        mobileUser=mobile;
                        familyUser=family;
                    }
                });
                if (modelAddresses.isEmpty()){
                    cardPlaceHolder.setVisibility(View.VISIBLE);
                    recyclerViewAddress.setVisibility(View.GONE);
                }else {
                    recyclerViewAddress.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                    recyclerViewAddress.setAdapter(adapterAddress);
                    recyclerViewAddress.hasFixedSize();
                }

            }
        });

        cardPlaceHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditInformation.class);
                startActivity(intent);
            }
        });

        apiServices.CartReceived(progressWheel,token, new ApiServices.OnCartReceived() {
            @Override
            public void onReceived(List<ModelBasket> modelBaskets, int totalPrice) {
                AdapterBasket adapterBasket = new AdapterBasket(getActivity(),modelBaskets);
                recyclerViewProducts.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                recyclerViewProducts.setAdapter(adapterBasket);
            }
        });
        cardOrder = view.findViewById(R.id.card_fragment_order);
        cardOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (addressUser == null || addressUser.equals("")){
                            Toast.makeText(getActivity(), "لطفا آدرس را جهت ارسال مرسوله انتخاب کنید" , Toast.LENGTH_SHORT).show();
                        }else {
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentOrderConfirmaton fragmentOrderConfirmaton = new FragmentOrderConfirmaton();
                            fragmentOrderConfirmaton.AddressInfo(nameUser,familyUser,addressUser,cityUser,cityCapUser,mobileUser);
                            FragmentTransaction transaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                            transaction.replace(R.id.rel_root,fragmentOrderConfirmaton).addToBackStack("");
                            transaction.commit();
                        }

                    }
                },1000);

            }
        });
        return view;
    }

}
