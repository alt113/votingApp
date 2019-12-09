package com.example.apple.votingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.apple.votingapp.R;
import com.example.apple.votingapp.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

/**
 * LoginActivity is the entry point for previous users to
 * access the application and it's services.
 * From this base activity, a user can either login in
 * with their existing email/password or trouble shoot
 * their account by either:
 * <ul>
 * <li>Resetting their password
 * <li>Signing up for the first time.
 * </ul>
 * <p>
 * All accounts are stored within a Firebase database and never on any
 * local device or file. This allows for data encryption and authenticated
 * user/application interaction.
 * <p>
 *
 * @author      Rayyan Nasr
 * @author      Jihad Eddine Al Khrufan
 * @version     %I%, %G%
 * @since       1.0
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     *  Input fields for email, password, and password-reset
     */
    private EditText inputEmail, inputPassword, inputRepeatPassword;

    /**
     *  Sign up button
     */
    protected Button buttonSignUp;

    /**
     *  Sign in button
     */
    protected View buttonSignIn;

    /**
     *  Progress bar object for data loading
     */
    private ProgressBar progressBar;

    /**
     *  Firebase authentication object
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get FireBase auth instance
        auth = FirebaseAuth.getInstance();

        buttonSignIn = findViewById(R.id.button_sign_in);
        buttonSignUp = findViewById(R.id.button_sign_up);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        inputRepeatPassword = findViewById(R.id.input_repeat_password);
        progressBar = findViewById(R.id.progress_bar);

        buttonSignIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);
    }

    /**
     * This is the state in which the app interacts with the user. The app stays in
     * this state until something happens to take focus away from the app. Such as
     * receiving a phone call. You should implement onResume() to initialize
     * components that you release during onPause().
     */
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    /**
     * When the user clicks a button, the Button object receives an on-click event.
     * <p>
     * If you use this event handler in your code, make sure that you are
     * having that button in your MainActivity. It won’t work if you use this event
     * handler in fragment because onClick attribute only works in Activity.
     *
     * @param  v  a view of the application
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                /**
                 * Firebase defined function with the purpose
                 * of authenticating that the user is registered
                 * in the Firebase server.
                 *
                 * <p>
                 *     If this case is satisfied then user
                 *     attempts to sign in to the application.
                 * </p>
                 */
                finish();
                break;
            case R.id.button_sign_up:
                /**
                 * The user provides sign up information in order
                 * to be granted access to the application. The following
                 * if statements define the password restrictions for singing
                 * up :
                 * <ul>
                 * <li> Length must be greater than 6 characters
                 * </ul>
                 * <p>
                 *     If this case is satisfied then user
                 *     attempts to sign up to the application.
                 * </p>
                 */
                if (TextUtils.isEmpty(inputEmail.getText())) {
                    Toast.makeText(getApplicationContext(), Constants.ENTER_EMAIL, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(inputPassword.getText()) || TextUtils.isEmpty(inputRepeatPassword.getText())) {
                    Toast.makeText(getApplicationContext(), Constants.ENTER_PASSWORD, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (inputPassword.getText().toString().trim().length() < 6) {
                    Toast.makeText(getApplicationContext(), Constants.PASSWORD_TOO_SHORT, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!inputPassword.getText().toString().equals(inputRepeatPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), Constants.PASSWORD_INCOMPATIBLE, Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(inputEmail.getText().toString().trim(), inputPassword.getText().toString().trim())
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, Constants.ACCOUNT_SIGNUP_FAIL + Objects.requireNonNull(task.getException()).getLocalizedMessage(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUpActivity.this, Constants.ACCOUNT_SIGNUP_SUCCESS, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
                break;
        }

    }
}
