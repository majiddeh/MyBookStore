package com.example.mybookstore.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomFontTextView extends TextView {
    public CustomFontTextView(Context context) {
        super(context);
    }

    public CustomFontTextView(Context context,AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()){
            setUpTextView();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomFontTextView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()){
            setUpTextView();
        }
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (!isInEditMode()){
            setUpTextView();
        }
    }


    private void setUpTextView() {
        MyApplication myApplication = (MyApplication) getContext().getApplicationContext();
        setTypeface(myApplication.getVazirFont());
    }
}
