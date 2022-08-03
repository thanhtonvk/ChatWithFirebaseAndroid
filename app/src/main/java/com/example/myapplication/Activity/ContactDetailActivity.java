package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Model.ContactModel;
import com.example.myapplication.R;

public class ContactDetailActivity extends AppCompatActivity {
    TextView tvUserName;
    TextView tvPhone;
    TextView tvAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        onInit();
        onGetIntent();
    }

    private void onGetIntent() {
        Intent intent = getIntent();
        ContactModel model = (ContactModel)intent.getSerializableExtra("ContactModel");
        tvUserName.setText("Name: " + model.getUserContact());
        tvAddress.setText("Email: " + model.getEmailContact());
    }

    private void onInit(){
        tvUserName = findViewById(R.id.contact_detail_tvUserName);
        tvPhone = findViewById(R.id.contact_detail_tvPhone);
        tvAddress = findViewById(R.id.contact_detail_tvAddress);
    }

}