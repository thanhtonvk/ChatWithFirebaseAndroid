package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.DAO.ContactDAO;
import com.example.myapplication.FirebaseAPI.FirebaseDBApi;
import com.example.myapplication.Model.Account;
import com.example.myapplication.Model.ContactModel;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class UpdateChiTietActivity extends AppCompatActivity {
    Button btnCancel;
    Button btnSave;
    EditText edtName, edtPhone;
    String mName = "";
    String mPhone = "";
    Account account;
    FirebaseDBApi firebaseDBApi;
    ContactDAO contactDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_chi_tiet);
        Intent intent = getIntent();
        ContactModel model = (ContactModel) intent.getSerializableExtra("ContactModel");
        firebaseDBApi = new FirebaseDBApi("Account");
        contactDAO = new ContactDAO(UpdateChiTietActivity.this);
        account = (Account) getIntent().getSerializableExtra("Account");
        onInit();
        edtName.setText(model.getUserContact());
        edtPhone.setText(model.getPhoneNumber());
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidateFrom()) {
                    firebaseDBApi.reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren()
                            ) {
                                Account acc = data.getValue(Account.class);
                                Log.e("LOG", acc.toString());
                                Log.e("LOG", String.valueOf(acc.getUserName()));
                                if (mName.equals(acc.getUserName()) && !mName.equals(account.getUserName())) {
                                    ContactModel contactModel = new ContactModel(account.getUserName(), acc.getUserName(), acc.getEmail(), mPhone);
                                    contactDAO.updateContact(contactModel);
                                    onClearFrom();
                                    finish();
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

    private boolean ValidateFrom() {
        mName = edtName.getText().toString();
        mPhone = edtPhone.getText().toString();
        if (mName.length() < 1) {
            edtName.setError("Name cannot be null");
            return false;
        }
        if (mPhone.length() < 1) {
            edtPhone.setError("Phone cannot be null");
            return false;
        }

        return true;
    }

    private void onClearFrom() {
        edtName.clearComposingText();
        edtPhone.clearComposingText();
    }

    private void onInit() {
        btnCancel = findViewById(R.id.Contact_edit_btnCancel);
        btnSave = findViewById(R.id.Contact_edit_btnSave);
        edtName = findViewById(R.id.contact_edit_edtName);
        edtPhone = findViewById(R.id.contact_edit_phonenumber);
    }
}

