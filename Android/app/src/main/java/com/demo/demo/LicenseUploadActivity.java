package com.demo.demo;

        import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.database.Cursor;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.provider.MediaStore;
        import android.support.annotation.NonNull;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.loopj.android.http.AsyncHttpClient;
        import com.loopj.android.http.AsyncHttpResponseHandler;
        import com.loopj.android.http.RequestParams;


        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.io.UnsupportedEncodingException;
        import java.util.UUID;

        import cz.msebera.android.httpclient.Header;

public class LicenseUploadActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = getClass().getName();
    //Declaring views
    private Button buttonChoose;
    private Button buttonUpload;
    private Button buttonCapture;
    private ImageView imageView;

    private File f;

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_upload);


        if(Utilty.type.equals("patient")){
            setTitle("Upload reports");
        }else {
            setTitle(getString(R.string.upload_activity));
        }
        //Requesting storage permission
        requestStoragePermission();

        //Initializing views
        buttonChoose =  findViewById(R.id.chooseButton);
        buttonUpload =  findViewById(R.id.buttonUpload);
        buttonCapture = findViewById(R.id.capture_button);
        imageView =   findViewById(R.id.imageView);
//        editText = (EditText) findViewById(R.id.editTextName);

        //Setting clicklistener
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        buttonCapture.setOnClickListener(this);
    }


    void postImage(File file){
        Utilty.showDialog(LicenseUploadActivity.this);
        RequestParams params = new RequestParams();
        params.put(JSONKeys.patient_id,Utilty.id);
        if(Utilty.type.equals("patient")){
            params.put("type", Utilty.type + "uploads");
        }else {
            params.put("type", Utilty.type + "license");
        }
        try {
            params.put("uploaded_file", file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(getString(R.string.uploadURL), params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Utilty.hideDialog();
                    finish();
                    Utilty.showToast(getApplicationContext(),"Image uploaded successfully");
                    if(!Utilty.type.equals("patient"))
                    startActivity(new Intent(getApplicationContext(),DoctorActivity.class));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Utilty.hideDialog();
                try {
                    Log.e(TAG, "onFailure: "+new String(responseBody,"UTF-8") );
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    private void openCamera(){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         File file = new File(getExternalCacheDir(),
                String.valueOf(System.currentTimeMillis()) + ".jpg");
        filePath = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, filePath);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                f = getFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK ) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                f = new File(filePath.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public File getFile(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return new File(path);
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if (v == buttonUpload) {
            postImage(f);
        }
        if(v == buttonCapture){
            openCamera();

        }
    }


}
