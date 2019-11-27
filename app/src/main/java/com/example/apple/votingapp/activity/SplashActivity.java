package com.example.apple.votingapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.apple.votingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends Activity {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Handler mHandler = new Handler();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Get FireBase auth instance
        auth = FirebaseAuth.getInstance();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                authListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        Intent intent = null;
                        FirebaseUser newUser = firebaseAuth.getCurrentUser();
                        if (auth != null && newUser != null) {
                            intent = new Intent(SplashActivity.this, MainActivity.class);
                        }
                        if (intent == null) {
                            intent = new Intent(SplashActivity.this, LoginActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    }
                };
                auth.addAuthStateListener(authListener);
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }


}
