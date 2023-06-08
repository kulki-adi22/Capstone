package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RiderChek extends AppCompatActivity {
    EditText riderVnumVerify;
    Button vnumCheckBtn;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_chek);
        riderVnumVerify = findViewById(R.id.riderVnumVerify);
        vnumCheckBtn = findViewById(R.id.riderVnumBtn);
        db = FirebaseFirestore.getInstance();

        vnumCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String documentId = riderVnumVerify.getText().toString().trim();
                if (!documentId.isEmpty()) {
                    checkDocumentExists(documentId);
                } else {
                    Toast.makeText(RiderChek.this, "Please enter a document ID", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkDocumentExists(String documentId) {
        Intent intentview = new Intent(RiderChek.this,ViewFiles.class);
        Intent intentadd = new Intent(RiderChek.this,MainActivity.class);
        db.collection("VehicleNumberStorage").document(documentId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            Toast.makeText(RiderChek.this, "Vehicle already registered", Toast.LENGTH_SHORT).show();
                            startActivity(intentview);
                        } else {
                            Toast.makeText(RiderChek.this, "New Vehicle", Toast.LENGTH_SHORT).show();
                            startActivity(intentadd);
                        }
                    } else {
                        Toast.makeText(RiderChek.this, "Failed to check document", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
