package com.example.apple.votingapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.apple.votingapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Handler mHandler = new Handler();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Get FireBase auth instance
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                if (auth.getCurrentUser() != null) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);

                }
                if (intent == null) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();

            }
        }, 1000);
    }
}
