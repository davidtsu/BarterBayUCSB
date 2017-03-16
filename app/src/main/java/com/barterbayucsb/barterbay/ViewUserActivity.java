package com.barterbayucsb.barterbay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Daniel on 2/18/2017.
 */
public class ViewUserActivity extends AppCompatActivity {
    public static User CurrentUser;
    public static Review CurrentReview;
    ImageView IV;
    TextView usernameTV;
    TextView emailTV;
    TextView reviewTV;
    RatingBar Rb1, Rb2;
    protected Activity thisActivity = this;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdisplay);
        ServerGate sg = new ServerGate();
        CurrentUser = sg.retrieve_user_by_id_direct( DispLocalOfferActivity.currentOffer.getUserId() );
        CurrentReview = new Review();

        Rb1 = (RatingBar) findViewById(R.id.ratingBarUser);
        Rb2 = (RatingBar) findViewById(R.id.ratingBarReviewDisplay);
        IV = (ImageView) findViewById(R.id.profilePic) ;
        usernameTV = (TextView) findViewById(R.id.usernameTextView);
        emailTV = (TextView) findViewById(R.id.DistanceTextView);
        reviewTV = (TextView) findViewById(R.id.reviewText);
        Button contactUserButton = (Button) findViewById(R.id.contactUserButton);
        Button doneButton = (Button) findViewById(R.id.doneButtonUser);
        Button reviewUserButton = (Button) findViewById(R.id.ReviewUserButton);

        //display info in currentOffer
        //IV.setImageBitmap(DispLocalOfferActivity.currentOffer.image);
        //TitleTV.setText(DispLocalOfferActivity.currentOffer.getName());
        //DescriptionTV.setText(DispLocalOfferActivity.currentOffer.getDescription());
        //ValueTV.setText("Estimated Value: "+PostActivity.valueStrings[DispLocalOfferActivity.currentOffer.getValue()]+".");
        //TimeTV.setText(new TimeFormatter().formattedAge(DispLocalOfferActivity.currentOffer.get_time_stamp()));
        //DistanceTV.setText(new DecimalFormat("#.##").format(l.distanceTo(L1))+" km away.");
        //DistanceTV.setText("");

       //Rb1.setEnabled(false);
       //Rb2.setEnabled(false);
        Rb1.setAlpha(.5f);
        Rb2.setAlpha(.5f);

        doneButton.setOnClickListener(new View.OnClickListener() {//end activity, clear currentOffer
            @Override
            public void onClick(View view) {
                finish();
            }

        });

        contactUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"123-456-7890", Snackbar.LENGTH_LONG).show(); //TODO: once user is implemented in Offer make this get the user's phone number (and potentially rewrite it so it can be copied to the clipboard)

            }
        });
        reviewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewUserActivity.this,ReviewActivity.class);
                startActivity(intent);

            }

    });

    }
    public static void setCurrentUser(User u)
    {
        CurrentUser = u;
    }
/*
    @Override
    public boolean onKeyDown(int key, KeyEvent event) {
        if(key == KeyEvent.KEYCODE_BACK)
        {
            MapsActivityNew.showInfoWindows();
            finish();
            return true;
        }
        return false;
    }
*/
}
