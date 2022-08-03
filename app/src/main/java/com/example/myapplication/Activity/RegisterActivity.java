package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DAO.UserDAO;
import com.example.myapplication.Model.Account;
import com.example.myapplication.R;

public class RegisterActivity extends AppCompatActivity {
    TextView tvSignin;
    EditText edtUsername,edtEmail,edtPassword,edtConfirmPassword;
    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userDAO = new UserDAO(RegisterActivity.this);
        initView();
        tvSignin = findViewById(R.id.login_registerr);
        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateOnLoginActivity();
            }
        });
        findViewById(R.id.Registerbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtUsername.getText().toString().equals("") || !edtEmail.getText().toString().contains("@")||edtPassword.getText().toString().equals("")||!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"Thông tin nhập không chính xác",Toast.LENGTH_LONG).show();
                }else{
                    Account account = new Account(edtUsername.getText().toString(),edtEmail.getText().toString(),edtPassword.getText().toString());
                    userDAO.register(account);
                }

            }
        });
    }
    private void navigateOnLoginActivity(){
        finish();
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void initView(){
        edtUsername = findViewById(R.id.username);
        edtEmail= findViewById(R.id.email);
        edtPassword= findViewById(R.id.password);
        edtConfirmPassword= findViewById(R.id.confirmpassword);

    }
}
