package com.example.mybookstore.Activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybookstore.R;
import com.example.mybookstore.Utils.ApiServices;
import com.example.mybookstore.Utils.ImageGallery;
import com.example.mybookstore.Utils.Links;
import com.example.mybookstore.Utils.Put;
import com.example.mybookstore.Utils.RuntimePermissionsActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivityND extends RuntimePermissionsActivity {

    CardView cardRegister;
    EditText edEmail,edPass,edPhone,edAddres;
    CheckBox chkpass;
    ImageView imgBack;
    TextView txtTitle,txtImage;
    private String imageEncode = "";
    private CircleImageView circleImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViews();
        chkpass.setChecked(true);
//        onClicks();

        cardRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiServices apiServices = new ApiServices(RegisterActivityND.this);
                apiServices.registerUserWithOutImage(edAddres.getText().toString().trim(), new ApiServices.OnSignUpComplete() {
                    @Override
                    public void onSignUp(int responseStatus) {
                        Toast.makeText(RegisterActivityND.this, responseStatus+"", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void onClicks() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        chkpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkpass.isChecked()){
                    edPass.setInputType(InputType.TYPE_CLASS_TEXT);
                }else {edPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_NUMBER);}
            }
        });

        cardRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = edPhone.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                final String password = edPass.getText().toString().trim();
                String address = edAddres.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty() && !phone.isEmpty() && !address.isEmpty()){
                    if (phone.length() == 11){
                        if (password.length() >= 4){
                            if (isEmailValid(email)){
                                if (!imageEncode.equals("")){
                                    ApiServices apiServices = new ApiServices(RegisterActivityND.this);
                                    apiServices.registerUser(imageEncode,phone, email, password, address, new ApiServices.OnSignUpComplete() {
                                        @Override
                                        public void onSignUp(int responseStatus) {
                                            switch (responseStatus){
                                                case Put.STATUS_USER_EXIST:
                                                    Toast.makeText(RegisterActivityND.this,"کاربری با این این شماره تلفن موجود است",Toast.LENGTH_LONG).show();
                                                    break;
                                                case Put.STATUS_FAILED:
                                                    Toast.makeText(RegisterActivityND.this, "متاسفانه خطایی رخ داد", Toast.LENGTH_SHORT).show();
                                                    break;
                                                case Put.STATUS_SUCCESS:
                                                    Toast.makeText(RegisterActivityND.this, "ثبت نام با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent();
                                                    intent.putExtra(Put.phone,phone);
                                                    intent.putExtra(Put.password,password);
                                                    setResult(RESULT_OK,intent);
                                                    finish();
                                                    break;
                                            }
                                        }
                                    });
                                }else
                                    Toast.makeText(RegisterActivityND.this, "لطفا یک عکس انتخاب کنید", Toast.LENGTH_SHORT).show();

                            }else Toast.makeText(RegisterActivityND.this, "لطفا یک ایمیل معتبر وارد کنید", Toast.LENGTH_SHORT).show();

                        }else Toast.makeText(RegisterActivityND.this, "پسورد حداقل باید 4 کاراکتر باشد", Toast.LENGTH_SHORT).show();

                    }else Toast.makeText(RegisterActivityND.this, "لطفا شماره تلفن را به درستی وارد کنید", Toast.LENGTH_SHORT).show();
                    
                }else Toast.makeText(RegisterActivityND.this, "تمامی فیلد هارا تکمیل کنید", Toast.LENGTH_SHORT).show();
            }
        });

        txtImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivityND.super.requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},Put.EXTERNAL_STORAGE);
            }
        });



    }





    private void findViews() {
        edAddres = findViewById(R.id.ed_address_register);
        edEmail = findViewById(R.id.ed_email_register);
        edPass = findViewById(R.id.ed_password_register);
        edPhone = findViewById(R.id.ed_phone_register);
        chkpass = findViewById(R.id.chk_show_pass_register);
        chkpass.setTypeface(Typeface.createFromAsset(getAssets(), Links.LINK_FONT_VAZIR));
        txtTitle = findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText("ثبت نام");
        cardRegister = findViewById(R.id.card_Register);
        imgBack = findViewById(R.id.img_back_second_toolbar);
        circleImageView = findViewById(R.id.img_user_register);
        txtImage = findViewById(R.id.txt_image_user_register);
    }

    private  boolean isEmailValid(String email){
       return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void getImage() {

        ImageGallery.showImage(RegisterActivityND.this,Put.CHOOSE_GALLERY);
    }




    @Override
    public void onPermissionsGranted(int requestCode) {

        if (requestCode == Put.EXTERNAL_STORAGE){
            getImage();
        }

    }

    @Override
    public void onPermissionsDeny(int requestCode) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==Put.CHOOSE_GALLERY && resultCode == RESULT_OK){

            Bitmap bitmap = ImageGallery.getBitmap(RegisterActivityND.this,data.getData());
            imageEncode = ImageGallery.getStringImage(bitmap,300);
            circleImageView.setImageBitmap(bitmap);
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
