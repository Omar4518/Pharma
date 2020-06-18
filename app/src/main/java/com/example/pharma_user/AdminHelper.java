package com.example.pharma_user;

public class AdminHelper {
    String adminName,adminAdd,adminEmail,adminPass,adminPhone;

    public AdminHelper() {
    }

    public AdminHelper(String adminName, String adminAdd, String adminEmail, String adminPass, String adminPhone) {
        this.adminName = adminName;
        this.adminAdd = adminAdd;
        this.adminEmail = adminEmail;
        this.adminPass = adminPass;
        this.adminPhone = adminPhone;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminAdd() {
        return adminAdd;
    }

    public void setAdminAdd(String adminAdd) {
        this.adminAdd = adminAdd;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }
}

