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
import android.widget.Toast;

import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Put;
import com.pnikosis.materialishprogress.ProgressWheel;

public class LoginActivity extends AppCompatActivity {

    EditText edUsername,edPassword;
    TextView txtTitle,tvRegister,tvForgetPass;
    CardView cardLogin;
    ImageView imgBack;
    CheckBox checkBox;
    ApiServices apiServices;
    ProgressWheel progressWheel;
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
                if (edUsername.getText().toString().trim().isEmpty()){
                    edUsername.setError("لطفا نام کاربری را وارد کنید...");
                }else {
                    apiServices.ForgetPass(edUsername.getText().toString().trim(),progressWheel);
                }

            }
        });

        cardLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edPassword.getText().toString().trim().equals("") && !edUsername.getText().toString().trim().equals("")){
                    String username = convertNumberToEnglish(edUsername.getText().toString().trim());
                    String password = convertNumberToEnglish(edPassword.getText().toString().trim());
                    apiServices.loginUserHA(username,password,progressWheel);
                }else {
                    edUsername.setError("لطفا نام کاربری و پسورد را وارد کنید");
                    edPassword.setError("لطفا نام کاربری و پسورد را وارد کنید");
                    edUsername.requestFocus();
                }

            }
        });
    }

    private String convertNumberToEnglish(String num) {
        String d = num;
        d = d.replace("۰", "0");
        d = d.replace("۱", "1");
        d = d.replace("۲", "2");
        d = d.replace("٣", "3");
        d = d.replace("٤", "4");
        d = d.replace("۵", "5");
        d = d.replace("٦", "6");
        d = d.replace("٧", "7");
        d = d.replace("۸", "8");
        d = d.replace("۹", "9");

        d = d.replace("۰", "0");
        d = d.replace("۱", "1");
        d = d.replace("۲", "2");
        d = d.replace("۳", "3");
        d = d.replace("۴", "4");
        d = d.replace("۵", "5");
        d = d.replace("۶", "6");
        d = d.replace("۷", "7");
        d = d.replace("۸", "8");
        d = d.replace("۹", "9");

        return d;
    }

    private void findViews() {
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText(" ورود");
        cardLogin = findViewById(R.id.card_login);
        imgBack = findViewById(R.id.img_back_second_toolbar);
        edPassword = findViewById(R.id.ed_password);
        edUsername = findViewById(R.id.ed_phone);
        edUsername.requestFocus();
        checkBox = findViewById(R.id.checkbox);
        tvForgetPass = findViewById(R.id.tv_forget_password);
        tvRegister = findViewById(R.id.tv_register);

        progressWheel=findViewById(R.id.progress_wheel);

        apiServices=new ApiServices(LoginActivity.this);
    }

}
