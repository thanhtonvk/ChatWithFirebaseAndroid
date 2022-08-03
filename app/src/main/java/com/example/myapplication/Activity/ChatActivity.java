package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.Adapter.ChatAdapter;
import com.example.myapplication.Model.Account;
import com.example.myapplication.Model.ChatContent;
import com.example.myapplication.Model.ContactModel;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    List<ChatContent> chatList;
    DatabaseReference reference;
    ChatAdapter chatAdapter;
    RecyclerView recyclerView;
    public static LinearLayout linearLayout;

    Button btn_send;
    EditText edt_content;
    //Tem

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        readMessage(Account.myAcc.getUserName(), ContactModel.userAcc);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edt_content.getText().toString();
                if (!message.equals("")) {
                    sendMessage(Account.myAcc.getUserName(), ContactModel.userAcc, message);
                } else {
                    Toast.makeText(getApplicationContext(), "The feild is not empty", Toast.LENGTH_LONG).show();
                }
                edt_content.setText("");
            }
        });
    }

    private void initView() {
        linearLayout = findViewById(R.id.layout_playmusic);
        btn_send = findViewById(R.id.btn_send);
        edt_content = findViewById(R.id.edt_content);
        recyclerView = findViewById(R.id.lv_chat);

    }

    private void sendMessage(String sender, String reciever, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("senderUser", sender);
        hashMap.put("receiveUser", reciever);
        hashMap.put("message", message);
        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessage(String myID, String userID) {
        chatList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatContent chat = dataSnapshot.getValue(ChatContent.class);
                    if (chat.getReceiveUser().equals(myID) && chat.getSenderUser().equals(userID) || chat.getReceiveUser().equals(userID) && chat.getSenderUser().equals(myID)) {
                        chatList.add(chat);
                        chatAdapter = new ChatAdapter(ChatActivity.this, chatList);
                        recyclerView.setAdapter(chatAdapter);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}