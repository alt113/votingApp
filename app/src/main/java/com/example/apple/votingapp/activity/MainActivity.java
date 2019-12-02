package com.example.apple.votingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.apple.votingapp.R;
import com.example.apple.votingapp.fragment.PollSessionFragment;
import com.example.apple.votingapp.fragment.SettingsFragment;
import com.example.apple.votingapp.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    protected Button buttonSettings;
    protected Button buttonStartPollSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        buttonSettings = findViewById(R.id.button_settings);
        buttonStartPollSession = findViewById(R.id.button_start_poll_session);
        buttonSettings.setOnClickListener(this);
        buttonStartPollSession.setOnClickListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
//        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_settings:
                if (getSupportFragmentManager().findFragmentByTag(Constants.FRAGMENT_SETTINGS) == null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .add(R.id.main_activity_container, new SettingsFragment(), Constants.FRAGMENT_SETTINGS).addToBackStack(Constants.FRAGMENT_SETTINGS).commit();
                }
                break;
            case R.id.button_start_poll_session:
                if (getSupportFragmentManager().findFragmentByTag(Constants.FRAGMENT_POLL_SESSION) == null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .add(R.id.main_activity_container, new PollSessionFragment(), Constants.FRAGMENT_POLL_SESSION).addToBackStack(Constants.FRAGMENT_POLL_SESSION).commit();
                }
                break;
        }
    }
}
