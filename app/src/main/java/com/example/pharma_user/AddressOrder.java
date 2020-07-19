package com.example.pharma_user;

public class AddressOrder {
    String address,buildNo,floorNo,appNo;

    public AddressOrder(String address, String buildNo, String floorNo, String appNo) {
        this.address = address;
        this.buildNo = buildNo;
        this.floorNo = floorNo;
        this.appNo = appNo;
    }

    public AddressOrder() {
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


}
