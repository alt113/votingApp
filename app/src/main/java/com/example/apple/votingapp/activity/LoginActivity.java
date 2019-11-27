package com.example.apple.votingapp.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    protected Button buttonSignUp, buttonLogin, buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the view now
        setContentView(R.layout.activity_login);

        //Get FireBase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_signup:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
            case R.id.button_reset_password:
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom)
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
