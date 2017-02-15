package com.barterbayucsb.barterbay;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.content.Intent;

/**
 * Created by Daniel on 1/27/2017.
 * edited on 2/3
 */
public class PostActivity extends AppCompatActivity {
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newpost);
        Button uploadButton = (Button) findViewById(R.id.uploadButton);
        Button doneButton = (Button) findViewById(R.id.doneButton);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this,UploadPicActivity.class);
                startActivity(intent);
            }

        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                finish();
            }
        });


        }


}
