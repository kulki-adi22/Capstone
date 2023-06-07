package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    Button b1;
    EditText rName, age, fileUpload;
    public static EditText vehNum;
    TextView numDisplay;
    FirebaseFirestore db, dbpush;
    NumPush vehObj;
    public static String vNumber,riderName;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        vehObj = new NumPush();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.vehNumSubmitBtn);
        vehNum = findViewById(R.id.vehNum);
        rName = findViewById(R.id.riderName);
        age = findViewById(R.id.riderAge);
        db = FirebaseFirestore.getInstance();
        dbpush = FirebaseFirestore.getInstance();
        vNumber= vehNum.getText().toString();
        riderName = rName.getText().toString();
        PutPDF putPDF = new PutPDF();
        putPDF.setVehicleNumber(vehNum.getText().toString());
        Intent intent= new Intent(MainActivity.this, Activity3.class);
        final String[] vNumber = {vehNum.getText().toString()}; // Get the value from vehNum EditText

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehicleNumber= vehNum.getText().toString();
                String s1 = rName.getText().toString();
                String s2 = age.getText().toString();
                vehObj.setVehNum(vehicleNumber);
                vehObj.setRName(s1);
                vehObj.setAge(s2);
                String collection ="VehicleNumberStorage";
                String document="VehNum";

                CollectionReference vn = db.collection("VehicleNumberStorage");
                vn.document(vehicleNumber).set(vehObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"Added to firestore",Toast.LENGTH_SHORT).show();
                        HoldVehNum.vehicleNumber = vehObj.getVehNum();
                    }
                });
                startActivity(intent);
            }
        });

        intent.putExtra("vNumber", vNumber[0]);

        ImageButton cameraButton = findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "No camera app available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Image captured, you can do something with it here
            Bundle extras = data.getExtras();
            // Retrieve the captured image
            if (extras != null) {
                imageUri = data.getData();
                // Do something with the image URI
            }
        }
    }
}
