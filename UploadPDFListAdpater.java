package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UploadPDFListAdapter extends BaseAdapter {

    private Context context;
    private List<UploadPDF> uploadPDFList;

    public UploadPDFListAdapter(Context context, List<UploadPDF> uploadPDFList) {
        this.context = context;
        this.uploadPDFList = uploadPDFList;
    }

    @Override
    public int getCount() {
        return uploadPDFList.size();
    }

    @Override
    public Object getItem(int position) {
        return uploadPDFList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_upload_pdf, parent, false);
        }

        UploadPDF uploadPDF = uploadPDFList.get(position);

        TextView tvLicenseName = convertView.findViewById(R.id.tvLicenseName);
        TextView tvRcName = convertView.findViewById(R.id.tvRcName);
        TextView tvInsuranceName = convertView.findViewById(R.id.tvInsuranceName);

        tvLicenseName.setText("License: " + uploadPDF.getLicenseName());
        tvRcName.setText("RC: " + uploadPDF.getRcName());
        tvInsuranceName.setText("Insurance: " + uploadPDF.getInsuranceName());

        return convertView;
    }
}
