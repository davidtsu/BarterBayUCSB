package com.barterbayucsb.barterbay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Daniel on 1/27/2017.
 * edited on 2/3
 */
public class PostActivity extends AppCompatActivity {
    static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    NumberPicker np = null;
    //Context thisContext = this;
    Activity thisActivity = this;
    public static String[] valueStrings =  {"$1—$10","$11—$25","$26—$50","$51—$100","$101—$250","$251—$500","$501—$1,000","$1,001—$10,000","$10,001+","Priceless"};
    static int[] newArray = new int[]{0xffffffff, 0x00000000, 0x00000000, 0xffffffff};
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newpost);
        Button uploadButton = (Button) findViewById(R.id.uploadButton);
        Button doneButton = (Button) findViewById(R.id.doneButton);

        final AutoCompleteTextView titleTextView = (AutoCompleteTextView) findViewById(R.id.reviewTextView);
        final AutoCompleteTextView descriptionTextView = (AutoCompleteTextView) findViewById(R.id.descriptionTextView);

        np = (NumberPicker) findViewById(R.id.numberPicker);
        NumberPicker.Formatter npFormatter = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                //int temp = value * 10;
                //return ( "$" + (temp-9) + "\u2014" +"$"+ temp);
                return valueStrings[value];
            }
        };
        np.setFormatter(npFormatter);
        np.setMaxValue(9);
        np.setMinValue(0);
        np.setWrapSelectorWheel(true);
        np.setClickable(false);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //this makes it so the user can't enter a value with the keyboard since that messes up the formatting
        np.setOnClickListener(new View.OnClickListener() {                  //prevents the numberPicker's format from resetting every time it is clicked
            @Override
            public void onClick(View view) {
                return;
            }
        });
        // modified from https://developer.android.com/training/permissions/requesting.html
        if (ContextCompat.checkSelfPermission(thisActivity, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
            //android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

            //} else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(thisActivity, new String[]{ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);


        }
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //fixes the first element in np not displaying
        Field f = null;
        try {
            f =NumberPicker.class.getDeclaredField("mInputText");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        f.setAccessible(true);
        EditText inputText = null;
        try {
            inputText = (EditText)f.get(np);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        inputText.setFilters(new InputFilter[0]);



        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //directs to UploadPicActivity when the upload button is clicked
                Intent intent = new Intent(PostActivity.this,UploadPicActivity.class);
                startActivity(intent);
            }

        });
        doneButton.setOnClickListener(new View.OnClickListener() {  //handles 3 different cases for clicking the post button:
            //case 1: titleText hasn't been updated by the user - displays a message and refuses to post;
            //case 2: titleText is valid but the user as not given permission to write to external storage - prompts the user to use external storage;
            //case 3: titleText is valid and the user has given permission - writes the offer generated as a file to external storage.



            @Override
            public void onClick(View view){
                String titleTest = titleTextView.getText().toString();
                if((titleTest.equals("")||titleTest.equals("Enter title here..."))) { //case 1


                    Snackbar.make(view,"Please enter a title for your post", Snackbar.LENGTH_SHORT).show();


                }
                else {//cases 2 and 3


                    Offer newOffer = new Offer();



                    while (ContextCompat.checkSelfPermission(thisActivity, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        //if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                        //android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                        //} else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(thisActivity, new String[]{ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);


                    }
                    Location l;
                    lm.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    }, null);
                    l =  lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    newOffer.setLocation(new LatLng(l.getLatitude(),l.getLongitude()));


                    newOffer.setName(titleTest); //no need to get titleTextView's string again
                    newOffer.setDescription(descriptionTextView.getText().toString());
                    newOffer.setValue(np.getValue());
                    //newOffer.setLocation(new LatLng(l.getLatitude(),l.getLongitude()));
                    if(UploadPicActivity.opened == 1)
                        newOffer.image = UploadPicActivity.currentBitmap;

                    else
                        newOffer.image = Bitmap.createBitmap(newArray, 2, 2, Bitmap.Config.ALPHA_8); //prevents the LocalActivity crash

                    newOffer.id = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US).format(new Date()); //prepares our post to be saved to a file named after the current timestamp
                    ServerGate gate = new ServerGate();
                    Offer offer = gate.upload_offer(newOffer);
                    if(offer==null) {
                        Snackbar.make(view, "Error uploading!", Snackbar.LENGTH_SHORT).show();
                        return;
                    }


                    // modified from https://developer.android.com/training/permissions/requesting.html
                    if (ContextCompat.checkSelfPermission(thisActivity, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {//case 2
                        //if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                        //android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                        //} else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(thisActivity, new String[]{WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                    } else {//case 3 //TODO: create a SerializableOffer from the current Offer and write it.
                        try {
                            newOffer.writeOffer(view);
                            Snackbar.make(view, "Successfully wrote to" + newOffer.getPath(), Snackbar.LENGTH_SHORT).show();

                            finish();

                        } catch (IOException e) {
                            e.printStackTrace();
                            Snackbar.make(view, "Error writing to " + newOffer.getPath(), Snackbar.LENGTH_SHORT).show();

                        }

                        return;
                    }
                }
            }
        });
    }

}
