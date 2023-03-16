package com.example.thenewappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {




    //view from xml


    Toolbar toolbar;
    RecyclerView recyclerView;
    ImageView profileIv;
    TextView nameTv, userStatusTv;
    EditText messageEt;
    ImageButton sendBtn;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference usersDbRef;


    String hisUid;
    String myUid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle("");
    recyclerView = findViewById(R.id.chat_recycleView);
    profileIv = findViewById(R.id.profileIv);
    nameTv = findViewById(R.id.nameTv);
    userStatusTv = findViewById(R.id.userStatusTv);
    messageEt = findViewById(R.id.messageEt);
    sendBtn = findViewById(R.id.sendBtn);


    //Nhận UID từ userlist Adapter Users
        //Ứng dụng để nhận uid làm tính năng đặt lịch
        Intent intent = getIntent();
        hisUid = intent.getStringExtra("hisUid");




    firebaseAuth = FirebaseAuth.getInstance();
    firebaseDatabase = FirebaseDatabase.getInstance();
    usersDbRef = firebaseDatabase.getReference("Users");

        Query userQuery = usersDbRef.orderByChild("uid").equalTo(hisUid);
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //chech đến khi tìm duoc info
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    //lấy data
                    String name = ""+ds.child("name").getValue();
                    String image =""+ds.child("image").getValue();
                    nameTv.setText(name);
                    try {// tìm thấy image, bắn image qua image view
                        Picasso.get().load(image).placeholder(R.drawable.ic_default_img).into(profileIv);

                    }
                    catch (Exception e){
                        //khong tim thay thi set default image
                        Picasso.get().load("https://img.freepik.com/premium-vector/man-avatar-profile-picture-vector-illustration_268834-538.jpg").into(profileIv);
                    }

                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        //click button để send message
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messeage = messageEt.getText().toString().trim();

                //chech xem tin nhắn có empty hay không
                if (TextUtils.isEmpty(messeage)){
                    Toast.makeText(ChatActivity.this, "Nhắn như thế thì làm sao mà gửi đây", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendMessage(messeage);
                }
            }
        });


    }

    private void sendMessage(String messeage) {

        //Tạo node Chats

        // Tin nhắn sẽ chứa trong nhánh con message của node chats
        //Uid sẽ chứa trong sender và receiver

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", myUid);
        hashMap.put("receiver",hisUid);
        hashMap.put("messeage", messeage);
        databaseReference.child("Chats").push().setValue(hashMap);

        //reset lại khung nhập tin nhắn sau khi gửi tin
        messageEt.setText("");

    }

    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){

            myUid = user.getUid();

            //da dang nhap
            //show email
//            mProfileTv.setText(user.getEmail());
        }
        else {
            //chua dang nhap
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }

    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //hide search view

        menu.findItem(R.id.action_search).setVisible(false);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id  = item.getItemId();
        if (id ==  R.id.action_logout){
            firebaseAuth.signOut();
            checkUserStatus();
        }


        return super.onOptionsItemSelected(item);
    }
}