package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.FirebaseAPI.AuthApi;
import com.example.myapplication.FirebaseAPI.FirebaseDBApi;
import com.example.myapplication.Model.Account;
import com.example.myapplication.R;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ImageView fbBtn;
    CallbackManager callbackManager;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    TextView tvRegister;
    AuthApi authApi;
    FirebaseDBApi accountApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authApi = new AuthApi();
        accountApi = new FirebaseDBApi("Account");
        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && accessToken.isExpired() == false) {
            startActivity(new Intent(MainActivity.this, SecondActivity2.class));
            finish();
        }

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(MainActivity.this, "LOGNIN SUCCESSFULL", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this, SecondActivity2.class));
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        googleBtn = findViewById(R.id.google_btn);
        tvRegister = findViewById(R.id.user_register);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToRegisterActivity();
            }


        });


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            navigateToSecondActivity();
        }

        googleBtn = findViewById(R.id.google_btn);
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();

            }
        });
        fbBtn = findViewById(R.id.fb_btn);
        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: ");
//                startActivity(new Intent(MainActivity.this, SecondActivity2.class));
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));
            }
        });


        TextView username = (TextView) findViewById(R.id.email);
        TextView password = (TextView) findViewById(R.id.password);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.logintn);

        //admin and admin

        loginbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("Logging");
                progressDialog.show();
                if (username.getText().toString().equals("") || !username.getText().toString().contains("@") || password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Information ivalid", Toast.LENGTH_LONG).show();
                } else {
                    authApi.auth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                accountApi.reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            Account account = dataSnapshot.getValue(Account.class);
                                            if (account.getEmail().equals(username.getText().toString())) {
                                                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                                                intent.putExtra("Account", account);
                                                startActivity(intent);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            } else {
                                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                progressDialog.dismiss();

            }

        });
    }

    private void navigateToRegisterActivity() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void SignIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "SOMETHING WENT WRONG", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void navigateToSecondActivity() {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}