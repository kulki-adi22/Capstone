package com.example.myapplication;

public class PutPDF {
    public String name;
    public String url;
    public String vehicleNumber;

    public PutPDF() {
    }

    public PutPDF(String name, String url, String vehicleNumber) {
        this.name = name;
        this.url = url;
        this.vehicleNumber = vehicleNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
}
