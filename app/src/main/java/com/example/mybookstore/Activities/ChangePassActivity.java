package com.example.mybookstore.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybookstore.R;

public class ChangePassActivity extends AppCompatActivity {

    EditText edrepass,edPassword;
    TextView txtTitle;
    CardView cardRepass;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
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

    }

    private void findViews() {
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText("ایجاد کلمه عبور جدید");
        cardRepass = findViewById(R.id.card_Register);
        edPassword = findViewById(R.id.ed_pass);
        edrepass = findViewById(R.id.ed_repass);
        imgBack = findViewById(R.id.img_back_second_toolbar);
    }
}
