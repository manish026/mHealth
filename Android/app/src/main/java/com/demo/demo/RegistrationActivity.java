package com.demo.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {

    EditText fnameET,lnameET,uidET,passwordET,cpasswordET,emailET,licenseET,addressET;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();
        setViews();

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateET() && Utilty.validateEmail(emailET) && Utilty.validateUid(uidET) && validatePassword()){

                    JSONObject patient = new JSONObject();
                    try {
                        patient.put(JSONKeys.patient_id,uidET.getText().toString());
                        patient.put(JSONKeys.patient_name,fnameET.getText().toString()+" "+lnameET.getText().toString());
                        patient.put(JSONKeys.patient_pass,passwordET.getText().toString());
                        patient.put(JSONKeys.patient_email,emailET.getText().toString());
                        patient.put(JSONKeys.pharmacy_address,addressET.getText().toString());
                        patient.put(JSONKeys.doctor_specialisation,spinner.getSelectedItem().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Utilty.showDialog(RegistrationActivity.this);
                    WebService.Register(patient, new WebServiceResponse() {
                        @Override
                        public void onSucess(JSONObject jsonObject) {
                            Utilty.hideDialog();
                            finish();

                        }

                        @Override
                        public void onFailure(String message) {
                            Utilty.hideDialog();
                            Utilty.showToast(getApplicationContext(),message);
                        }
                    });

                }
            }
        });


    }

    void initViews(){

        fnameET = findViewById(R.id.fnameET);
        lnameET = findViewById(R.id.lnameET);
        uidET = findViewById(R.id.uidET);
        passwordET = findViewById(R.id.passwordET);
        cpasswordET = findViewById(R.id.cpasswordET);
        emailET = findViewById(R.id.emailET);
        licenseET = findViewById(R.id.licenseNoET);
        addressET = findViewById(R.id.addressET);
        spinner = findViewById(R.id.spinner);

    }

    void setViews(){

        switch (Utilty.type){
            case "doctor" : setDoctorView();    break;
            case "patient" : setPatientView();  break;
            case "pharmacy": setPharmacyView(); break;
        }
    }

    void setDoctorView(){
        addressET.setVisibility(View.GONE);
        licenseET.setVisibility(View.GONE);
    }

    void setPatientView(){

        licenseET.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);
        addressET.setVisibility(View.GONE);

    }

    void setPharmacyView(){
        spinner.setVisibility(View.GONE);
        licenseET.setVisibility(View.GONE);
    }

    Boolean validateET(){

       return Utilty.validateTextFields(fnameET,lnameET,uidET,passwordET,cpasswordET,emailET,licenseET,addressET);

    }

    Boolean validatePassword(){
      if( !Utilty.getString(passwordET).equals(Utilty.getString(cpasswordET))){
          cpasswordET.setError("Password not matched");
          return false;
      }
      return true;
    }
}
