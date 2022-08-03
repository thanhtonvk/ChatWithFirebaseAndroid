package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.UserLoginDetailModel;

public class UserLoginDetailActivity extends AppCompatActivity {
    TextView tvUserName;
    TextView tvPassword;
    TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_detail);
        onInit();
        onGetIntent();
    }
    private void onGetIntent(){
        Intent intent = getIntent();
        UserLoginDetailModel model = (UserLoginDetailModel) intent.getSerializableExtra("UserLoginDetailModel");
        tvUserName.setText("UserName: " + model.getUserName());
        tvPassword.setText("Password: " + model.getPassword());
        tvEmail.setText("Email: " + model.getEmail());
    }
    private void onInit(){
        tvUserName = findViewById(R.id.user_login_detail_tvUserName);
        tvPassword = findViewById(R.id.user_login_detail_tvPassword);
        tvEmail = findViewById(R.id.user_login_detail_tvEmail);
    }
}