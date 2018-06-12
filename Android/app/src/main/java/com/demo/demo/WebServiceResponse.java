package com.demo.demo;

import org.json.JSONObject;

/**
 * Created by Manish on 18-03-2018.
 */

public interface WebServiceResponse {

    void onSucess(JSONObject jsonObject);
    void onFailure(String message);
}
