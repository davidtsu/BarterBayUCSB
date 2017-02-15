package com.barterbayucsb.barterbay;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.barterbayucsb.barterbay.R.id.floatingActionButton2;

/**
 * Created by David on 1/27/2017.
 */

public class LocalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localoffers);
        FloatingActionButton fab = (FloatingActionButton) findViewById(floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocalActivity.this, MapActivity.class);
                startActivity(intent);
            }


        });
    }
}
