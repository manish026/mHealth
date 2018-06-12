package com.demo.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;


public class DoctorActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = getClass().getName();
    AutoCompleteTextView textView;
    TextView presTV;
    String patient_id;
    Button send_prescription,view_prescription,show_uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        setTitle(getString(R.string.patient_dashboard));



        send_prescription = findViewById(R.id.uploadPrescription);
        view_prescription = findViewById(R.id.viewPrescription);
        show_uploads = findViewById(R.id.showUploads);

        presTV = findViewById(R.id.presTV);

        send_prescription.setOnClickListener(this);
        view_prescription.setOnClickListener(this);
        show_uploads.setOnClickListener(this);




        textView = findViewById(R.id.autoCompleteTextView);
        final String[] aadhar = getIntent().getStringArrayExtra("aadhar");
        if(aadhar != null && aadhar.length>0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, aadhar);
            textView.setAdapter(adapter);
        }
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = (String) adapterView.getItemAtPosition(i);
                int pos = Arrays.asList(aadhar).indexOf(selected);
                patient_id = aadhar[pos];
                presTV.setText("");
                Utilty.showToast(getApplicationContext(),patient_id);
            }
        });

        setView();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.uploadPrescription: uploadPres(); break;

            case R.id.viewPrescription: WebService.showPrescription(DoctorActivity.this,patient_id); break;

            case R.id.showUploads: WebService.showUploads(DoctorActivity.this,patient_id);
        }

    }

    void uploadPres(){

        String url = Utilty.baseURL+"?prescription&"+JSONKeys.patient_id+"="+patient_id+"&prescription="+presTV.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String s = new String(responseBody,"UTF-8");
                    Log.e(TAG, "onSuccess: "+s );
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Utilty.showToast(getApplicationContext(),"Prescription sent");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    void setView(){

        if(Utilty.type.equals("doctor")) {
            view_prescription.setVisibility(View.GONE);
            show_uploads.setVisibility(View.GONE);
        }else{
            send_prescription.setVisibility(View.GONE);
            presTV.setVisibility(View.GONE);
        }

    }




}
