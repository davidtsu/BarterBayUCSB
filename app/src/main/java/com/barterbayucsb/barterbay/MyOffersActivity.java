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
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.os.Environment.getExternalStorageDirectory;

/**
 * Created by David on 1/27/2017.
 */

/**
 * Created by David on 1/27/2017.
 */

public class MyOffersActivity extends AppCompatActivity {
    public static Offer currentOffer;
    protected int maxPage = 0;
    protected int page = 1;
    public static ArrayList<Offer> LocalOffers;
    protected Activity thisActivity = this;
    static int[] newArray = new int[]{0xffffffff, 0x00000000, 0x00000000, 0xffffffff};

    protected Button prevButton, nextButton;
    protected View thisView;
    protected TextView info_text1, info_text2, info_text3, info_text4, info_text5, info_text6, info_text7, pageNo;
    protected ImageView image1, image2, image3, image4, image5, image6, image7;
    protected CardView card1, card2, card3, card4, card5, card6, card7;
    ArrayList<TextView> info_texts;
    ArrayList<ImageView> images;
    ArrayList<CardView> cards;

    private boolean add_offer_complete_flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocalOffers = new ArrayList<Offer>();
        setContentView(R.layout.localoffersnew);
        super.onCreate(savedInstanceState);
        /*
        for(int i=0; i<8; i++) {

            LocalOffers.add(new Offer()); //so that we don't try to display offers that don't exist

        }*/
        Button DELETE = (Button) findViewById(R.id.deletebutton);

