package com.barterbayucsb.barterbay;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
/**
 * Created by Daniel on 2/18/2017.
 */
public class ViewPost extends AppCompatActivity {
    int[] newArray = new int[]{0xffffffff,0x00000000,0x00000000,0xffffffff};

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postdisplay);
        ImageView IV = (ImageView) findViewById(R.id.postImg) ;
        Button contactPoster = (Button) findViewById(R.id.contactPoster);
        Button doneButton = (Button) findViewById(R.id.doneButton);
        IV.setImageBitmap(Bitmap.createBitmap(newArray, 2,2,Bitmap.Config.ALPHA_8));
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });
        contactPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFrag = new DialogFragment();
                newFrag.show(getSupportFragmentManager(), "(123) 456-7890");

            }
        });

    }
}
