package com.demo.demo;


import java.io.Serializable;

public class DataModel implements Serializable{


    String prescription_date;
    String prescription;


    public DataModel(String prescription_date, String prescription) {
        this.prescription_date = prescription_date;
        this.prescription = prescription;

    }


    public String getPrescription_date() {
        return prescription_date;
    }


    public String getPrescription() {
        return prescription;
    }


}