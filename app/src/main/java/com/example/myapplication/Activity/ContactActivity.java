package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.Adapter.ContactAdapter;
import com.example.myapplication.FirebaseAPI.FirebaseDBApi;
import com.example.myapplication.Model.Account;
import com.example.myapplication.Model.ContactModel;
import com.example.myapplication.R;;;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    ListView listView;
    static List<ContactModel> modelList = new ArrayList<>();
    static ContactAdapter adapter;
    Button btnAddItem;
    Account account;
    FirebaseDBApi dbApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        onInit();
        modelList = new ArrayList<>();
        dbApi = new FirebaseDBApi("Contact");
        account = (Account) getIntent().getSerializableExtra("Account");
        Account.acc = account;
        Account.myAcc = account;
        adapter = new ContactAdapter(ContactActivity.this, R.layout.contact_item, modelList);
        listView.setAdapter(adapter);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactActivity.this, EditContactActivity.class);
                intent.putExtra("Account", account);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSetData();
            }
        });

        onSetData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContactModel.userAcc = modelList.get(i).getUserContact();
                startActivity(new Intent(ContactActivity.this, ChatActivity.class));
            }
        });
    }

    private void onSetData() {
        modelList = new ArrayList<>();
        dbApi.reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    ContactModel model = dataSnapshot.getValue(ContactModel.class);
                    Log.e("LOG", model.getUser());
                    if (model.getUser().equals(account.getUserName())) {
                        modelList.add(model);

                        adapter = new ContactAdapter(ContactActivity.this, R.layout.contact_item, modelList);
                        listView.setAdapter(adapter);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onInit() {
        listView = findViewById(R.id.lv_Contact);
        btnAddItem = findViewById(R.id.contact_Add);
    }
}

