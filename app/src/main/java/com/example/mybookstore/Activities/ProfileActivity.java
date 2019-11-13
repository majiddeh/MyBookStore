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
import com.pnikosis.materialishprogress.ProgressWheel;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvEdit,tvFav,tvExit,tvChangePass;
    private ImageView imgBack;
    UserSharedPrefrences userSharedPrefrences;
    private TextView txtTitle;
    private String username;

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

        tvExit.setOnClickListener(new View.OnClickListener() {
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

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,EditInformation.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        tvFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, FavoriteActivity.class);
                intent.putExtra(Put.username, username);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        tvChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ChangePassActivity.class);
                intent.putExtra("changepass", "change");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    private void findViews() {

        userSharedPrefrences = new UserSharedPrefrences(ProfileActivity.this);

        username = userSharedPrefrences.getUserName();

        tvEdit=findViewById(R.id.tv_personalInformation);
        tvExit=findViewById(R.id.tv_exit);
        tvChangePass = findViewById(R.id.tv_change_pass);
        tvFav=findViewById(R.id.tv_fav);
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
