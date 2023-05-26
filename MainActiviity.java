package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button b1;
    EditText vehNum;
    TextView numDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //String s;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.vehNumSubmitBtn);
        vehNum = findViewById(R.id.editTextText);
        numDisplay = findViewById(R.id.numDisplay);
        Intent intent= new Intent(MainActivity.this, Activity2.class);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s= vehNum.getText().toString();
                vehNum.setVisibility(View.INVISIBLE);
                //numDisplay.setText(s);
                //numDisplay.setVisibility(View.VISIBLE);
                startActivity(intent);
            }
        });

    }
}
