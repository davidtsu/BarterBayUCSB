package com.barterbayucsb.barterbay;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

//import android.graphics.Bitmap;
//import java.io.FileDescriptor;
//import java.io.IOException;

//import static android.R.attr.data;

/**
 * Created by Daniel on 2/3/2017.
 */
public class UploadPicActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    static int[] newArray = new int[]{0xffffffff, 0x00000000, 0x00000000, 0xffffffff};

    static Bitmap currentBitmap = Bitmap.createBitmap(newArray, 2, 2, Bitmap.Config.ALPHA_8);

    Activity thisActivity = this;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadpic);
        Button uploadButton2 = (Button) findViewById(R.id.uploadButton2);
        Button doneButton = (Button) findViewById(R.id.doneButton);
        final ImageView imageView = (ImageView) findViewById(R.id.imgView);


        uploadButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // modified from https://developer.android.com/training/permissions/requesting.html
                if (ContextCompat.checkSelfPermission(thisActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                            //android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    //} else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(thisActivity, new String[]{READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                    }
                else{
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                    Uri selectedImage = i.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    try {
                        cursor.moveToFirst();
                    } catch (Exception e) {
                    }
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    currentBitmap = BitmapFactory.decodeFile(picturePath);
                    }


                //}
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });

    }
}
