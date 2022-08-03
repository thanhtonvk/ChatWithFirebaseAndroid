package com.example.myapplication.DAO;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.FirebaseAPI.FirebaseDBApi;
import com.example.myapplication.Model.ContactModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ContactDAO {
    private FirebaseDBApi firebaseDBApi;
    private Context context;

    public ContactDAO(Context context) {
        this.context = context;
        firebaseDBApi = new FirebaseDBApi("Contact");
    }

    public void updateContact(ContactModel model) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait");
        progressDialog.show();
        firebaseDBApi.reference.child(model.getUser() + model.getUserContact()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    public void deleteContact(ContactModel model) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait");
        progressDialog.show();
        firebaseDBApi.reference.child(model.getUser() + model.getUserContact()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}
