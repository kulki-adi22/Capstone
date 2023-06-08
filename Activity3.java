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

    Button uploadBtn, returnBtn;
    EditText licenseEditText, rcEditText, insuranceEditText;
    ProgressDialog progressDialog;
    Uri licenseUri, rcUri, insuranceUri;
    StorageReference storageReference;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        uploadBtn = findViewById(R.id.uploadBtn);
        returnBtn = findViewById(R.id.returnBtn);
        licenseEditText = findViewById(R.id.uploadLicense);
        rcEditText = findViewById(R.id.uploadRCCard);
        insuranceEditText = findViewById(R.id.uploadInsurance);

        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
        Intent returnIntent = new Intent(Activity3.this,WelcomePage.class);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(returnIntent);
            }
        });
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFiles();
            }
        });

        licenseEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile(1);
            }
        });

        rcEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile(2);
            }
        });

        insuranceEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile(3);
            }
        });
    }

    private void selectFile(int fileType) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), fileType);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedFileUri = data.getData();
            switch (requestCode) {
                case 1:
                    licenseUri = selectedFileUri;
                    licenseEditText.setText(getFileName(selectedFileUri));
                    break;
                case 2:
                    rcUri = selectedFileUri;
                    rcEditText.setText(getFileName(selectedFileUri));
                    break;
                case 3:
                    insuranceUri = selectedFileUri;
                    insuranceEditText.setText(getFileName(selectedFileUri));
                    break;
            }
        }
    }

    private String getFileName(Uri uri) {
        String filePath = uri.getPath();
        return filePath.substring(filePath.lastIndexOf('/') + 1);
    }

    private void uploadFiles() {
        if (licenseUri != null && rcUri != null && insuranceUri != null) {
            progressDialog = new ProgressDialog(Activity3.this);
            progressDialog.setTitle("Uploading files...");
            progressDialog.show();

            // Upload license file
            StorageReference licenseRef = storageReference.child("licenses").child(getFileName(licenseUri));
            licenseRef.putFile(licenseUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // License file uploaded successfully
                            Task<Uri> licenseUriTask = taskSnapshot.getStorage().getDownloadUrl();
                            licenseUriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri licenseDownloadUri) {
                                    // Upload RC file
                                    StorageReference rcRef = storageReference.child("rcs").child(getFileName(rcUri));
                                    rcRef.putFile(rcUri)
                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    // RC file uploaded successfully
                                                    Task<Uri> rcUriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                    rcUriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri rcDownloadUri) {
                                                            // Upload insurance file
                                                            StorageReference insuranceRef = storageReference.child("insurances").child(getFileName(insuranceUri));
                                                            insuranceRef.putFile(insuranceUri)
                                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                        @Override
                                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                            // Insurance file uploaded successfully
                                                                            Task<Uri> insuranceUriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                            insuranceUriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                @Override
                                                                                public void onSuccess(Uri insuranceDownloadUri) {
                                                                                    // All files uploaded successfully, save details to Firestore
                                                                                    UploadPDF uploadPDF = new UploadPDF(
                                                                                            getFileName(licenseUri), licenseDownloadUri.toString(),
                                                                                            getFileName(rcUri), rcDownloadUri.toString(),
                                                                                            getFileName(insuranceUri), insuranceDownloadUri.toString()
                                                                                    );

                                                                                    String documentId = MainActivity.vehNum.getText().toString();
                                                                                    firestore.collection("VehicleFileStorage")
                                                                                            .document(documentId)
                                                                                            .set(uploadPDF)
                                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                @Override
                                                                                                public void onSuccess(Void aVoid) {
                                                                                                    progressDialog.dismiss();
                                                                                                    Toast.makeText(Activity3.this, "Files uploaded successfully!", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            })
                                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                                @Override
                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                    progressDialog.dismiss();
                                                                                                    Toast.makeText(Activity3.this, "Failed to upload files.", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            });
                                                                                }
                                                                            });
                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            progressDialog.dismiss();
                                                                            Toast.makeText(Activity3.this, "Failed to upload insurance file.", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }
                                                    });
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(Activity3.this, "Failed to upload RC file.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Activity3.this, "Failed to upload license file.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(Activity3.this, "Please select all files.", Toast.LENGTH_SHORT).show();
        }
    }
}
