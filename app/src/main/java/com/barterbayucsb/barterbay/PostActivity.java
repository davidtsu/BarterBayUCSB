package com.barterbayucsb.barterbay;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.content.Intent;
import android.widget.NumberPicker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Daniel on 1/27/2017.
 * edited on 2/3
 */
public class PostActivity extends AppCompatActivity {
    NumberPicker np = null;
    public static Offer testOffer = new Offer();
    String[] valueStrings =  {"$1—$10","$11—$25","$26—$50","$51—$100","$101—$250","$251—$500","$501—$1,000","$1,001—$10,000","$10,001+","Priceless"};
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newpost);
        Button uploadButton = (Button) findViewById(R.id.uploadButton);
        Button doneButton = (Button) findViewById(R.id.doneButton);

        final AutoCompleteTextView titleTextView = (AutoCompleteTextView) findViewById(R.id.titleTextView);
        final AutoCompleteTextView descriptionTextView = (AutoCompleteTextView) findViewById(R.id.descriptionTextView);

        np = (NumberPicker) findViewById(R.id.numberPicker);
        NumberPicker.Formatter npFormatter = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                //int temp = value * 10;
                //return ( "$" + (temp-9) + "\u2014" +"$"+ temp);
                return valueStrings[value];
            }
        };
        np.setFormatter(npFormatter);
        np.setMaxValue(9);
        np.setMinValue(0);
        np.setWrapSelectorWheel(true);
        np.setClickable(false);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); //this makes it so the user can't enter a value with the keyboard since that messes up the formatting
        np.setOnClickListener(new View.OnClickListener() {                  //prevents the numberPicker's format from resetting every time it is clicked
            @Override
            public void onClick(View view) {
                return;
            }
        });




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
                String titleTest = titleTextView.getText().toString();
                if(!(titleTest.equals("")||titleTest.equals("Enter title here..."))) {
                    testOffer.setName(titleTest);
                    testOffer.setDescription(descriptionTextView.getText().toString());
                    testOffer.setValue(1);
                    testOffer.image = UploadPicActivity.currentBitmap;
                    testOffer.id = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    try {
                        testOffer.writeOffer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    finish();
                }
                else {

                    Snackbar.make(view, R.string.snackbar_text, Snackbar.LENGTH_SHORT).show();



                }
            }
        });


    }


}
