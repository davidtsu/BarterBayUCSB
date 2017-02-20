package com.barterbayucsb.barterbay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.barterbayucsb.barterbay.R.id.floatingActionButton2;
import static com.barterbayucsb.barterbay.R.id.localText;

/**
 * Created by David on 1/27/2017.
 */

public class LocalActivity extends AppCompatActivity {
    protected ArrayList<Offer> LocalOffers = new ArrayList<Offer>();
    protected Activity thisActivity = this;


    protected View thisView;
    protected TextView localTextBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.localoffers);
        FloatingActionButton fab = (FloatingActionButton) findViewById(floatingActionButton2);

        thisView = this.getWindow().getDecorView().findViewById(android.R.id.content);
        localTextBox = (TextView) findViewById(localText);
        super.onCreate(savedInstanceState);
        getDevicePosts(localTextBox, thisView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocalActivity.this, MapActivity.class);
                startActivity(intent);
            }


        });
    }
    protected void getDevicePosts(TextView t, View view)
    {
        File _offers = new File("storage/emulated/0/offers");
        File[] allOffers = _offers.listFiles();
        for (File f: allOffers) {
            try {
                LocalOffers.add(LocalOffers.size(), SerializableOffer.readOffer(f, thisActivity));
            }
            catch (IOException e) {
                e.printStackTrace();
                Snackbar.make(view, "Error reading from " + f.getPath(), Snackbar.LENGTH_SHORT).show();

            }


        }
        displayPosts(t);
    }
    protected void displayPosts(TextView t)
    {
        //t.setText((""));
        for(Offer o:LocalOffers)
        {
            t.setText(t.getText()+o.getName()+"\n");
        }

    }

}
