package com.example.myapplication;

public class UploadPDF {


    public String licenseName;
    public String licenseUrl;
    public String rcName;
    public String rcUrl;
    public String insuranceName;
    public String insuranceUrl;
    public UploadPDF(){};
    public UploadPDF(String licenseName, String licenseUrl, String rcName, String rcUrl, String insuranceName, String insuranceUrl) {
        this.licenseName = licenseName;
        this.licenseUrl = licenseUrl;
        this.rcName = rcName;
        this.rcUrl = rcUrl;
        this.insuranceName = insuranceName;
        this.insuranceUrl = insuranceUrl;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getRcName() {
        return rcName;
    }

    public void setRcName(String rcName) {
        this.rcName = rcName;
    }

    public String getRcUrl() {
        return rcUrl;
    }

    public void setRcUrl(String rcUrl) {
        this.rcUrl = rcUrl;
    }

    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public String getInsuranceUrl() {
        return insuranceUrl;
    }

    public void setInsuranceUrl(String insuranceUrl) {
        this.insuranceUrl = insuranceUrl;
    }
}
