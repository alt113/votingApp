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

/**
 * SplashActivity serves as a switching center for the
 * users once they launch the application. If the user
 * was previously logged into the application then they
 * bypass the sign in/up screen and go straight to the
 * MainActivity. Otherwise they are sent to the sign in
 * page.
 *
 * @author      Rayyan Nasr
 * @author      Jihad Eddine Al Khrufan
 * @version     %I%, %G%
 * @since       1.0
 */
public class SplashActivity extends Activity {

    /**
     *  Firebase authentication Listener Object
     */
    private FirebaseAuth.AuthStateListener authListener;

    /**
     *  Firebase authentication Object
     */
    private FirebaseAuth auth;

    /**
     * If you save the state of the application in a bundle (typically non-
     * persistent, dynamic data in onSaveInstanceState), it can be passed back
     * to onCreate if the activity needs to be recreated (e.g., orientation change).
     * If the orientation changes(i.e rotating your device from landscape mode to
     * portrait and vice versa), the activity is recreated and onCreate() method is
     * called again, so that you don't lose this prior information. If no data was
     * supplied, savedInstanceState is null.
     * <p>
     * For further information visit the official link:
     * http://developer.android.com/guide/topics/resources/runtime-changes.html
     *
     * @param  savedInstanceState  a saved instance of the application
     */
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
                    /**
                     * Based on the authentication state of the user they are presented
                     * with two options:
                     *
                     * <ul>
                     * <li> <p>
                     *     If they are not a new user and their authentication did
                     *     not return as null then proceed to the MainAcitivity.
                     * </p>
                     * <li> <p>
                     *     Else send the user to the LoginActivity
                     * </p>
                     * </ul>
                     */
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

    /**
     * Called before the activity is destroyed. This is the final call that the activity
     * receives. This callback mainly happens because the activity is finishing by
     * calling finish(), or system is temporarily destroying the process to save
     * space or in orientation change. The onDestroy() callback releases all
     * resources that have not yet been released by earlier callbacks such as
     * onStop().
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
