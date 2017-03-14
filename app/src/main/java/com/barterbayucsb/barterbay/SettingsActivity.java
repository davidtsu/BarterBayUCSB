package com.barterbayucsb.barterbay;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.barterbayucsb.barterbay.PostActivity.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE;

/**
 * Created by Daniel on 3/11/2017.
 */

public class SettingsActivity extends AppCompatActivity {
    public static SerializableSettings Preferences = new SerializableSettings();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        View view = this.getWindow().getDecorView().findViewById(android.R.id.content);
        final TextView sliderLabel = (TextView) findViewById(R.id.sliderLabelText);
        final Activity thisActivity = this;
        final SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        final Button doneButton = (Button) findViewById(R.id.SettingsDoneButton);
        final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        final RadioButton ageButton = (RadioButton) findViewById(R.id.ageButton);
        final RadioButton locationButton = (RadioButton) findViewById(R.id.locationButton);
        final RadioButton priceButton = (RadioButton) findViewById(R.id.priceButton);
        final Switch filterSwitch = (Switch) findViewById(R.id.switch2);
        final EditText phoneInput = (EditText) findViewById(R.id.editPhone);
        initializePreferences(view);



        //final TextView sliderLabel = (TextView) findViewById(R.id.sliderLabelText);

        sb.setMax(10001);
        //sb.incrementProgressBy(1);
        sb.setProgress(Preferences.getDISTANCE());

        phoneInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher());


                ageButton.setChecked(Preferences.getFILTER_BY_AGE());
        locationButton.setChecked(Preferences.getFILTER_BY_LOCATION());
        priceButton.setChecked(Preferences.getFILTER_BY_PRICE());
        filterSwitch.setChecked(Preferences.getFILTER_LOW_TO_HIGH());
        phoneInput.setText(Preferences.getPhoneNo());

        float currentValue = ((float) (Preferences.getDISTANCE()-1 )/ 100);
        if(currentValue>1)
            sliderLabel.setText(Float.toString(currentValue) + " km");
        else
            sliderLabel.setText("1.00 km");









        doneButton.setOnClickListener(new View.OnClickListener() {  //handles submitting settings
            @Override
            public void onClick(View view) {
                Preferences.setDISTANCE(sb.getProgress());
                Preferences.setFILTER_BY_AGE(ageButton.isChecked());
                Preferences.setFILTER_BY_LOCATION(locationButton.isChecked());
                Preferences.setFILTER_BY_PRICE(priceButton.isChecked());
                Preferences.setFILTER_LOW_TO_HIGH(filterSwitch.isChecked());
                Preferences.setPhoneNo(phoneInput.getText().toString());




                // modified from https://developer.android.com/training/permissions/requesting.html
                if (ContextCompat.checkSelfPermission(thisActivity, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {//case 2
                    //if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    //android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    //} else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(thisActivity, new String[]{WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                } else {
                    try {
                        Preferences.writeSettings(view);
                        Snackbar.make(view, "Successfully wrote to" + Preferences.getPath(), Snackbar.LENGTH_SHORT).show();

                        finish();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Snackbar.make(view, "Error writing to " + Preferences.getPath(), Snackbar.LENGTH_SHORT).show();

                    }
                }
            }


        });
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){}//might not be necessary

                float currentValue = ((float) (i-1 )/ 100);
                if(currentValue>1)
                    sliderLabel.setText(Float.toString(currentValue) + " km");
                else
                    sliderLabel.setText("1.00 km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    public static void initializePreferences(View view)
    {
        File settingsPath = new File(SerializableSettings.getPath());
        if(settingsPath.exists()) {
            try {
                Preferences = SerializableSettings.readSettings(settingsPath);
            } catch (IOException e) {
                e.printStackTrace();
                Snackbar.make(view, "Error reading from " + SerializableSettings.getPath()+ ". Initialized default settings.", Snackbar.LENGTH_LONG).show();
                SerializableSettings defaultConfig = new SerializableSettings(1000, true, false, false, true, "(123) 456-7890"); //default configs are 10.0km, filter by age, filter low to high
                Preferences = defaultConfig;


            } catch (ClassNotFoundException e) {

                e.printStackTrace();
                Snackbar.make(view, "Error reading from " + SerializableSettings.getPath() +". Initialized default settings.", Snackbar.LENGTH_SHORT).show();
                SerializableSettings defaultConfig = new SerializableSettings(1000, true, false, false, true, "(123) 456-7890"); //default configs are 10.0km, filter by age, filter low to high
                Preferences = defaultConfig;

            }
        }
        else {
            SerializableSettings defaultConfig = new SerializableSettings(1000, true, false, false, true, "(123) 456-7890"); //default configs are 10.0km, filter by age, filter low to high
            Preferences = defaultConfig;
            try {
                defaultConfig.writeSettings(view);
                Snackbar.make(view, "Wrote default settings to " + SerializableSettings.getPath(), Snackbar.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
                Snackbar.make(view, "Error writing default settings to " + SerializableSettings.getPath(), Snackbar.LENGTH_LONG).show();

            }
        }


    }
}