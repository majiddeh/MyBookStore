package com.example.mybookstore.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mybookstore.R;

import mehdi.sakout.aboutpage.AboutPage;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        View aboutPage = new AboutPage(this)
                .isRTL(true)
                .setImage(R.drawable.banner2)
                //.addItem(versionElement)
               // .addItem(adsElement)
                .addGroup("ارتباط با ما")
                .setDescription("فروشگاه کتاب صامد ارائه کتاب های بروز در همه زمینه ها")
                .addEmail("www.samed.ir")
                .addWebsite("http://medyo.github.io/")
                .addFacebook("the.medy")
                .addTwitter("medyo80")
                .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                .addPlayStore("com.ideashower.readitlater.pro")
                .addGitHub("medyo")
                .addInstagram("majiddehghan74")
                .create();
        setContentView(aboutPage);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
