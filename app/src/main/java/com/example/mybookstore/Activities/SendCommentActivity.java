package com.example.mybookstore.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.Put;
import com.example.mybookstore.Utils.UserSharedPrefrences;
import com.willy.ratingbar.ScaleRatingBar;

public class SendCommentActivity extends AppCompatActivity {

    ScaleRatingBar ratingBar;
    EditText edInsert,edPositive,edNegative;
    Button btnSubmit;
    TextView txtTollbar;
    ImageView imgBack;
    String phone,image;
    String id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_comment);

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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.equals("ورود/عضویت") && image.equals("")){
                    Toast.makeText(SendCommentActivity.this, "لطفا وارد حساب کاربری خود شوید", Toast.LENGTH_SHORT).show();
                }else {
                    if (!edInsert.getText().toString().equals("") && !edNegative.getText().toString().trim().equals("")
                                && !edPositive.getText().toString().trim().equals("")){
                        if (ratingBar.getRating() != 0){
                            ApiServices apiServices = new ApiServices(SendCommentActivity.this);
                            apiServices.SendComment(id, phone, image, edInsert.getText().toString().trim(),
                                    edPositive.getText().toString().trim(), edNegative.getText().toString().trim(),
                                    ratingBar.getRating(), new ApiServices.OnCommentSend() {
                                        @Override
                                        public void onSend(int responsStatus) {
                                            if (responsStatus == 218){
                                                Toast.makeText(SendCommentActivity.this, "نظر با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else
                            Toast.makeText(SendCommentActivity.this, "لطفا امتیاز دهید", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(SendCommentActivity.this, "لطفا تمامی مقادیر را پر کنید", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void findViews() {

        ratingBar = findViewById(R.id.rating_insert_comment);
        edInsert = findViewById(R.id.ed_comment_input);
        edNegative = findViewById(R.id.ed_negative);
        edPositive = findViewById(R.id.ed_positive);
        btnSubmit = findViewById(R.id.btn_submit);

        txtTollbar = findViewById(R.id.txt_title_toolbar_second);
        txtTollbar.setText(R.string.insert_comment);

        imgBack = findViewById(R.id.img_back_second_toolbar);

        UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(SendCommentActivity.this);
        phone = userSharedPrefrences.getUserName();
        image = userSharedPrefrences.getUserImaje();

        id = getIntent().getStringExtra(Put.id);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