        thisView = this.getWindow().getDecorView().findViewById(android.R.id.content);
        prevButton = (Button) findViewById(R.id.previousButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        pageNo = (TextView) findViewById(R.id.pageNoTextView);

        SettingsActivity.initializePreferences(thisView);//need to make sure settings are loaded

        initializeTextsAndImages();
        getDevicePosts(thisView);
        displayPosts();
        updateButtons();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page >= maxPage)
                    return;
                page ++;
                displayPosts();
                updateButtons();
            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page <=1)
                    return;
                page--;
                displayPosts();
                updateButtons();
            }
        });


        DELETE.setVisibility( View.GONE );
        DELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String deleteCmd = "rm -rf " + new File(getExternalStorageDirectory().toString() + "/offers");
                Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec(deleteCmd);
                    Snackbar.make(view, "Wiped " + getExternalStorageDirectory().toString() + "/offers/", Snackbar.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Snackbar.make(view, "Error wiping " + getExternalStorageDirectory().toString() + "/offers/", Snackbar.LENGTH_SHORT).show();

                }
                getDevicePosts(thisView);
                sortPosts();
                displayPosts();
                updateButtons();


            }
        });
    }




    protected void getDevicePosts(View view) {
        ServerGate gate = new ServerGate();
        LocalOffers = gate.retrieve_all_offers();
        if (LocalOffers == null){
            System.out.print("localoffers null");
            LocalOffers = new ArrayList<Offer>();
        }
        ArrayList<Offer> temp = new ArrayList<Offer>();
        for (Offer offer : LocalOffers){

            if (offer.getUserId().equals(User.CURRENT_USER.getId())){
                temp.add(offer);
                System.out.println("offer:" + offer.toString());
            }
        }
        LocalOffers = temp;
        sortPosts();
        displayPosts();
        updateButtons();

    }

    protected void sortPosts() {
        if(SettingsActivity.Preferences.getFILTER_BY_LOCATION()) //TODO: implement location calculations and sorting. Will need a permission check for location
        {
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
            final Location l;
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
            //final LatLng currentLocation = new LatLng(l.getLatitude(),l.getLongitude());


            Collections.sort(LocalOffers, new Comparator<Offer>() {
                @Override
                public int compare(Offer O1, Offer O2) {
                    Location L1 = new Location("");
                    L1.setLatitude(O1.getLocation().latitude);
                    L1.setLongitude(O1.getLocation().longitude);

                    Location L2 = new Location("");
                    L2.setLatitude(O2.getLocation().latitude);
                    L2.setLongitude(O2.getLocation().longitude);
                    if(O1.id.equals("test id")||O2.id.equals("test id")) return 0;
                    if(SettingsActivity.Preferences.getFILTER_LOW_TO_HIGH())
                        return Float.compare(l.distanceTo(L1),l.distanceTo(L2));
                    return Float.compare(l.distanceTo(L2),l.distanceTo(L1));
                }
            });
        }
        else if(SettingsActivity.Preferences.getFILTER_BY_PRICE())
        {
            Collections.sort(LocalOffers, new Comparator<Offer>() {
                @Override
                public int compare(Offer O1, Offer O2) {
                    if (O1.id.equals("test id") || O2.id.equals("test id")) return 0;

                    if(!SettingsActivity.Preferences.getFILTER_LOW_TO_HIGH()) {

                        return Integer.compare(O1.getValue(), O2.getValue());
                    }
                    else return Integer.compare(O2.getValue(), O1.getValue());
                }
            });


        }
        else //default case. Since the radio buttons are mutually exclusive and the logic is too, we can assume this case will be applicable. Additionally, this allows the function to work properly even if settings haven't been initialized yet.
            Collections.sort(LocalOffers, new Comparator<Offer>() {

                @Override
                public int compare(Offer O1, Offer O2)
                {
                    if(O1.id.equals("test id")||O2.id.equals("test id")) return 0;
                    return TimeFormatter.compareAges(O1, O2);
                }
            });


    }


    protected void displayPosts()
    {
        System.out.println("in display posts");
        if (LocalOffers.size() <= 7 * (page - 1)){
            info_text1.setText("No local offers here \uD83D\uDE1E");
            info_text1.setClickable(false);
            for (int i = 1; i < 7 ; i++) {

                    cards.get(i).setVisibility(View.GONE);
                    info_texts.get(i).setClickable(false);
                    continue;

            }
            return;
        }

        for (int i = 0; i < 7 ; i++) {
            if ( i + 7 * (page - 1) >= LocalOffers.size()){
                cards.get(i).setVisibility(View.GONE);
                info_texts.get(i).setClickable(false);
                continue;
            }
            Offer offer = LocalOffers.get(i + 7 * (page - 1));
            if (!offer.id.equals("test id")) {
                info_texts.get(i).setText(offer.getName());
                images.get(i).setImageBitmap(Bitmap.createScaledBitmap((LocalOffers.get(i + 7 * (page - 1)).image), 100, 100, false));
                cards.get(i).setVisibility(View.VISIBLE);
                info_texts.get(i).setClickable(true);
                cards.get(i).animate();
            } else {
                cards.get(i).setVisibility(View.GONE);
                info_texts.get(i).setClickable(false);
            }

        }

    }


    protected void initializeTextsAndImages() {
        info_text1 = (TextView) findViewById(R.id.info_text1);
        info_text2 = (TextView) findViewById(R.id.info_text2);
        info_text3 = (TextView) findViewById(R.id.info_text3);
        info_text4 = (TextView) findViewById(R.id.info_text4);
        info_text5 = (TextView) findViewById(R.id.info_text5);
        info_text6 = (TextView) findViewById(R.id.info_text6);
        info_text7 = (TextView) findViewById(R.id.info_text7);
        image1 = (ImageView) findViewById(R.id.card_image1);
        image2 = (ImageView) findViewById(R.id.card_image2);
        image3 = (ImageView) findViewById(R.id.card_image3);
        image4 = (ImageView) findViewById(R.id.card_image4);
        image5 = (ImageView) findViewById(R.id.card_image5);
        image6 = (ImageView) findViewById(R.id.card_image6);
        image7 = (ImageView) findViewById(R.id.card_image7);
        card1 = (CardView) findViewById(R.id.card_view1);
        card2 = (CardView) findViewById(R.id.card_view2);
        card3 = (CardView) findViewById(R.id.card_view3);
        card4 = (CardView) findViewById(R.id.card_view4);
        card5 = (CardView) findViewById(R.id.card_view5);
        card6 = (CardView) findViewById(R.id.card_view6);
        card7 = (CardView) findViewById(R.id.card_view7);
        if (info_texts == null) info_texts = new ArrayList<TextView>();
        if (images == null) images = new ArrayList<ImageView>();
        if (cards == null) cards = new ArrayList<CardView>();
        info_texts.add(info_text1);
        info_texts.add(info_text2);
        info_texts.add(info_text3);
        info_texts.add(info_text4);
        info_texts.add(info_text5);
        info_texts.add(info_text6);
        info_texts.add(info_text7);

        images.add(image1);
        images.add(image2);
        images.add(image3);
        images.add(image4);
        images.add(image5);
        images.add(image6);
        images.add(image7);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);
        cards.add(card7);

        info_text1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(0 + 7*(page-1));
                Intent intent = new Intent(MyOffersActivity.this, ViewMyPost.class);
                startActivity(intent);
            }
        });

        info_text2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(1 + 7*(page-1));
                Intent intent = new Intent(MyOffersActivity.this, ViewMyPost.class);
                startActivity(intent);
            }
        });

        info_text3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(2 + 7*(page-1));
                Intent intent = new Intent(MyOffersActivity.this, ViewMyPost.class);
                startActivity(intent);
            }
        });

        info_text4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(3 + 7*(page-1));
                Intent intent = new Intent(MyOffersActivity.this, ViewMyPost.class);
                startActivity(intent);
            }
        });

        info_text5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(4 + 7*(page-1));
                Intent intent = new Intent(MyOffersActivity.this, ViewMyPost.class);
                startActivity(intent);
            }
        });

        info_text6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(5 + 7*(page-1));
                Intent intent = new Intent(MyOffersActivity.this, ViewMyPost.class);
                startActivity(intent);
            }
        });

        info_text7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(6 + 7*(page-1));
                Intent intent = new Intent(MyOffersActivity.this, ViewMyPost.class);
                startActivity(intent);
            }
        });


    }

    protected void updateButtons()
    {
        maxPage = (LocalOffers.size()+6)/7;

        if(page>=maxPage)
        {
            nextButton.setEnabled(false);
            nextButton.setAlpha(.5f);

        }
        else
        {
            nextButton.setEnabled(true);
            nextButton.setAlpha(1f);

        }
        if(page<=1)
        {
            prevButton.setEnabled(false);
            prevButton.setAlpha(.5f);

        }
        else
        {
            prevButton.setEnabled(true);
            prevButton.setAlpha(1f);

        }
        pageNo.setText("Page " + page +" of " + maxPage);
    }


    public static ArrayList<Offer> getOfferArrayList()
    {
        return LocalOffers;
    }



    //allows the settings to be accessed from this activity.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MyOffersActivity.this,SettingsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

//decided not to use these since they can be implemented inline

/*
class dateComparator implements Comparator<Offer>
{
    @Override
    public int compare(Offer O1, Offer O2)
    {
        return TimeFormatter.compareAges(O1, O2);
    }
}
class locationComparator implements Comparator<Offer>
{
    @Override
    public int compare(Offer O1, Offer O2)
    {
        return 1;
    }
}
class priceComparator implements Comparator<Offer>
{
    @Override
    public int compare(Offer O1, Offer O2)
    {
        return 1;
    }
}
*/
