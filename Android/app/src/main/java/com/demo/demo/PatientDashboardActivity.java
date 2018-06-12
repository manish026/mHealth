package com.demo.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PatientDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
        setTitle(getString(R.string.patient_dashboard));
        TextView welcomeTv = findViewById(R.id.welcomeTV);
        welcomeTv.setText("Welcome, "+Utilty.name+"\n What would you like to do today?");
        findViewById(R.id.uploadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LicenseUploadActivity.class));
            }
        });

        findViewById(R.id.alarmButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AlarmActivity .class));
            }
        });

        findViewById(R.id.myuploadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebService.showUploads(PatientDashboardActivity.this,Utilty.id);
            }
        });

        findViewById(R.id.prescriptionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebService.showPrescription(PatientDashboardActivity.this,Utilty.id);
            }
        });
    }
}
