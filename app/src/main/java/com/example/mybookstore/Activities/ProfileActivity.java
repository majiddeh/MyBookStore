package com.example.mybookstore.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybookstore.R;
import com.example.mybookstore.Utils.Put;
import com.example.mybookstore.Utils.UserSharedPrefrences;

public class ProfileActivity extends AppCompatActivity {

    private Button btnEdit,btnFavorit,btnExite;
    private ImageView imgBack;

    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViews();
        onClicks();

    }



    private void onClicks() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnExite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(ProfileActivity.this);
                userSharedPrefrences.exitFromAccount();
                Intent intent =new Intent();
                intent.putExtra(Put.username,"ورود/عضویت");
                intent.putExtra(Put.image,"");
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,EditInformation.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    private void findViews() {

        btnEdit=findViewById(R.id.btn_edit_profile);
        btnExite=findViewById(R.id.btn_exit_profile);
        btnFavorit=findViewById(R.id.btn_favorite_profile);
        imgBack=findViewById(R.id.img_back_second_toolbar);
        txtTitle=findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText("حساب کاربری");


    }





    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }



}
