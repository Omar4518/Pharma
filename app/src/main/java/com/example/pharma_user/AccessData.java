package com.example.pharma_user;

public class AccessData {
    String address,buildNo,floorNo,appNo,medicine;

    public AccessData(String address, String buildNo, String floorNo, String appNo, String medicine) {
        this.address = address;
        this.buildNo = buildNo;
        this.floorNo = floorNo;
        this.appNo = appNo;
        this.medicine = medicine;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuildNo() {
        return buildNo;
    }

    public void setBuildNo(String buildNo) {
        this.buildNo = buildNo;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public AccessData() {

    }
}
