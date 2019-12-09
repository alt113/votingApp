package com.example.apple.votingapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.apple.votingapp.R;
import com.example.apple.votingapp.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * ResetPasswordFragment is only called up whenever a user wishes
 * to reset the password associated with their registered Firebase
 * account.
 *
 * @author      Rayyan Nasr
 * @author      Jihad Eddine Al Khrufan
 * @version     %I%, %G%
 * @since       1.0
 */
public class ResetPasswordFragment extends Fragment implements View.OnClickListener {

    /**
     * Textbox to input email address
     */
    private EditText inputEmail;

    /**
     * Reset and Back buttons
     */
    protected Button buttonReset, buttonBack;

    /**
     * Firebase authentication Object
     */
    private FirebaseAuth auth;

    /**
     * Progress bar object for data loading
     */
    private ProgressBar progressBar;

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
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
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
        inputEmail = view.findViewById(R.id.input_email);
        buttonReset = view.findViewById(R.id.button_reset_password);
        buttonBack = view.findViewById(R.id.button_back);
        progressBar = view.findViewById(R.id.progress_bar);

        auth = FirebaseAuth.getInstance();

        buttonBack.setOnClickListener(this);
        buttonReset.setOnClickListener(this);
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
            case R.id.button_back:
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                }
                break;
            case R.id.button_reset_password:
                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), Constants.ENTER_EMAIL, Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(),
                                        task.isSuccessful() ? Constants.RESET_EMAIL_SENT : Constants.RESET_EMAIL_FAILED,
                                        Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                break;
        }
    }
}
