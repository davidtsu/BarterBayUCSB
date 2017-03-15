package com.barterbayucsb.barterbay;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RatingBar;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Daniel on 1/27/2017.
 * edited on 2/3
 */
public class ReviewActivity extends AppCompatActivity {
    static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    NumberPicker np = null;
    //Context thisContext = this;
    Activity thisActivity = this;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newreview);
        Button uploadButton = (Button) findViewById(R.id.uploadButton);
        Button doneButton = (Button) findViewById(R.id.doneButtonReview);
        final RatingBar rating = (RatingBar) findViewById(R.id.ratingBar);

        final AutoCompleteTextView reviewTextView = (AutoCompleteTextView) findViewById(R.id.usernameTextView);

        // modified from https://developer.android.com/training/permissions/requesting.html


        doneButton.setOnClickListener(new View.OnClickListener() {  //handles 3 different cases for clicking the post button:
            //case 1: titleText hasn't been updated by the user - displays a message and refuses to post;
            //case 2: titleText is valid but the user as not given permission to write to external storage - prompts the user to use external storage;
            //case 3: titleText is valid and the user has given permission - writes the offer generated as a file to external storage.



            @Override
            public void onClick(View view){
                String text = reviewTextView.getText().toString();
                if((text.equals(""))) { //case 1


                    Snackbar.make(view,"Please enter a body for your review", Snackbar.LENGTH_SHORT).show();


                }
                else {//cases 2 and 3


                    Review review = new Review(reviewTextView.getText().toString(),text,rating.getNumStars() );



                    //might not need permission in this activity, but it's here anyways
                    if (ContextCompat.checkSelfPermission(thisActivity, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {//case 2
                        //if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                        //android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                        //} else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(thisActivity, new String[]{WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                    } else {//case 3 //TODO: Do anything with the review...
                        try{
                            //newOffer.writeOffer(view);
                            //Snackbar.make(view, "Successfully wrote to" + newOffer.getPath(), Snackbar.LENGTH_SHORT).show();
                            ViewUserActivity.CurrentUser.addToReviews(review);
                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                            finish();
                           // Snackbar.make(view, "Error writing to " + newOffer.getPath(), Snackbar.LENGTH_SHORT).show();

                        }

                        return;
                    }
                }
            }
        });
    }

}
