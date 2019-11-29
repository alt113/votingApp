package com.example.apple.votingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.apple.votingapp.R;
import com.example.apple.votingapp.activity.LoginActivity;
import com.example.apple.votingapp.activity.SignUpActivity;
import com.example.apple.votingapp.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.cachapa.expandablelayout.ExpandableLayout;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    protected View buttonChangeEmail, buttonChangePassword, buttonSendResetEmail, buttonRemoveUser;
    protected Button
            changeEmail, changePassword, sendEmail, remove, signOut, buttonBack;
    private EditText oldEmail, newEmail, password, newPassword;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private ExpandableLayout expandableLayout0, expandableLayout1, expandableLayout2, expandableLayout3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    if (getActivity() != null) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                    }
                }
            }
        };

        buttonChangeEmail = view.findViewById(R.id.change_email_button);
        buttonChangePassword = view.findViewById(R.id.change_password_button);
        buttonSendResetEmail = view.findViewById(R.id.sending_pass_reset_button);
        buttonRemoveUser = view.findViewById(R.id.remove_user_button);
        buttonBack = view.findViewById(R.id.button_back);
        changeEmail = view.findViewById(R.id.changeEmail);
        changePassword = view.findViewById(R.id.changePass);
        sendEmail = view.findViewById(R.id.send);
        remove = view.findViewById(R.id.remove);
        signOut = view.findViewById(R.id.sign_out);

        oldEmail = view.findViewById(R.id.old_email);
        newEmail = view.findViewById(R.id.new_email);
        password = view.findViewById(R.id.input_password);
        newPassword = view.findViewById(R.id.newPassword);


        expandableLayout0 = view.findViewById(R.id.expandable_layout_0);
        expandableLayout1 = view.findViewById(R.id.expandable_layout_1);
        expandableLayout2 = view.findViewById(R.id.expandable_layout_2);
        expandableLayout3 = view.findViewById(R.id.expandable_layout_3);


        progressBar = view.findViewById(R.id.progress_bar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        buttonChangeEmail.setOnClickListener(this);
        changeEmail.setOnClickListener(this);
        buttonChangePassword.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        buttonSendResetEmail.setOnClickListener(this);
        sendEmail.setOnClickListener(this);
        buttonRemoveUser.setOnClickListener(this);
        signOut.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.sending_pass_reset_button:
                expandableLayout0.expand();
                expandableLayout1.collapse();
                expandableLayout2.collapse();
                expandableLayout3.collapse();
                break;

            case R.id.change_email_button:
                expandableLayout0.collapse();
                expandableLayout1.expand();
                expandableLayout2.collapse();
                expandableLayout3.collapse();
                break;

            case R.id.change_password_button:
                expandableLayout0.collapse();
                expandableLayout1.collapse();
                expandableLayout2.expand();
                expandableLayout3.collapse();
                break;

            case R.id.remove_user_button:
                expandableLayout0.collapse();
                expandableLayout1.collapse();
                expandableLayout2.collapse();
                expandableLayout3.expand();
                break;

            case R.id.changeEmail:
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newEmail.getText().toString().trim().equals("")) {
                    user.updateEmail(newEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                        signOut();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(getContext(), "Failed to update email!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else if (newEmail.getText().toString().trim().equals("")) {
                    newEmail.setError("Enter email");
                    progressBar.setVisibility(View.GONE);
                }
                break;

            case R.id.changePass:
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newPassword.getText().toString().trim().equals("")) {
                    if (newPassword.getText().toString().trim().length() < 6) {
                        newPassword.setError(Constants.PASSWORD_TOO_SHORT);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(newPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), Constants.PASSWORD_UPDATE_SUCCESS, Toast.LENGTH_SHORT).show();
                                            signOut();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(getContext(), Constants.PASSWORD_UPDATE_FAIL, Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                } else if (newPassword.getText().toString().trim().equals("")) {
                    newPassword.setError(Constants.ENTER_PASSWORD);
                    progressBar.setVisibility(View.GONE);
                }
                break;

            case R.id.send:
                progressBar.setVisibility(View.VISIBLE);
                if (!oldEmail.getText().toString().trim().equals("")) {
                    auth.sendPasswordResetEmail(oldEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), Constants.RESET_EMAIL_SENT, Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(getContext(), Constants.RESET_EMAIL_FAILED, Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                } else {
                    oldEmail.setError(Constants.ENTER_EMAIL);
                    progressBar.setVisibility(View.GONE);
                }
                break;

            case R.id.remove:
                progressBar.setVisibility(View.VISIBLE);
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), Constants.ACCOUNT_DELETE_SUCCESS, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getContext(), SignUpActivity.class));
                                        if (getActivity() != null) {
                                            getActivity().getSupportFragmentManager().popBackStackImmediate();
                                        }
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(getContext(), Constants.ACCOUNT_DELETE_FAIL, Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
                break;
            case R.id.sign_out:
                signOut();
                break;
            case R.id.button_back:
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                }
                break;
        }
    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }


    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
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

}
