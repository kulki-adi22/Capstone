package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.UploadPDF;
import com.example.myapplication.R;

import java.util.List;

public class FileListAdapter extends ArrayAdapter<UploadPDF> {

    private Context context;

    public FileListAdapter(Context context, List<UploadPDF> files) {
        super(context, 0, files);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_file, parent, false);
        }

        UploadPDF currentFile = getItem(position);

        TextView fileNameTextView = listItemView.findViewById(R.id.textFileName);
        fileNameTextView.setText(currentFile.getLicenseName()); // Set the appropriate file name

        Button openButton = listItemView.findViewById(R.id.buttonOpen);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPDF(currentFile.getLicenseUrl()); // Open the PDF file when the button is clicked
            }
        });

        return listItemView;
    }

    private void openPDF(String fileURL) {
        try {
            Uri uri = Uri.parse(fileURL);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, "No application available to view PDF", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Failed to open PDF", Toast.LENGTH_SHORT).show();
        }
    }
}

