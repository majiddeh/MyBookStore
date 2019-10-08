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
import android.widget.Toast;

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
                if (!edPassword.getText().toString().trim().equals("")&&!edUsername.getText().toString().trim().equals("")){
                    if (!(edPassword.getText().toString().trim().length() <4)){
                        String username=convertNumberToEnglish(edUsername.getText().toString().trim());
//                        Toast.makeText(RegisterActivity.this, username, Toast.LENGTH_SHORT).show();
                        apiServices.registeWhitSMS(username,edPassword.getText().toString().trim());
                    }else
                        Toast.makeText(RegisterActivity.this, "پسورد حداقل باید چهار کاراکتر باشد", Toast.LENGTH_SHORT).show();

                }else
                    Toast.makeText(RegisterActivity.this, "لطفا نام کاربری و پسورد را وارد کنید", Toast.LENGTH_SHORT).show();

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
