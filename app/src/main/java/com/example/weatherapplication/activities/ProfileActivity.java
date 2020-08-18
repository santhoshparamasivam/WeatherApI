package com.example.weatherapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.weatherapplication.R;
import com.example.weatherapplication.sessionmanager.SessionKeys;
import com.example.weatherapplication.sessionmanager.SessionManager;

public class ProfileActivity extends AppCompatActivity {

    TextView userEmail,userName;

    SessionManager sessionManager;

    ImageView ImageView;
    Toolbar toolbar_main;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userName=findViewById(R.id.userName);
        userEmail=findViewById(R.id.userEmail);
        ImageView=findViewById(R.id.ImageView);
//        toolbar_main=findViewById(R.id.toolbar_main);
        sessionManager=new SessionManager(this);
//        toolbar_main.setTitle("Profile");

//       toolbar_main.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              finish();
//            }
//        });


        String FirstName=sessionManager.getStringKey(SessionKeys.FIRST_NAME);
        String Email=sessionManager.getStringKey(SessionKeys.EMAIL);
        String pic=sessionManager.getStringKey(SessionKeys.PHOTO);
        if (Email!=null&& !(Email.isEmpty())){
           userEmail.setText(Email);
        }

        if (FirstName!=null&& !(FirstName.isEmpty())){
            userName.setText(FirstName);
        }
        if (pic!=null&& !(pic.isEmpty())){
            Glide.with(this)
                    .load(pic)
                    .into(ImageView);
        }



    }
}