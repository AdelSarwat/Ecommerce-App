package com.example.ecommerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.ecommerceapp.R;


public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    public static int CID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);




        sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);

        getSupportActionBar().hide();


        if (Check()) {
            SendToActivity(HomeActivity.class);

            CID= sharedPref.getInt(getString(R.string.saved_user_ID),0);

        } else {
            SendToActivity(LoginActivity.class);
        }
    }


    private boolean Check() {
        boolean checklogin = sharedPref.getBoolean(getString(R.string.saved_user_boolean), false);
        return checklogin;
    }


    private void SendToActivity(Class c) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, c);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}