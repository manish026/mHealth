package com.demo.demo;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


class WebService {

    static Context context;
     static void Login(final String uid, final String password, final WebServiceResponse webServiceResponse){




        RequestQueue queue = Volley.newRequestQueue(context);
        String url = Utilty.baseURL+"?"+JSONKeys.patient_id+"="+uid;

        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Webservice", "onResponse: "+response );
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if(jsonArray.length() > 0) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(0);


                                if (jsonObject.get(JSONKeys.patient_pass).equals(password)) {
                                    webServiceResponse.onSucess(jsonObject);
                                    Utilty.id = uid;
                                } else {
                                    webServiceResponse.onFailure("Password not matched");
                                }
                            }else{
                                webServiceResponse.onFailure("User not registered");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error == null || error.networkResponse == null) {
                    return;
                }

                String body = null;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // exception
                }
                webServiceResponse.onFailure(body);

            }
        });

        queue.add(stringRequest);

    }

     static void Register(JSONObject patient, final WebServiceResponse webServiceResponse){


        RequestQueue queue = Volley.newRequestQueue(context);
        String url = Utilty.baseURL+"?"+JSONKeys.add_patient+"="+patient;


        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Webservice", "onResponse: "+response );
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean(JSONKeys.status)){
                                Utilty.showToast(context,jsonObject.getString(JSONKeys.message));
                                webServiceResponse.onSucess(null);
                            }else{
                                webServiceResponse.onFailure(jsonObject.getString(JSONKeys.message));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    static void showPrescription(final Context context, String patient_id){
        Utilty.showDialog(context);
        String url = Utilty.baseURL+"?prescription&"+JSONKeys.patient_id+"="+patient_id;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Utilty.hideDialog();
                    String s = new String(responseBody,"UTF-8");
                    Log.e("WS", "onSuccess: "+s );
                    JSONArray jsonArray = new JSONArray(s);
                    ArrayList<DataModel> arrayList = new ArrayList<>();

                    if(jsonArray.length() == 0){

                        Utilty.showToast(context,"No prescriptions found");
                        return;

                    }

                    for(int i=0 ; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        DataModel dataModel = new DataModel(jsonObject.getString("prescription_date"),jsonObject.getString("prescription"));
                        arrayList.add(dataModel);
                    }
                    Intent intent = new Intent(context,PrescrptionActivity.class);
                    intent.putExtra("array",arrayList);

                    context.startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Utilty.hideDialog();
            }
        });
    }

    static void showUploads(final Context context, String patient_id){
        Utilty.showDialog(context);
        String url = Utilty.baseURL+"?uploads&"+JSONKeys.patient_id+"="+patient_id;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Utilty.hideDialog();
                    String s = new String(responseBody,"UTF-8");
                    Log.e("WS", "onSuccess: "+s );
                    JSONArray jsonArray = new JSONArray(s);
                    ArrayList<ImageModel> arrayList = new ArrayList<>();

                    if(jsonArray.length() == 0){

                        Utilty.showToast(context,"No uploads found");
                        return;

                    }

                    for(int i=0 ; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ImageModel dataModel = new ImageModel(jsonObject.getString("url"));
                        arrayList.add(dataModel);
                    }
                    Intent intent = new Intent(context,ImageActivity.class);
                    intent.putExtra("array",arrayList);
                    context.startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

}
