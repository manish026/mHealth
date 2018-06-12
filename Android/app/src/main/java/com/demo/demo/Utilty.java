package com.demo.demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by manish on 17/03/18.
 */

public class Utilty {

    static String baseURL;
    static String type;
    static String name;
    static String id;
    static ProgressDialog dialog;

    private static SharedPreferences getPref(Context context){
        return context.getSharedPreferences("Demo",Context.MODE_PRIVATE);
    }

    public static void saveToPreference(Context context, String key, String value){

        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static String getFromPreference(Context context,String key){
        return getPref(context).getString(key,null);
    }


    // Use to validate text fields

    public static Boolean validateTextFields(EditText... editTexts){


        for (EditText et: editTexts) {

            if(et.getText().toString().isEmpty() && et.getVisibility() != View.GONE){
                et.setError(et.getHint() + " is Required");
                return false;
            }

        }

        return true;

    }

    // shows Toast

    public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }


    public static Boolean validateEmail(EditText editText){
        if((!TextUtils.isEmpty(editText.getText().toString()) && Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches())){
            return true;
        }else{
            editText.setError("Invalid email address");
            return false;
        }
    }

    public static Boolean validateUid(EditText editText){

        if( editText.getText().toString().length() == 12 ){
            return true;
        }else{
            editText.setError("Invalid Uid");
            return false;
        }

    }

    static String getString(EditText editText){
        return editText.getText().toString();
    }

    static void showDialog(Context context){

         dialog = ProgressDialog.show(context, "",
                "Loading. Please wait...", true);

    }

    static void hideDialog(){

        dialog.dismiss();

    }

}
