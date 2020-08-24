package com.rizal.pegawaique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        int waktu = 4000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent utama = new Intent(SplashScreenActivity.this, AuthActivity.class);
                startActivity(utama);
                finish();
            }
        }, waktu);
    }
}
