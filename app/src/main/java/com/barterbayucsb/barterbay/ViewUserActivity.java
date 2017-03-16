package com.barterbayucsb.barterbay;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    static TextView reviewTV;
    static RatingBar Rb1, Rb2;
    static Button NextButton, PrevButton;
    static int currentReview=0,maxReviews=0;
    protected Activity thisActivity = this;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdisplay);
        ServerGate gate = new ServerGate();
        CurrentUser = gate.retrieve_user_by_id( DispLocalOfferActivity.currentOffer.getUserId() );

        try{System.out.println("Current User=" + CurrentUser.toString());}
        catch(Exception e) {
            e.printStackTrace();
        }
        Rb1 = (RatingBar) findViewById(R.id.ratingBarUser);
        Rb2 = (RatingBar) findViewById(R.id.ratingBarReviewDisplay);

        NextButton = (Button) findViewById(R.id.nextReviewButton);
        PrevButton = (Button) findViewById(R.id.prevReviewButton);

        IV = (ImageView) findViewById(R.id.profilePic) ;
        usernameTV = (TextView) findViewById(R.id.UserNameTextView);
        emailTV = (TextView) findViewById(R.id.EmailTextView);
        reviewTV = (TextView) findViewById(R.id.reviewText);
        Button contactUserButton = (Button) findViewById(R.id.contactUserButton);
        Button doneButton = (Button) findViewById(R.id.doneButtonUser);
        Button reviewUserButton = (Button) findViewById(R.id.ReviewUserButton);

        try{usernameTV.setText(CurrentUser.get_name());}
        catch(Exception e)
        {
            e.printStackTrace();
            usernameTV.setText("Error fetching name!");
        }
        emailTV.setText(CurrentUser.getEmail());
        if (CurrentUser == null ){
            //todo handle the case here
            finish();
        }


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
        Rb1.setAlpha(.8f);
        Rb2.setAlpha(.8f);
        updateButtons();


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
        NextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                currentReview++;
                updateReviews();
            }
        });
        PrevButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                currentReview--;
                updateReviews();
            }
        });
        emailTV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                try {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(emailTV.getText(), emailTV.getText());
                    clipboard.setPrimaryClip(clip);
                    Snackbar.make(view, "Copied email to clipboard", Snackbar.LENGTH_SHORT).show();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    Snackbar.make(view,"Error copying email to clipboard", Snackbar.LENGTH_SHORT).show();

                }

            }
        });


    }
    public static void setCurrentUser(User u)
    {
        CurrentUser = u;
    }

    public static void updateReviews(){

        try{
            maxReviews = CurrentUser.getAllReviews().size()-1;
        }catch (Exception e)
        {
            e.printStackTrace();
            reviewTV.setText("No reviews");
            reviewTV.setEnabled(false);
            reviewTV.setAlpha(.7f);
        }

        updateButtons();

    }
    private static void updateButtons()
    {
        if(currentReview==maxReviews)
        {
            NextButton.setEnabled(false);
            NextButton.setAlpha(.5f);
        }
        else
        {
            NextButton.setEnabled(true);
            NextButton.setAlpha(1f);
        }
        if(currentReview==0)
        {
            PrevButton.setEnabled(false);
            PrevButton.setAlpha(.5f);
        }
        else
        {
            PrevButton.setEnabled(true);
            PrevButton.setAlpha(1f);
        }



        try{
            Rb2.setRating(CurrentUser.getAllReviews().get(currentReview).getRating());
            float sum = 0;
            float reviews = 0;
            for (Review r:CurrentUser.getAllReviews()
                 ) { sum += r.getRating();
                    reviews++;
            }
            Rb1.setRating(sum/reviews);
            reviewTV.setText(CurrentUser.getAllReviews().get(currentReview).getText());

        }
        catch(Exception e)
        {
            e.printStackTrace();
            Rb2.setRating(3);
        }


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
