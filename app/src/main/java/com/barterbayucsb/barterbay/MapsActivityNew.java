package com.barterbayucsb.barterbay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivityNew extends FragmentActivity implements OnMapReadyCallback {
    Activity thisActivity = this;


    private GoogleMap mMap;
    private ArrayList<Offer> LocalOffers;
    public static ArrayList<Marker> markerArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_new);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
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








        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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

        LatLng UCSB = new LatLng(34.4140, -119.8489);

        mMap = googleMap;



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng ll)
            {
                showInfoWindows();  //Prevents the info windows going away when the map is pressed
            }
        });


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker m) {
                Intent intent = new Intent(MapsActivityNew.this, ViewPost.class);
                startActivity(intent);
                DispLocalOfferActivity.currentOffer = (Offer) m.getTag();
            }
        });

        LocalOffers = DispLocalOfferActivity.getOfferArrayList();
        MarkerOptions mo = new MarkerOptions();
        Marker m;
        for(Offer o: LocalOffers)
        {
            if(!o.getLocation().equals(UCSB)) { //debug offers will have UCSB as their lat long
                String snip = o.getDescription();
                if(snip.length()>=25)
                    snip = snip.substring(0,23) + "...";
                mo.position(o.getLocation()).title(o.getName()).snippet(snip);
                m = mMap.addMarker(mo);
                //m.showInfoWindow();
                m.setTag(o);
                markerArrayList.add(m);

            }
        }
        //mMap.addMarker(new MarkerOptions().position(UCSB).title("UCSB"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(l.getLatitude(),l.getLongitude()), 12.0f));
        if(SettingsActivity.Preferences.getDISTANCE()!=SettingsActivity.DISTANCE_HUGE)
            mMap.addCircle(new CircleOptions().center(
                new LatLng(l.getLatitude(),l.getLongitude()))
                .radius((float)10*(100+SettingsActivity.Preferences.getDISTANCEfloat()))
                .strokeColor(Color.argb(0xFF,0x21,0x07,0x6d))
                .fillColor(Color.argb(0x22,0x21,0x07,0x6d)));   //adds a circle that is radius 10000 (units?) with border color that is the accent color and fill color that is a translucent accent color
    }

    public static void showInfoWindows() //without this the info windows go away every time the view post is dismissed
    {
        for (Marker m:markerArrayList
             ) {//m.showInfoWindow();

        }
    }





}
