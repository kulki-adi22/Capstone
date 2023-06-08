package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class PoliceVerify extends AppCompatActivity {
    EditText uid;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_verify);
        uid = findViewById(R.id.policeVerify);
        submitBtn = findViewById(R.id.policeVerSubmitBtn);
        ArrayList<String> policeUids = new ArrayList<>();
        policeUids.add("2003KA41002");
        policeUids.add("2001TN41003");
        policeUids.add("2000TN41034");
        policeUids.add("2018KA41110");
        policeUids.add("2004TS41001");
        policeUids.add("2003KA41020");
        policeUids.add("2010AP41012");
        policeUids.add("2002AP41102");
        Intent intent = new Intent(PoliceVerify.this, ViewFiles.class);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputUid = uid.getText().toString().trim();
                if (policeUids.contains(inputUid)) {
                    startActivity(intent);
                } else {
                    showWarningDialog();
                }
            }
        });
    }

    private void showWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning")
                .setMessage("Police ID not registered with the APP. Please contact your Office for further details")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Continue with any desired action or dismiss the dialog
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
}
