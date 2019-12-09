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

/**
 * SettingsFragment is responsible for dealing with
 * user related data such as configuring/changing
 * existing email addresses or passwords.
 *
 * @author      Rayyan Nasr
 * @author      Jihad Eddine Al Khrufan
 * @version     %I%, %G%
 * @since       1.0
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    /**
     *  Change email, change password, reset email, and remove user views
     */
    protected View buttonChangeEmail, buttonChangePassword, buttonSendResetEmail, buttonRemoveUser;

    /**
     *  Change email, change password, send email, remove, signout, back buttons
     */
    protected Button
            changeEmail, changePassword, sendEmail, remove, signOut, buttonBack;


    /**
     *  Textviews to enter old and new emails and passwords
     */
    private EditText oldEmail, newEmail, password, newPassword;

    /**
     *  Progress bar object for data loading
     */
    private ProgressBar progressBar;

    /**
     *  Firebase authentication Listener Object
     */
    private FirebaseAuth.AuthStateListener authListener;


    /**
     *  Firebase authentication Object
     */
    private FirebaseAuth auth;

    /**
     *  Firebase User Object
     */
    private FirebaseUser user;

    /**
     *  Expandable Layouts
     */
    private ExpandableLayout expandableLayout0, expandableLayout1, expandableLayout2, expandableLayout3;

    /**
     * After the onCreate() is called (in the Fragment), the Fragment's
     * onCreateView() is called. You can assign your View variables and do
     * any graphical initialisations. You are expected to return a View from
     * this method, and this is the main UI view, but if your Fragment does not
     * use any layouts or graphics, you can return null (happens by default if
     * you don't override).
     *
     * @param inflater instantiates a layout XML file into its corresponding View objects.
     * @param container a special view that can contain other views (called children)
     * @param savedInstanceState  a saved instance of the application
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    /**
     * A make sure that view is fully created.
     *
     * @param view a view of the application
     * @param savedInstanceState  a saved instance of the application
     */
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
        remove.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
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

        /**
         * Different views to set up
         * based on which buttons have
         * been pressed.
         */
        switch (v.getId()) {

            /**
             * Case of password reset
             */
            case R.id.sending_pass_reset_button:
                expandableLayout0.expand();
                expandableLayout1.collapse();
                expandableLayout2.collapse();
                expandableLayout3.collapse();
                break;

            /**
             * Case of email reset
             */
            case R.id.change_email_button:
                expandableLayout0.collapse();
                expandableLayout1.expand();
                expandableLayout2.collapse();
                expandableLayout3.collapse();
                break;

            /**
             * Password reset button clicked
             */
            case R.id.change_password_button:
                expandableLayout0.collapse();
                expandableLayout1.collapse();
                expandableLayout2.expand();
                expandableLayout3.collapse();
                break;

            /**
             * Case of user reset
             */
            case R.id.remove_user_button:
                expandableLayout0.collapse();
                expandableLayout1.collapse();
                expandableLayout2.collapse();
                expandableLayout3.expand();
                break;

            /**
             * Case of changing email
             */
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

            /**
             * Case of changing password
             */
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

            /**
             * Case of sending updated user data
             */
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

            /**
             * Case of removing existing user data
             */
            case R.id.remove:
                progressBar.setVisibility(View.VISIBLE);
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), Constants.ACCOUNT_DELETE_SUCCESS, Toast.LENGTH_SHORT).show();
                                        if (getActivity() != null) {
                                            startActivity(new Intent(getActivity(), SignUpActivity.class));
                                            getActivity().finish();
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
            /**
             * Case of signing out
             */
            case R.id.sign_out:
                signOut();
                break;

            /**
             * Case of back button clicked
             */
            case R.id.button_back:
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                }
                break;
        }
    }

    /**
     *  Method to handle signing the user out.
     */
    public void signOut() {
        auth.signOut();
    }

    /**
     * This is the state in which the app interacts with the user.
     * The app stays in this state until something happens to take focus
     * away from the app. Such as receiving a phone call.
     */
    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
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

}
