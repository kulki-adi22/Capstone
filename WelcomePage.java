package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomePage extends AppCompatActivity {
    Button policeBtn, riderBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        policeBtn = findViewById(R.id.policeBtn);
        riderBtn = findViewById(R.id.riderBtn);
        Intent policeI = new Intent(WelcomePage.this,PoliceVerify.class);
        Intent riderI = new Intent(WelcomePage.this,RiderChek.class);
        policeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(policeI);
            }
        });
        riderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(riderI);
            }
        });
    }
}
