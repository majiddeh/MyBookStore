package com.example.mybookstore.Activities;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Put;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class VerfyCodeActivity extends AppCompatActivity {
    ImageView imgBack;
    TextView txtTitle, tvPhoneInfo,tvTimer,edPhoneEdit;
//
    EditText edCode;
    Timer timer;
    long timercount;
    int check;
    String code="";
    ApiServices apiServices;
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

        edCode.requestFocus();
        edCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==5){
                    apiServices.VerfyCode(getIntent().getStringExtra(Put.username),edCode.getText().toString().trim());
                }
            }
        });


//        for (int i = 0; i <arrayid.length ; i++) {
//            final int j = i+1;
//            final int k = i-1;
//            final EditText editText1=findViewById(arrayid[i]);
//            if (arrayid.length-1!=i){
//                final EditText editText2=findViewById(arrayid[j]);
//                editText1.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    }
//
//                    //TODO agar adad ro vared bokonim dorost amal mikone vali agar adad ro pak konim na
//                    @Override
//                    public void afterTextChanged(Editable s) {
////                        editText2.requestFocus();
//                    }
//                });
//            }
//
//            editText1.setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
//                    if(keyCode == KeyEvent.KEYCODE_DEL) {
//                        final EditText editText3=findViewById(arrayid[k]);
//                        if (k!=0){
//                            editText3.requestFocus();
//                        }
//
//                    }
//                    return false;
//                }
//            });
//        }
//
//        EditText editText3=findViewById(arrayid[4]);
//        editText3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                code="";
//                for (int i = 0; i <arrayid.length ; i++) {
//                    EditText editText1=findViewById(arrayid[i]);
//                    if (editText1.getText().toString().trim().isEmpty()){
//                        Toast.makeText(VerfyCodeActivity.this, "لطفا کد را کامل وارد کنید", Toast.LENGTH_SHORT).show();
//                    }else {
//                        code+=editText1.getText().toString().trim();
//                        apiServices.VerfyCode(getIntent().getStringExtra(Put.username),code);
//                    }
//                }
//
//            }
//        });



    }

    private void onClicks() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvPhoneInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerfyCodeActivity.this,RegisterActivity.class));
                finish();
            }
        });
    }
    private void findViews() {
        edPhoneEdit=findViewById(R.id.tv_edit_phone);
        apiServices=new ApiServices(VerfyCodeActivity.this);
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        tvPhoneInfo = findViewById(R.id.tv_phone_info);
        tvTimer = findViewById(R.id.tv_timer);
        txtTitle.setText("تایید شماره همراه");
        imgBack = findViewById(R.id.img_back_second_toolbar);
        tvPhoneInfo.setText("کد تایید به "+getIntent().getStringExtra(Put.username) + " ارسال شد");
        edCode=findViewById(R.id.ed_code);
    }

}
