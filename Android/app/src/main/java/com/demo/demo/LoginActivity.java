package com.demo.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class LoginActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();
    EditText uidET,passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.loginTitle);

        JSONKeys.setKeys();

        uidET = findViewById(R.id.uidET);
        passwordET = findViewById(R.id.passwordET);
        WebService.context = getApplicationContext();

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utilty.validateTextFields(uidET,passwordET) && Utilty.validateUid(uidET)) {

                    Utilty.showDialog(LoginActivity.this);
                    WebService.Login(uidET.getText().toString(), passwordET.getText().toString(), new WebServiceResponse() {
                        @Override
                        public void onSucess(JSONObject jsonObject) {
                            try {

                                Utilty.name = jsonObject.getString(JSONKeys.patient_name);
                                Utilty.hideDialog();
                                final Class actvity ;

                                switch (Utilty.type){
                                    case "patient" : actvity = PatientDashboardActivity.class; break;
                                    default: actvity = DoctorActivity.class;
                                }

                                if(!Utilty.type.equals("patient")&&jsonObject.getString(JSONKeys.patient_license).equals("null")){
                                    startActivity(new Intent(getApplicationContext(),LicenseUploadActivity.class));
                                }else {

                                    AsyncHttpClient client = new AsyncHttpClient();
                                    client.get(Utilty.baseURL + "?Aadhar", new AsyncHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                            try {
                                                Intent intent = new Intent(getApplicationContext(), actvity);
                                                String s = new String(responseBody,"UTF-8");
                                                if(s.length()>3) {
                                                    JSONArray jsonArray = new JSONArray(s);
                                                    ArrayList<String> array = new ArrayList<>();
                                                    for(int i = 0 ; i< jsonArray.length();i++){
                                                        JSONObject object = jsonArray.getJSONObject(i);
                                                        if(!object.getString("patient_id").isEmpty())
                                                            array.add(object.getString("patient_id"));
                                                    }
                                                    intent.putExtra("aadhar",array.toArray(new String[array.size()]));
                                                    Log.e(TAG, "onSuccess: "+jsonArray );
                                                }
                                                startActivity(intent);

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                        }
                                    });

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(String message) {
                            Utilty.showToast(getApplicationContext(), message);
                            Utilty.hideDialog();
                        }
                    });
                }
            }
        });

        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getBaseContext(),RegistrationActivity.class);
                startActivity(registerIntent);
            }
        });

    }


}
