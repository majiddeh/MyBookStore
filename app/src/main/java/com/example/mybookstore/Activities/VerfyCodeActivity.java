package com.example.mybookstore.Activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Trace;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybookstore.R;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class VerfyCodeActivity extends AppCompatActivity {
    ImageView imgBack;
    TextView txtTitle,tvEditPhone,tvTimer;
    int arrayid[]= {R.id.Et_1,R.id.Et_2,R.id.Et_3,R.id.Et_4,R.id.Et_5};
    Timer timer;
    long timercount;
    int check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfy_code);

        findViews();
        onClicks();
        initCodeReceptor();
        gettime();

    }

    void gettime()
    {
        timercount=120000;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timercount-=1000;
                        tvTimer.setText(gettimergn(timercount)+" ثانیه تا ارسال مجدد کد ");
                        if(timercount==0)
                        {
                            timer.cancel();
                            tvTimer.setText("00 : 00 ");
                            tvTimer.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                            check=1;
                        }
                    }
                });
            }
        },0,1000);
    }

    public  String  gettimergn(long timercount)
    {
        long secound=(timercount/1000);
        long mint=secound/60;
        secound %=60;
        return String.format(Locale.ENGLISH,"%02d",secound)+" : "+String.format(Locale.ENGLISH,"%02d", mint);
    }

    private void initCodeReceptor() {
        for (int i = 0; i <arrayid.length ; i++) {
            int j = i+1;
            EditText editText1=findViewById(arrayid[i]);
            if (arrayid.length-1!=i){
                final EditText editText2=findViewById(arrayid[j]);
                editText1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    //TODO agar adad ro vared bokonim dorost amal mikone vali agar adad ro pak konim na
                    @Override
                    public void afterTextChanged(Editable s) {
                        editText2.requestFocus();
                    }
                });
            }
        }


    }

    private void onClicks() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerfyCodeActivity.this,RegisterActivity.class));
                finish();
            }
        });
    }
    private void findViews() {
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        tvEditPhone = findViewById(R.id.tv_edit_phone);
        tvTimer = findViewById(R.id.tv_timer);
        txtTitle.setText("تایید شماره همراه");
        imgBack = findViewById(R.id.img_back_second_toolbar);
    }

}
