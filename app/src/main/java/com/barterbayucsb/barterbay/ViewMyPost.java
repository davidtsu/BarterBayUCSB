package com.barterbayucsb.barterbay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by Daniel on 2/18/2017.
 */
public class ViewMyPost extends AppCompatActivity {
    ImageView IV;
    TextView TitleTV;
    TextView DescriptionTV;
    TextView ValueTV;
    TextView TimeTV;
    TextView DistanceTV;
    protected Activity thisActivity = this;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.postdisplay);

        //initializations
        IV = (ImageView) findViewById(R.id.postImg) ;
        TitleTV = (TextView) findViewById(R.id.usernameTextView);
        DescriptionTV = (TextView) findViewById(R.id.descriptionTextView);
        ValueTV = (TextView) findViewById(R.id.ValueTextView);
        TimeTV = (TextView) findViewById(R.id.TimeTextView);
        DistanceTV = (TextView) findViewById(R.id.DistanceTextView);
        Button contactPosterButton = (Button) findViewById(R.id.contactPoster);
        Button doneButton = (Button) findViewById(R.id.doneButton);

        //uses location, so it needs a permissions check
        while (ContextCompat.checkSelfPermission(thisActivity, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
            //android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

            //} else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(thisActivity, new String[]{ACCESS_FINE_LOCATION}, PostActivity.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);


        }
        //get current location
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        Location L1 = new Location("");

        L1.setLatitude(MyOffersActivity.currentOffer.getLocation().latitude);
        L1.setLongitude(MyOffersActivity.currentOffer.getLocation().longitude);





        //display info in currentOffer
        IV.setImageBitmap(MyOffersActivity.currentOffer.image);
        TitleTV.setText(MyOffersActivity.currentOffer.getName());
        DescriptionTV.setText(MyOffersActivity.currentOffer.getDescription());
        ValueTV.setText("Estimated Value: "+PostActivity.valueStrings[MyOffersActivity.currentOffer.getValue()]+".");
        TimeTV.setText(new TimeFormatter().formattedAge(MyOffersActivity.currentOffer.get_time_stamp()));
        //DistanceTV.setText(new DecimalFormat("#.##").format(l.distanceTo(L1))+" km away.");
        DistanceTV.setText("");
        doneButton.setOnClickListener(new View.OnClickListener() {//end activity, clear currentOffer
            @Override
            public void onClick(View view) {
                MyOffersActivity.currentOffer = null;
                MapsActivityNew.showInfoWindows();
                finish();
            }

        });

        contactPosterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view,"123-456-7890", Snackbar.LENGTH_LONG).show(); //TODO: tell ViewUserActivity what do display...
//                ViewUserActivity.setCurrentUser(ServerGate.retrieve_user_by_id(MyOffersActivity.currentOffer.getUser_id())); TODO: uncomment this after these functions are implemented
                Intent intent = new Intent(ViewMyPost.this, ViewUserActivity.class);
                startActivity(intent);


            }
        });

    }

    @Override
    public boolean onKeyDown(int key, KeyEvent event) {
        if(key == KeyEvent.KEYCODE_BACK)
        {
            MapsActivityNew.showInfoWindows();
            finish();
            return true;
        }
        return false;
    }

}
