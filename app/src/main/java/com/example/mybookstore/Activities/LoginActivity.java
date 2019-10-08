package com.example.mybookstore.Activities;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    EditText edUsername,edPassword;
    TextView txtTitle,tvRegister,tvForgetPass;
    CardView cardRegister;
    ImageView imgBack;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
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

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void findViews() {
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText(" ورود");
        cardRegister = findViewById(R.id.card_Register);
        imgBack = findViewById(R.id.img_back_second_toolbar);
        edPassword = findViewById(R.id.ed_password);
        edUsername = findViewById(R.id.ed_phone);
        checkBox = findViewById(R.id.checkbox);
        tvForgetPass = findViewById(R.id.tv_forget_password);
        tvRegister = findViewById(R.id.tv_register);
    }

}
