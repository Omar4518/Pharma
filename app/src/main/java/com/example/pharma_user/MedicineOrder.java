package com.example.pharma_user;

public class MedicineOrder {
    String Medicine;

    public MedicineOrder(String medicine) {
        Medicine = medicine;
    }

    public MedicineOrder() {
    }

    public String getMedicine() {
        return Medicine;
    }

    public void setMedicine(String medicine) {
        Medicine = medicine;
    }
}
