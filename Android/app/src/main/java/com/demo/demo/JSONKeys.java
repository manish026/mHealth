package com.demo.demo;

/**
 * Created by Manish on 18-03-2018.
 */

 class JSONKeys {


    static void setKeys(){
         switch (Utilty.type){
             case "patient": patient_id = "patient_id";
                 add_patient = "AddPatient";
                 patient_name = "patient_name";
                 patient_pass = "patient_pass";
                 break;

             case "doctor": patient_id = doctor_id;
                add_patient = add_doctor;
                patient_name = doctor_name;
                patient_pass = doctor_pass;
                patient_license = doctor_license;
                break;

             case "pharmacy": patient_id = pharmacy_id;
                 add_patient = add_pharmacy;
                 patient_name = pharmacy_name;
                 patient_pass = pharmacy_pass;
                 patient_license = pharmacy_license;
                 break;
         }
     }

     // Patient keys
     static String patient_name = "patient_name";
     static String patient_license = "patient_id";
    static String patient_id = "patient_id";
     static String patient_pass = "patient_pass";
     static String patient_email = "patient_email";
     static String add_patient = "AddPatient";

     //Doctor keys
     static String doctor_specialisation = "doctor_specialisation";
     static String doctor_license = "doctor_license";
     static String doctor_pass = "doctor_pass";
     static String add_doctor = "AddDoctor";
     static String doctor_id = "doctor_id";
     static String doctor_name = "doctor_name";

    //Pharmacy keys
    static String pharmacy_address = "pharmacy_address";
    static String pharmacy_license = "pharmacy_license";
    static String add_pharmacy = "AddPharmacy";
    static String pharmacy_id = "pharmacy_id";
    static String pharmacy_pass = "pharmacy_pass";
    static String pharmacy_name = "pharmacy_name";

     //Response keys
     static String status = "status";
     static String message = "message";
     static String result = "result";
}
