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

public class EditInformation extends AppCompatActivity {
    private EditText edName,edFamily,edAddress,edEmail;
    private ImageView imgback;
    private TextView txtTitle;
    private Button btnEdit;
    private String phone;
    private ApiServices apiServices = new ApiServices(EditInformation.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);
        findViews();
        UserSharedPrefrences userSharedPrefrences = new UserSharedPrefrences(EditInformation.this);
        phone =userSharedPrefrences.getUserPhone();
        receiveInformation();
        onClicks();
    }

    private void receiveInformation() {
        apiServices.receivePersonalInformation(phone, new ApiServices.OnInformationReceived() {
            @Override
            public void onReceived(String name, String family, String email, String address) {
                edAddress.setText(address);
                edEmail.setText(email);
                edFamily.setText(family);
                edName.setText(name);
            }
        });
    }

    private void onClicks() {

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiServices.editInformation(phone, edEmail.getText().toString().trim(), edAddress.getText().toString().trim(),
                        edName.getText().toString().trim(), edFamily.getText().toString().trim(), new ApiServices.OnInformationEdit() {
                            @Override
                            public void onEdit(int editStatus) {
                                if (editStatus==Put.STATUS_SUCCESS){
                                    Toast.makeText(EditInformation.this, "اطلاعات با موفقیت ویرایش شد", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });



    }

    private void findViews() {
        edAddress = findViewById(R.id.ed_edit_address);
        edEmail = findViewById(R.id.ed_edit_email);
        edFamily = findViewById(R.id.ed_edit_family);
        edName = findViewById(R.id.ed_edit_name);
        imgback=findViewById(R.id.img_back_second_toolbar);
        txtTitle=findViewById(R.id.txt_title_toolbar_second);
        txtTitle.setText("ویرایش اطلاعات");
        btnEdit=findViewById(R.id.btn_edit_edit_activity);

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
