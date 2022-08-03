package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class SecondActivity2 extends AppCompatActivity {
    ImageView imageView;
    TextView name;
    Button logOubtn;
    EditText edtUsername;
    EditText edtPassword;
    Button btnPushData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnPushData = findViewById(R.id.btnPushData);
        logOubtn = findViewById(R.id.loguot);
        imageView = findViewById(R.id.imageView);
        name= findViewById(R.id.name);
        btnPushData.setOnClickListener(view -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("list_user");
            myRef.setValue(edtUsername.getText().toString(), (error, ref) -> {
                Toast.makeText(this, "Push Complete", Toast.LENGTH_SHORT).show();
            });
        });
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if (object!=null){
                            try {
                                String fullName = object.getString("name");
                                String url = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                Log.e("TAG", "onCompleted: "+url );
                                name.setText(fullName);
                                Picasso.get().load(url).into(imageView);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Log.e("TAG", "onCompleted: " );
                        }

                        // Application code
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();

        logOubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                startActivity(new Intent(SecondActivity2.this,MainActivity.class));
                finish();
            }
        });

    }
}