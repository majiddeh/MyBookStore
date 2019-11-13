package com.example.mybookstore.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybookstore.R;

public class PayConfirmActivity extends AppCompatActivity {

    TextView txtTitle;
    ImageView imgBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_confirm);

        findViews();
        onClicks();
    }

    private void onClicks() {
        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void findViews() {
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText("نهایی کردن خرید");
        imgBackButton = findViewById(R.id.img_back_second_toolbar);
    }
}
