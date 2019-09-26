package com.example.mybookstore.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Put;

import org.w3c.dom.Text;

public class ForgetPassActivity extends AppCompatActivity {

    private EditText edForget;
    private CardView cardForget;
    private TextView txtTitle;
    private ImageView imgBack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        findViews();
        onClicks();
    }

    private void onClicks() {
        cardForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiServices apiServices = new ApiServices(ForgetPassActivity.this);
                apiServices.ForgetPass(edForget.getText().toString().trim(), new ApiServices.OnForgetPass() {
                    @Override
                    public void OnForget(int responseStatus) {
                        if (responseStatus == Put.STATUS_SUCCESS){
                            Toast.makeText(ForgetPassActivity.this, "رمز عبور شما به آدرس پست الکترونیکی شما ارسال شد", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(ForgetPassActivity.this, "پست الکترونیک وارد شده معتبر نمی باشد", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViews() {
        edForget = findViewById(R.id.ed_email_forget);
        cardForget = findViewById(R.id.card_forget);
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText("فراموشی رمز عبور");
        imgBack = findViewById(R.id.img_back_second_toolbar);
    }
}
