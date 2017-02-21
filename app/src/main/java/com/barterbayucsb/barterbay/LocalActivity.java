package com.barterbayucsb.barterbay;

import android.app.Activity;
import android.content.Intent;
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

/**
 * Created by David on 1/27/2017.
 */

public class LocalActivity extends AppCompatActivity {
    public static Offer currentOffer;
    protected int page = 1;
    protected ArrayList<Offer> LocalOffers = new ArrayList<Offer>();
    protected Activity thisActivity = this;


    protected View thisView;
    protected TextView info_text1, info_text2, info_text3, info_text4, info_text5, info_text6, info_text7;
    protected ImageView image1, image2, image3, image4, image5, image6, image7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.localoffersnew);
        for(int i=0; i<7; i++) {
            LocalOffers.add(new Offer()); //so that we don't try to display offers that don't exist
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingMapButton);
        Button DELETE = (Button) findViewById(R.id.deletebutton);

        thisView = this.getWindow().getDecorView().findViewById(android.R.id.content);
        initializeTextsAndImages();
        super.onCreate(savedInstanceState);
        getDevicePosts(info_text1, thisView);






        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocalActivity.this, MapActivity.class);
                startActivity(intent);
            }


        });
        DELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File _offers = new File("storage/emulated/0/offers");
                File[] allOffers = _offers.listFiles();

                for (File f : allOffers) {
                    f.delete();
                    Snackbar.make(view, "Wiped storage/emulated/0/offers", Snackbar.LENGTH_SHORT).show();

                }
            }
        });
    }




    protected void getDevicePosts(TextView t, View view)
    {
        File _offers = new File("storage/emulated/0/offers");
        File[] allOffers = _offers.listFiles();
        int i = 0;
        for (File f: allOffers) {
            try {

//                LocalOffers.add(LocalOffers.size(), SerializableOffer.readOffer(f));
                LocalOffers.add(i,SerializableOffer.readOffer(f));
                i++;
            }
            catch (IOException e) {
                e.printStackTrace();
                Snackbar.make(view, "Error reading from " + f.getPath(), Snackbar.LENGTH_LONG).show();

            }
            catch (ClassNotFoundException e) {

                e.printStackTrace();
                Snackbar.make(view, "Error reading from " + f.getPath(), Snackbar.LENGTH_SHORT).show();
            }


        }
        //displayPostsOld(t);
        displayPosts();
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

        image1.setImageBitmap(LocalOffers.get(0 + 7*(page-1)).image);
        image2.setImageBitmap(LocalOffers.get(1 + 7*(page-1)).image);
        image3.setImageBitmap(LocalOffers.get(2 + 7*(page-1)).image);
        image4.setImageBitmap(LocalOffers.get(3 + 7*(page-1)).image);
        image5.setImageBitmap(LocalOffers.get(4 + 7*(page-1)).image);
        image6.setImageBitmap(LocalOffers.get(5 + 7*(page-1)).image);
        image7.setImageBitmap(LocalOffers.get(6 + 7*(page-1)).image);

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
                Intent intent = new Intent(LocalActivity.this, ViewPost.class);
                startActivity(intent);
            }
        });

        info_text2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(1 + 7*(page-1));
                Intent intent = new Intent(LocalActivity.this, ViewPost.class);
                startActivity(intent);
            }
        });

        info_text3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(2 + 7*(page-1));
                Intent intent = new Intent(LocalActivity.this, ViewPost.class);
                startActivity(intent);
            }
        });

        info_text4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(3 + 7*(page-1));
                Intent intent = new Intent(LocalActivity.this, ViewPost.class);
                startActivity(intent);
            }
        });

        info_text5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(4 + 7*(page-1));
                Intent intent = new Intent(LocalActivity.this, ViewPost.class);
                startActivity(intent);
            }
        });

        info_text6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(5 + 7*(page-1));
                Intent intent = new Intent(LocalActivity.this, ViewPost.class);
                startActivity(intent);
            }
        });

        info_text7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                currentOffer = LocalOffers.get(6 + 7*(page-1));
                Intent intent = new Intent(LocalActivity.this, ViewPost.class);
                startActivity(intent);
            }
        });


    }



}
