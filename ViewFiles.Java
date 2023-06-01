package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewFiles extends AppCompatActivity {
    EditText vNumForGet;
    Button getBtn;
    TextView tv;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_files);

        vNumForGet = findViewById(R.id.getVehNumDisp);
        getBtn = findViewById(R.id.btnFetch);
        tv = findViewById(R.id.dispURL);
        db = FirebaseFirestore.getInstance();

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehicleNumber = vNumForGet.getText().toString().trim();

                if (vehicleNumber.isEmpty()) {
                    Toast.makeText(ViewFiles.this, "Please enter a vehicle number", Toast.LENGTH_SHORT).show();
                } else {
                    fetchFileURL(vNumForGet.getText().toString());
                }
            }
        });
    }
    private void fetchFileURL(String vehicleNumber) {
        CollectionReference c = db.collection("VehicleFileStorage");
        c.document(vNumForGet.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //Log.d(documentSnapshot.getData().toString());
                String s = documentSnapshot.toString();
                int urlStartIndex = s.indexOf("url:") + 4; // Find the starting index of the URL
                int urlEndIndex = s.indexOf(",", urlStartIndex); // Find the ending index of the URL
                String urlString = s.substring(urlStartIndex, urlEndIndex).trim(); // Extract the URL
                tv.setText(urlString);
                openPDF(urlString);
            }
        });
    }

//        private void fetchFileURL(String vehicleNumber) {
//        db.collection("VehicleFileStorage")
//                .document(vehicleNumber)
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            String fileURL = documentSnapshot.getString("fileURL");
//                            openPDF(fileURL);
//                        } else {
//                            Toast.makeText(ViewFiles.this, "File not found for the entered vehicle number", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(Exception e) {
//                        Toast.makeText(ViewFiles.this, "Failed to fetch file URL", Toast.LENGTH_SHORT).show();
//                    }
//                });
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
