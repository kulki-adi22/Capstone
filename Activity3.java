package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Activity3 extends AppCompatActivity {

    Button fileUpload;
    Button uploadBtn,btn;
    StorageReference storageReference;
    FirebaseFirestore databaseReference;
    TextView confirm;
    EditText editText;
    EditText numInput;
    ProgressDialog progressDialog;
    Uri pdfUri;
    private static final int PERMISSION_REQUEST_CODE = 123;
    String vNumber;
    boolean isStoragePermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        //fileUpload = findViewById(R.id.uploadFile);
        editText = findViewById(R.id.editText);
        uploadBtn = findViewById(R.id.uploadBtn);
        confirm = findViewById(R.id.confirmUpload);
        //String vNum = MainActivity.vNumber;
        numInput = findViewById(R.id.vehicleNumber);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseFirestore.getInstance();
        //btn = findViewById(R.id.btn);
        // Inside onCreate() method
//        Intent intent = getIntent();
//        vNumber = intent.getStringExtra("vNumber");

        //String vN = numInput.getText().toString();

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();
            }
        });

    }
    private void selectPDF() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF file select"),12);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData()!=null) {
            editText.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));
            uploadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadPDFFileFirebase(data.getData());
                }
            });
        } else {
            Toast.makeText(Activity3.this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadPDFFileFirebase(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is loading...");
        progressDialog.show();
        StorageReference reference = storageReference.child("Upload"+numInput.getText().toString()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(Activity3.this,"File Uploaded",Toast.LENGTH_SHORT).show();
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();
                        String pdfUrl = uri.toString();
                        //CollectionReference c = databaseReference.collection("VehicleFileStorage");
                        DocumentReference documentReference = databaseReference.collection("VehicleFileStorage")
                                .document(numInput.getText().toString());
                        PutPDF putPDF = new PutPDF(confirm.getText().toString(),uri.toString(),MainActivity.vNumber.toString());
                        documentReference.set(putPDF)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Activity3.this, "File Uploaded and URI saved", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Activity3.this, "Failed to save URI", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        progressDialog.setMessage("File Uploaded "+(int)progress+"%");

                    }
                });
    }

}

