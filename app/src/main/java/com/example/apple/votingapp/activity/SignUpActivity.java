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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputEmail, inputPassword, inputRepeatPassword;
    protected Button buttonSignUp;
    protected View buttonSignIn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

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

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                finish();
                break;
            case R.id.button_sign_up:

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
