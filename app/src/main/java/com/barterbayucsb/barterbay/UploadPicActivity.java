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
import android.util.Log;
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
    static int opened = 0;

    protected static Bitmap currentBitmap = Bitmap.createBitmap(newArray, 2, 2, Bitmap.Config.ALPHA_8);
    Activity thisActivity = this;

    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback

            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE) {
                Uri selectedImageUri = data.getData();
                String picturePath = getPath(selectedImageUri);
                //String[] filePathColumn = {MediaStore.Images.Media.DATA};
                ImageView imageView = (ImageView) findViewById(R.id.imgView);
                Log.i("picture path:", picturePath);
                currentBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(picturePath),Offer.PICTURE_SIZE,Offer.PICTURE_SIZE,false); //makes it easier to fetch the image at when the post is submitted
                imageView.setImageBitmap(currentBitmap);    //shows the user a preview
                opened = 1; //PostActivity can now see if there is an image
                //TODO: handle the edge case that the image is not locally available (happens with google photos app)
            }
        }
    }
}
