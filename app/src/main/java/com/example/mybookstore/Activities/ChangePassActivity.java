package com.example.mybookstore.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Put;
import com.example.mybookstore.Utils.UserSharedPrefrences;
import com.pnikosis.materialishprogress.ProgressWheel;

public class ChangePassActivity extends AppCompatActivity {

    EditText edRepass, edCurrentPass,edNewPass;
    RelativeLayout liner2;
    TextView txtTitle;
    CardView cardChangepass;
    ProgressWheel progressWheel;
    ImageView imgBack;
    ApiServices apiServices;
    String username,token;
    UserSharedPrefrences userSharedPrefrences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        findViews();
        initPage();
        onClicks();


    }

    private void initPage() {
        if (getIntent().getStringExtra("changepass").equals("forget")){
            liner2.setVisibility(View.GONE);
            edNewPass.requestFocus();
        }else if (getIntent().getStringExtra("changepass").equals("change")){
            edCurrentPass.requestFocus();
        }
    }

    private void onClicks() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cardChangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getStringExtra("changepass").equals("forget")){
                    String pass = convertNumberToEnglish(edNewPass.getText().toString().trim());
                    String repass = convertNumberToEnglish(edRepass.getText().toString().trim());
                    apiServices.SetPass(getIntent().getStringExtra(Put.username),pass,repass,progressWheel);
                }else if (getIntent().getStringExtra("changepass").equals("change")){
                    String pass = convertNumberToEnglish(edNewPass.getText().toString().trim());
                    String repass = convertNumberToEnglish(edRepass.getText().toString().trim());
                    String currentpass = convertNumberToEnglish(edCurrentPass.getText().toString().trim());
                    apiServices.ChangePass(username,token,currentpass,pass,repass,progressWheel);
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
        userSharedPrefrences = new UserSharedPrefrences(ChangePassActivity.this);
        apiServices = new ApiServices(ChangePassActivity.this);
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText("ایجاد کلمه عبور جدید");
        cardChangepass = findViewById(R.id.card_Register);
        progressWheel = findViewById(R.id.progress_wheel);
        edCurrentPass = findViewById(R.id.ed_current_pass);
        edNewPass = findViewById(R.id.ed_newpass);
        edRepass = findViewById(R.id.ed_repass);
        imgBack = findViewById(R.id.img_back_second_toolbar);
        liner2 =findViewById(R.id.liner2);
        username = userSharedPrefrences.getUserName();
        token = userSharedPrefrences.getUserToken();
    }
}
