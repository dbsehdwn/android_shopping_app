package com.dinosaurfactory.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class WriteReviewActivity extends AppCompatActivity {

    final String TAG = getClass().getSimpleName();

    String mCurrentPhotoPath = "";
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GET_PHOTO = 2;

    Button upload_img_button;
    Button upload_review;
    ImageView upload_img_view;
    EditText edit_review;

    Bitmap upload_img;
    File image;

    int user_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_sub);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_48dp);


        user_num = SaveSharedPreference.getUsernum(WriteReviewActivity.this);

        upload_img_view = (ImageView)findViewById(R.id.upload_img);
        upload_img_button = (Button)findViewById(R.id.uplad_img_button);
        upload_img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] item = {"사진 촬영","앨범에서 선택"};
                AlertDialog.Builder builder = new AlertDialog.Builder(WriteReviewActivity.this);
                builder.setTitle("이미지 업로드")
                        .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                                            && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ) {
                                        Log.d(TAG, "권한 설정 완료");
                                        dispatchTakePictureIntent();
                                    } else {
                                        Log.d(TAG, "권한 설정 요청");
                                        ActivityCompat.requestPermissions(WriteReviewActivity.this,
                                                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                                    }
                                    break;
                                }
                            case 1:
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ) {
                                        Log.d(TAG, "권한 설정 완료");
                                        Intent intent1 = new Intent(Intent.ACTION_PICK);
                                        intent1.setType("image/*");
                                        startActivityForResult(intent1, REQUEST_GET_PHOTO);
                                    } else {
                                        Log.d(TAG, "권한 설정 요청");
                                        ActivityCompat.requestPermissions(WriteReviewActivity.this,
                                                new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                                    }
                                }
                                break;
                        }

                    }
                }).show();
            }
        });

        upload_review = (Button)findViewById(R.id.upload_review);
        upload_review.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(mCurrentPhotoPath != ""){
                    try{
                        File file = getCacheDir();
                        File temp = new File(file,mCurrentPhotoPath);
                        temp.createNewFile();
                        OutputStream out = new FileOutputStream(temp);
                        upload_img.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        FileUploadUtils.send2Server(temp);

                        try{
                            Thread.sleep(1500);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                        temp.delete();

                    } catch(Exception e){
                        e.printStackTrace();
                        Toast.makeText(WriteReviewActivity.this, "등록실패" , Toast.LENGTH_SHORT).show();
                    }
                }

                edit_review = (EditText)findViewById(R.id.edit_review);
                String review = edit_review.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(WriteReviewActivity.this, "리뷰가 등록되었습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(WriteReviewActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }else{
                                Toast.makeText(WriteReviewActivity.this, "에러", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(JSONException e){
                            AlertDialog.Builder builder = new AlertDialog.Builder(WriteReviewActivity.this);
                            builder.setMessage("실패2")
                                    .setNegativeButton("다시시도",null)
                                    .create().show();
                            e.printStackTrace();

                        }
                    }
                };
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                ReviewRequest request = new ReviewRequest(user_num,review,mCurrentPhotoPath,date,responseListener);
                RequestQueue queue = Volley.newRequestQueue(WriteReviewActivity.this);
                queue.add(request);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:

                    if (resultCode == RESULT_OK) {
                        upload_img = MediaStore.Images.Media
                                .getBitmap(getContentResolver(), Uri.fromFile(image));

                    }
                    break;

                case REQUEST_GET_PHOTO:
                    if (resultCode == RESULT_OK) {
                        createImageFile();
                        InputStream in = getContentResolver().openInputStream(intent.getData());
                        upload_img = BitmapFactory.decodeStream(in);
                        mCurrentPhotoPath = getPath(intent.getData());


                    }
                    break;
            }

            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);

            int angle = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            upload_img_view.setImageBitmap(rotateImage(upload_img,angle));
            mCurrentPhotoPath = image.getName();
            upload_img = ((BitmapDrawable)upload_img_view.getDrawable()).getBitmap();
            image.delete();

        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                Log.d(TAG, "onRequestPermissionsResult");
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
                    Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                    dispatchTakePictureIntent();
                    break;
                }
            case 2:
                Log.d(TAG, "onRequestPermissionsResult");
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                    Intent intent1 = new Intent(Intent.ACTION_PICK);
                    intent1.setType("image/*");
                    startActivityForResult(intent1, REQUEST_GET_PHOTO);
                    break;
                }

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        getPackageName(),
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public Bitmap rotateImage(Bitmap source, int angle) {
        Matrix matrix = new Matrix();

        switch(angle) {
            case ExifInterface.ORIENTATION_NORMAL:
                return source;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1,1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1,1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1,1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return source;
        }
        Bitmap bmRotated = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
        source.recycle();
        return bmRotated;
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }
}


