package com.barterbayucsb.barterbay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Daniel on 2/23/2017.
 */
public class LoginActivity extends AppCompatActivity {
    @Override


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //TODO: implement LoginActivity, create a new XML layout for LoginActivit
        Button login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView emailView = (TextView) findViewById(R.id.login_email);
                TextView passwordView = (TextView) findViewById(R.id.login_password);
                ServerGate gate = new ServerGate();
                gate.login(emailView.toString(), passwordView.toString());
            }
        });

    }
}
