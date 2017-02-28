package com.barterbayucsb.barterbay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * Created by David on 1/27/2017.
 */

public class DispLocalOfferActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocalOffers = new ArrayList<Offer>();
        setContentView(R.layout.localoffersnew);
        super.onCreate(savedInstanceState);

        for(int i=0; i<8; i++) {

            LocalOffers.add(new Offer()); //so that we don't try to display offers that don't exist

        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingMapButton);
        Button DELETE = (Button) findViewById(R.id.deletebutton);

        thisView = this.getWindow().getDecorView().findViewById(android.R.id.content);
        prevButton = (Button) findViewById(R.id.previousButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        pageNo = (TextView) findViewById(R.id.pageNoTextView);

        initializeTextsAndImages();
        getDevicePosts(info_text1, thisView);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page>=maxPage)
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






        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DispLocalOfferActivity.this, MapsActivityNew.class);
                startActivity(intent);
            }


        });
        DELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*

                File _offers = new File(getExternalStorageDirectory().toString() + "/offers");
                if(_offers.listFiles() == null)
                    return;
                for (File f : _offers.listFiles()) {
                    f.delete();
                    Snackbar.make(view, "Wiped" + getExternalStorageDirectory().toString() + "/offers/", Snackbar.LENGTH_SHORT).show();


                }*/
                String deleteCmd = "rm -rf " + new File(getExternalStorageDirectory().toString()+"/offers");
                Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec(deleteCmd);
                    Snackbar.make(view, "Wiped" + getExternalStorageDirectory().toString() + "/offers/", Snackbar.LENGTH_SHORT).show();
                } catch (IOException e) { }
                for(int i=0; i<8; i++) {
            /*try {
                new SerializableOffer(new Offer()).writeOffer(thisView);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

                    //LocalOffers.add(new Offer()); //so that we don't try to display offers that don't exist

                }

            }
        });
    }




    protected void getDevicePosts(TextView t, View view) {
        File _offers = new File(getExternalStorageDirectory().toString() + "/offers/");
        Snackbar.make(view, "got offers from" + getExternalStorageDirectory().toString() + "/offers/", Snackbar.LENGTH_SHORT).show();

        if (!_offers.isDirectory()) {
            displayPosts();
            updateButtons();
            return;

        }
        //File[] allOffers = _offers.listFiles();
        if (_offers.listFiles() == null) {
            displayPosts();
            updateButtons();
            return;
        }
        int i = 0;
        for (File f : _offers.listFiles()) {
            try {

//                LocalOffers.add(LocalOffers.size(), SerializableOffer.readOffer(f));
                Offer newOffer = SerializableOffer.readOffer(f);
                if(newOffer.image==null)
                    newOffer.image = Bitmap.createBitmap(newArray, 2, 2, Bitmap.Config.ALPHA_8);
                LocalOffers.add(i,newOffer);

                i++;
            } catch (IOException e) {
                e.printStackTrace();
                Snackbar.make(view, "Error reading from " + f.getPath(), Snackbar.LENGTH_LONG).show();

            } catch (ClassNotFoundException e) {

                e.printStackTrace();
                Snackbar.make(view, "Error reading from " + f.getPath(), Snackbar.LENGTH_SHORT).show();
            }


        }
        //displayPostsOld(t);
        displayPosts();
        updateButtons();

    }
    protected void displayPostsOld(TextView t)
    {
        //t.setText((""));
        for(Offer o:LocalOffers)
        {
            t.setText(t.getText()+o.getName()+"\n");
        }

    }
    protected void displayPosts()
    {

        info_text1.setText(LocalOffers.get(0 + 7*(page-1)).getName());
        info_text2.setText(LocalOffers.get(1 + 7*(page-1)).getName());
        info_text3.setText(LocalOffers.get(2 + 7*(page-1)).getName());
        info_text4.setText(LocalOffers.get(3 + 7*(page-1)).getName());
        info_text5.setText(LocalOffers.get(4 + 7*(page-1)).getName());
        info_text6.setText(LocalOffers.get(5 + 7*(page-1)).getName());
        info_text7.setText(LocalOffers.get(6 + 7*(page-1)).getName());


        image1.setImageBitmap(Bitmap.createScaledBitmap((LocalOffers.get(0 + 7*(page-1)).image),100,100,false));
        image2.setImageBitmap(Bitmap.createScaledBitmap((LocalOffers.get(1 + 7*(page-1)).image),100,100,false));
        image3.setImageBitmap(Bitmap.createScaledBitmap((LocalOffers.get(2 + 7*(page-1)).image),100,100,false));
        image4.setImageBitmap(Bitmap.createScaledBitmap((LocalOffers.get(3 + 7*(page-1)).image),100,100,false));
        image5.setImageBitmap(Bitmap.createScaledBitmap((LocalOffers.get(4 + 7*(page-1)).image),100,100,false));
        image6.setImageBitmap(Bitmap.createScaledBitmap((LocalOffers.get(5 + 7*(page-1)).image),100,100,false));
        image7.setImageBitmap(Bitmap.createScaledBitmap((LocalOffers.get(6 + 7*(page-1)).image),100,100,false));

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

        info_text1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(0 + 7*(page-1));
                Intent intent = new Intent(DispLocalOfferActivity.this, ViewPost.class);
                startActivity(intent);
            }
        });

        info_text2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(1 + 7*(page-1));
                Intent intent = new Intent(DispLocalOfferActivity.this, ViewPost.class);
                startActivity(intent);
            }
        });

        info_text3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(2 + 7*(page-1));
                Intent intent = new Intent(DispLocalOfferActivity.this, ViewPost.class);
                startActivity(intent);
            }
        });

        info_text4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(3 + 7*(page-1));
                Intent intent = new Intent(DispLocalOfferActivity.this, ViewPost.class);
                startActivity(intent);
            }
        });

        info_text5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(4 + 7*(page-1));
                Intent intent = new Intent(DispLocalOfferActivity.this, ViewPost.class);
                startActivity(intent);
            }
        });

        info_text6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(5 + 7*(page-1));
                Intent intent = new Intent(DispLocalOfferActivity.this, ViewPost.class);
                startActivity(intent);
            }
        });

        info_text7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(6 + 7*(page-1));
                Intent intent = new Intent(DispLocalOfferActivity.this, ViewPost.class);
                startActivity(intent);
            }
        });


    }

    protected void updateButtons()
    {
        maxPage = LocalOffers.size()/7;

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






}
