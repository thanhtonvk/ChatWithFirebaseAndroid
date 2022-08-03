package com.example.myapplication.DAO;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.FirebaseAPI.AuthApi;
import com.example.myapplication.FirebaseAPI.FirebaseDBApi;
import com.example.myapplication.Model.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class UserDAO {
    private AuthApi authApi;
    private Context context;
    private FirebaseDBApi firebaseDBApi;
    public UserDAO(Context context) {
        this.context = context;
        authApi = new AuthApi();
        firebaseDBApi = new FirebaseDBApi("Account");
    }
    public void register(Account account) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Registering");
        dialog.show();
        authApi.auth.createUserWithEmailAndPassword(account.getEmail(), account.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseDBApi.reference.child(account.getUserName()).setValue(account).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(context,"Register successfully",Toast.LENGTH_LONG).show();
                            }else {

                                Toast.makeText(context,"Register failed - loi 1",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(context,"Register failed",Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }
}
