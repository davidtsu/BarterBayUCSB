package com.barterbayucsb.barterbay;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Daniel on 2/18/2017.
 */
public class ViewPost extends AppCompatActivity {
    ImageView IV;
    TextView TitleTV;
    TextView DescriptionTV;
    TextView ValueTV;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.postdisplay);

        //initializations
        IV = (ImageView) findViewById(R.id.postImg) ;
        TitleTV = (TextView) findViewById(R.id.titleTextView);
        DescriptionTV = (TextView) findViewById(R.id.descriptionTextView);
        ValueTV = (TextView) findViewById(R.id.ValueTextView);
        Button contactPosterButton = (Button) findViewById(R.id.contactPoster);
        Button doneButton = (Button) findViewById(R.id.doneButton);

        //display info in currentOffer
        IV.setImageBitmap(LocalActivity.currentOffer.image);
        TitleTV.setText(LocalActivity.currentOffer.getName());
        DescriptionTV.setText(LocalActivity.currentOffer.getDescription());
        ValueTV.setText("Estimated Value: "+PostActivity.valueStrings[LocalActivity.currentOffer.getValue()]);

        doneButton.setOnClickListener(new View.OnClickListener() {//end activity, clear currentOffer
            @Override
            public void onClick(View view) {
                LocalActivity.currentOffer = null;
                finish();
            }

        });

        contactPosterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"(123) 456-7890", Snackbar.LENGTH_LONG).show(); //TODO: once user is implemented in Offer make this get the user's phone number (and potentially rewrite it so it can be copied to the clipboard)

            }
        });

    }
}
