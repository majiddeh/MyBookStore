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
import com.example.mybookstore.Utils.ApiServices;

public class RegisterActivity extends AppCompatActivity {
    EditText edUsername,edPassword;
    TextView txtTitle;
    CardView cardRegister;
    CheckBox checkBox;
    ImageView imgBack;
    ApiServices apiServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

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

        cardRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiServices.
            }
        });
    }

    private void findViews() {
        apiServices = new ApiServices(RegisterActivity.this);
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText("ثبت نام");
        cardRegister = findViewById(R.id.card_Register);
        edPassword = findViewById(R.id.ed_password);
        edUsername = findViewById(R.id.ed_phone);
        imgBack = findViewById(R.id.img_back_second_toolbar);
        checkBox = findViewById(R.id.checkbox_reg);
    }
}
