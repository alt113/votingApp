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
import com.example.apple.votingapp.fragment.ResetPasswordFragment;
import com.example.apple.votingapp.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     *  Input boxes for email and password
     */
    private EditText inputEmail, inputPassword;

    /**
     * Firebase authentication Object
     */
    private FirebaseAuth auth;

    /**
     * Progress bar object for data loading
     */
    private ProgressBar progressBar;

    /**
     * Login and Sign up buttons
     */
    protected Button buttonSignUp, buttonLogin;
    /**
     * The Button reset.
     */
    protected View buttonReset;


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

        // set the view now
        setContentView(R.layout.activity_login);

        //Get FireBase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class)); // change back to MainActivity
            finish();
        }

        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        progressBar = findViewById(R.id.progress_bar);
        buttonSignUp = findViewById(R.id.button_signup);
        buttonLogin = findViewById(R.id.button_login);
        buttonReset = findViewById(R.id.button_reset_password);

        //Get FireBase auth instance
        auth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(this);
        buttonReset.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);
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
            case R.id.button_signup:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
            case R.id.button_reset_password:
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_top, R.anim.enter_from_top, R.anim.exit_to_bottom)
                        .add(R.id.login_activity_container, new ResetPasswordFragment()).addToBackStack(Constants.FRAGMENT_RESET_PASSWORD).commit();
                break;
            case R.id.button_login:
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), Constants.EMPTY_EMAIL_INPUT, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), Constants.EMPTY_PASSWORD_INPUT, Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, Constants.AUTH_FAIL, Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                break;
        }
    }
}
