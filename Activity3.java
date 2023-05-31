package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Activity3 extends AppCompatActivity {

    Button fileUpload;
    Button uploadBtn;
    StorageReference storageReference;
    FirebaseFirestore databaseReference;
    TextView confirm;
    EditText editText;
    ProgressDialog progressDialog;
    Uri pdfUri;
    private static final int PERMISSION_REQUEST_CODE = 123;

    boolean isStoragePermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        //fileUpload = findViewById(R.id.uploadFile);
        editText = findViewById(R.id.editText);
        uploadBtn = findViewById(R.id.uploadBtn);
        confirm = findViewById(R.id.confirmUpload);
        String vNum = MainActivity.vNumber;
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseFirestore.getInstance();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    selectPDF();
            }
        });

//        uploadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (pdfUri != null) {
//                    uploadFile(pdfUri);
//                } else {
//                    Toast.makeText(Activity3.this, "Please select a file", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
//
//    private void showPermissionRequestWidget() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Storage Permission")
//                .setMessage("Please grant permission to access external storage.")
//                .setPositiveButton("Grant", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Request the permission when the user clicks "Grant"
//                        requestStoragePermission();
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Handle the case when the user clicks "Cancel"
//                        // For example, show a toast or close the activity
//                        Toast.makeText(Activity3.this, "Permission denied. Cannot proceed.", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                })
//                .setCancelable(false)
//                .show();
//    }

//    private void requestStoragePermission() {
//        ActivityCompat.requestPermissions(Activity3.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//    }
//    private void uploadFile(Uri pdfUri) {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressDialog.setTitle("Uploading");
//        progressDialog.show();
//        progressDialog.setProgress(0);
//        storageReference.child("Uploads").child(MainActivity.riderName).putFile(pdfUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        String uri = taskSnapshot.getUploadSessionUri().toString();
//
//                        Toast.makeText(Activity3.this, uri + "Successfully Uploaded", Toast.LENGTH_SHORT).show();
//                        CollectionReference c = databaseReference.collection("VehicleFileStorage");
//                        c.document(MainActivity.vNumber).set(uri).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(Activity3.this, "URI Successfully Uploaded", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Activity3.this, "Error! Upload Agaian", Toast.LENGTH_SHORT).show();
//
//                    }
//                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                        int currentProgress = (int) (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
//                        progressDialog.setProgress(currentProgress);
//                    }
//                });
//
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted
//                isStoragePermissionGranted = true;
//                selectPDF();
//            } else {
//                // Permission denied
//                Toast.makeText(Activity3.this, "Please grant permission to access storage",
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//    }



//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Toast.makeText(Activity3.this, "Req Per 1", Toast.LENGTH_SHORT).show();
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Toast.makeText(Activity3.this, "Req Per 2", Toast.LENGTH_SHORT).show();
//
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, proceed with selecting PDF
//                Toast.makeText(Activity3.this, "Req Per 3", Toast.LENGTH_SHORT).show();
//
//                selectPDF();
//            } else {
//                // Permission denied, show a message or handle it as needed
//                Toast.makeText(Activity3.this, "Please grant permission to access external storage", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }d


    //    private void selectPDF(){
//        Intent intent = new Intent();
//        intent.setType("application/pdf");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent,86);
//    }
    private void selectPDF() {
//        if (ContextCompat.checkSelfPermission(Activity3.this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED) {
//            // Permission already granted, proceed with selecting PDF
//            Intent intent = new Intent();
//            intent.setType("application/pdf");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(intent, 86);
//        }
////        else {
////            // Permission not granted, request it
////            ActivityCompat.requestPermissions(Activity3.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
////        }
//        else {
//            // Permission not granted, request it
//
//            ActivityCompat.requestPermissions(Activity3.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//        }
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
        StorageReference reference = storageReference.child("Upload"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(Activity3.this,"File Uploaded",Toast.LENGTH_SHORT).show();
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();
                        PutPDF putPDF = new PutPDF(confirm.getText().toString(),uri.toString(),MainActivity.vNumber);
                        CollectionReference c = databaseReference.collection("VehicleFileStorage");
                        c.document(MainActivity.vNumber).set(putPDF).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Activity3.this,"URI Uploaded",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
//            pdfUri = data.getData();
//        } else {
//            Toast.makeText(Activity3.this, "Please select a file", Toast.LENGTH_SHORT).show();
//        }
//    }
}
