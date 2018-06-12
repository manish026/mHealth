package com.demo.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.doctorButton).setOnClickListener(this);
        findViewById(R.id.pharmacyButton).setOnClickListener(this);
        findViewById(R.id.patientButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String type = null;
        String baseUrl = null;

        switch (view.getId()){

            case R.id.doctorButton :    type = "doctor";
                                        baseUrl = getString(R.string.doctorURL);
                                        break;
            case R.id.pharmacyButton :  type = "pharmacy";
                                        baseUrl = getString(R.string.pharmacyURL);
                                        break;
            case R.id.patientButton :   type = "patient";
                                        baseUrl = getString(R.string.patientURL);
                                        break;
        }

        Utilty.saveToPreference(this,"type",type);
        Utilty.type = type;
        Utilty.baseURL = baseUrl;
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));

    }
}
