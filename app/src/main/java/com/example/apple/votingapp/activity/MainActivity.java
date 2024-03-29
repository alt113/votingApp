package com.example.apple.votingapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.apple.votingapp.R;
import com.example.apple.votingapp.fragment.PollFragment;
import com.example.apple.votingapp.fragment.PollSessionFragment;
import com.example.apple.votingapp.fragment.SettingsFragment;
import com.example.apple.votingapp.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * MainAcitivity is the base view for all verified users
 * once they have been authenticated by the Firebase
 * backend. In essence, it is the base point of the
 * entire application.
 *
 * @author Rayyan Nasr
 * @author Jihad Eddine Al Khrufan
 * @version %I%, %G%
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, PollFragment.PreviousOptionsUpdater {

    /**
     * Firebase authentication Listener Object
     */
    private FirebaseAuth.AuthStateListener authListener;

    /**
     * Firebase authentication object
     */
    private FirebaseAuth auth;
    /**
     * Settings button
     */
    protected Button buttonSettings;
    /**
     * Button to start a poll session.
     */
    protected Button buttonStartPollSession;
    /**
     * Button to view a poll session results.
     */
    protected Button buttonCheckResults;
    private PollSessionFragment pollSessionFragment;
    HashMap<String, Integer> pollSelections = new HashMap<>();

    /**
     * This is the state in which the app interacts with the user. The app stays in
     * this state until something happens to take focus away from the app. Such as
     * receiving a phone call. You should implement onResume() to initialize
     * components that you release during onPause().
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

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
     * @param savedInstanceState a saved instance of the application
     */
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
        try {
            loadSelectionsFromCache();
        } catch (IOException e) {
            e.printStackTrace();
        }

        buttonSettings = findViewById(R.id.button_settings);
        buttonStartPollSession = findViewById(R.id.button_start_poll_session);
        buttonCheckResults = findViewById(R.id.button_check_poll_results);
        buttonSettings.setOnClickListener(this);
        buttonCheckResults.setOnClickListener(this);
        buttonStartPollSession.setOnClickListener(this);

    }

    /**
     * Calls when the activity is getting visible to user. ex:- when activity
     * loading for first time, coming back from another activity, coming
     * foreground from minimized state, in screen rotation, when turn on the
     * display from sleep state etc. If you are using BroadcastReceivers you have to
     * register them here.
     */
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    /**
     * When your activity is no longer visible to the user, it has entered the
     * stopped state. In the onStop() method, the app can release almost all
     * resources that aren’t needed while the user is not using it.
     */
    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    /**
     * When the user clicks a button, the Button object receives an on-click event.
     * <p>
     * If you use this event handler in your code, make sure that you are
     * having that button in your MainActivity. It won’t work if you use this event
     * handler in fragment because onClick attribute only works in Activity.
     *
     * @param v a view of the application
     */
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
                    pollSessionFragment = PollSessionFragment.newInstance(1, pollSelections);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .add(R.id.main_activity_container, pollSessionFragment, Constants.FRAGMENT_POLL_SESSION).addToBackStack(Constants.FRAGMENT_POLL_SESSION).commit();
                }
                break;
            case R.id.button_check_poll_results:
                if (getSupportFragmentManager().findFragmentByTag(Constants.FRAGMENT_POLL_SESSION) == null) {
                    pollSessionFragment = PollSessionFragment.newInstance(2, null);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .add(R.id.main_activity_container, pollSessionFragment, Constants.FRAGMENT_POLL_SESSION).addToBackStack(Constants.FRAGMENT_POLL_SESSION).commit();
                }
                break;
        }
    }

    @Override
    public void updatePreviousOptions(String key, int option) {
        if (pollSessionFragment != null) {
            pollSelections.put(key, option);
            pollSessionFragment.updateOptionsList(key, option);
            try {
                saveSelectionsToCache();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Saved user selections in cache so the user does not make multiple selections
     *
     * @throws IOException
     */
    private void saveSelectionsToCache() throws IOException {
        FileOutputStream stream = null;
        try {
            stream = this.openFileOutput(Constants.PREVIOUS_SELECTIONS, Context.MODE_PRIVATE);
            ObjectOutputStream dout = new ObjectOutputStream(stream);
            dout.writeObject(pollSelections);
            dout.flush();
            stream.getFD().sync();
        } catch (IOException e) { //Do something intelligent
        } finally {

            if (stream != null) {
                stream.close();
            }
        }
    }

    /**
     * loads previously saved user selections
     *
     * @throws IOException
     */
    private void loadSelectionsFromCache() throws IOException {
        FileInputStream stream = null;
        try {
            stream = this.openFileInput(Constants.PREVIOUS_SELECTIONS);
            ObjectInputStream din = new ObjectInputStream(stream);
            try {
                pollSelections = (HashMap<String, Integer>) din.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            stream.getFD().sync();
        } catch (IOException e) { //Do something intelligent
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }
}
