package com.example.mybookstore.Utils;

import android.app.Application;
import android.graphics.Typeface;

public class MyApplication extends Application {
    private static Typeface Vazir;

    @Override
    public void onCreate() {
        super.onCreate();
        Vazir = Typeface.createFromAsset(getAssets(),Links.LINK_FONT_VAZIR);

    }

    public Typeface getVazirFont(){
        return Vazir;
    }

}
