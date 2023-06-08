package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewFiles extends AppCompatActivity {

    EditText vNumForGet;
    Button getBtn;
    ListView filesListView;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_files);

        vNumForGet = findViewById(R.id.getVehNumDisp);
        getBtn = findViewById(R.id.btnFetch);
        filesListView = findViewById(R.id.filesListView);
        db = FirebaseFirestore.getInstance();

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehicleNumber = vNumForGet.getText().toString().trim();

                if (vehicleNumber.isEmpty()) {
                    Toast.makeText(ViewFiles.this, "Please enter a vehicle number", Toast.LENGTH_SHORT).show();
                } else {
                    fetchFileURLs(vehicleNumber);
                }
            }
        });

        filesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String fileURL = (String) adapterView.getItemAtPosition(position);
                openPDF(fileURL);
            }
        });
    }

    private void fetchFileURLs(String vehicleNumber) {
        CollectionReference collectionRef = db.collection("VehicleFileStorage");
        collectionRef.document(vehicleNumber).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String vnum = documentSnapshot.getId();
                if (documentSnapshot.exists()) {
                    UploadPDF uploadPDF = documentSnapshot.toObject(UploadPDF.class);

                    if (uploadPDF != null) {
                        List<String> fileLabels = new ArrayList<>();
                        fileLabels.add("View License of "+vnum);
                        fileLabels.add("View RC of "+vnum);
                        fileLabels.add("View Insurance of "+vnum);

                        List<String> fileURLs = new ArrayList<>();
                        fileURLs.add(uploadPDF.getLicenseUrl());
                        fileURLs.add(uploadPDF.getRcUrl());
                        fileURLs.add(uploadPDF.getInsuranceUrl());

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewFiles.this,
                                android.R.layout.simple_list_item_1, fileLabels);

                        filesListView.setAdapter(adapter);

                        filesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                String fileURL = fileURLs.get(position);
                                openPDF(fileURL);
                            }
                        });
                    }
                } else {
                    Toast.makeText(ViewFiles.this, "No files found for the vehicle number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


//    private void fetchFileURLs(String vehicleNumber) {
//        CollectionReference collectionRef = db.collection("VehicleFileStorage");
//        collectionRef.document(vehicleNumber).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()) {
//                    UploadPDF uploadPDF = documentSnapshot.toObject(UploadPDF.class);
//                    if (uploadPDF != null) {
//                        List<String> fileURLs = new ArrayList<>();
//                        fileURLs.add(uploadPDF.getLicenseUrl());
//                        fileURLs.add(uploadPDF.getRcUrl());
//                        fileURLs.add(uploadPDF.getInsuranceUrl());
//
//                        ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewFiles.this,
//                                android.R.layout.simple_list_item_1, fileURLs);
//                        filesListView.setAdapter(adapter);
//                    }
//                } else {
//                    Toast.makeText(ViewFiles.this, "No files found for the vehicle number", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    private void openPDF(String fileURL) {
        try {
            Uri uri = Uri.parse(fileURL);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No application available to view PDF", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to open PDF", Toast.LENGTH_SHORT).show();
        }
    }
}
