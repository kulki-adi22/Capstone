package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button b1;
    EditText vehNum, rName, age;
    TextView numDisplay;
    FirebaseFirestore db, dbpush;
    NumPush vehObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //String s;
        vehObj = new NumPush();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.vehNumSubmitBtn);
        vehNum = findViewById(R.id.editTextText);
        rName = findViewById(R.id.riderName);
        age = findViewById(R.id.riderAge);
        //numDisplay = findViewById(R.id.numDisplay);
        db = FirebaseFirestore.getInstance();
        dbpush = FirebaseFirestore.getInstance();
        Intent intent= new Intent(MainActivity.this, Activity3.class);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehicleNumber= vehNum.getText().toString();
                //vehNum.setVisibility(View.INVISIBLE);
                String s1 = rName.getText().toString();
                //Toast.makeText(getApplicationContext(),s1,Toast.LENGTH_SHORT).show();
                String s2 = age.getText().toString();
                //Toast.makeText(getApplicationContext(),s2,Toast.LENGTH_SHORT).show();

                //numDisplay.setText(s);
                //numDisplay.setVisibility(View.VISIBLE);
                vehObj.setVehNum(vehicleNumber);
                vehObj.setRName(s1);
                vehObj.setAge(s2);
                String collection ="VehicleNumberStorage";
                String document="VehNum";
//                Toast.makeText(getApplicationContext(),vehObj.getVehNum(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),vehObj.getRName(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),vehObj.getAge(),Toast.LENGTH_SHORT).show();
                CollectionReference vn = db.collection("VehicleNumberStorage");
                vn.document(vehicleNumber).set(vehObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"Added to firestore",Toast.LENGTH_SHORT).show();
                    }
                });
//                db.collection(collection)
//                        .add(vehicleNumber)
//                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                // Document was successfully written with a unique ID
//                                startActivity(intent);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(Exception e) {
//                                // Error occurred while writing the document
//                                Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });

                //startActivity(intent);
            }
        });
    }
}
