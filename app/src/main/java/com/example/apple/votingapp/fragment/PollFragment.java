package com.example.apple.votingapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.apple.votingapp.R;
import com.example.apple.votingapp.classes.Policy;
import com.example.apple.votingapp.utils.Constants;
import com.example.apple.votingapp.utils.DataBaseHelper;

import java.util.List;

/**
 * PollFragment takes care of populating the fragment
 * page with the actual data values from the Firebase
 * Realtime Database. It also handles data manipulation.
 *
 * @author Rayyan Nasr
 * @author Jihad Eddine Al Khrufan
 * @version %I%, %G%
 * @since 1.0
 */
public class PollFragment extends Fragment implements View.OnClickListener {
    protected TextView pTitle;
    protected TextView pDescription;
    private String key;
    private Policy p;

    protected TextView count1TextView;
    protected TextView count2TextView;
    protected TextView count3TextView;
    protected TextView count4TextView;
    protected TextView option1TextView;
    protected TextView option2TextView;
    protected TextView option3TextView;
    protected TextView option4TextView;
    protected Button option1Button;
    protected Button option2Button;
    protected Button option3Button;
    protected Button option4Button;
    protected ProgressBar result1;
    protected ProgressBar result2;
    protected ProgressBar result3;
    protected ProgressBar result4;
    private Long count1;
    private Long count2;
    private Long count3;
    private Long count4;
    protected Integer previousOption;
    protected TextView totalVotesTextView;
    Integer mode = 1;
    public PreviousOptionsUpdater updater;


    /**
     * New instance poll fragment.
     *
     * @param p              the p
     * @param key            the key
     * @param previousOption the previously selected option
     *                       so that user dos no select multiple options
     * @param mode           1 for session, 2 for results
     * @return the poll fragment
     */
    public static PollFragment newInstance(Policy p, String key, Integer previousOption, Integer mode) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.KEY, key);
        args.putSerializable(Constants.POLICY, p);
        args.putSerializable(Constants.PREVIOUS_OPTION, previousOption);
        args.putSerializable(Constants.MODE, mode);
        PollFragment fragment = new PollFragment();
        fragment.setArguments(args);
        return fragment;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            key = (String) getArguments().getSerializable(Constants.KEY);
            p = (Policy) getArguments().getSerializable(Constants.POLICY);
            previousOption = (Integer) getArguments().getSerializable(Constants.PREVIOUS_OPTION);
            mode = (Integer) getArguments().getSerializable(Constants.MODE);
        }
    }

    /**
     * Set up interface to handle user options
     *
     * @param activity interface
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        updater = (PreviousOptionsUpdater) activity;
    }

    /**
     * After the onCreate() is called (in the Fragment), the Fragment's
     * onCreateView() is called. You can assign your View variables and do
     * any graphical initialisations. You are expected to return a View from
     * this method, and this is the main UI view, but if your Fragment does not
     * use any layouts or graphics, you can return null (happens by default if
     * you don't override).
     *
     * @param inflater           instantiates a layout XML file into its corresponding View objects.
     * @param container          a special view that can contain other views (called children)
     * @param savedInstanceState a saved instance of the application
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poll_holder, container, false);
    }

    /**
     * A make sure that view is fully created.
     *
     * @param view               a view of the application
     * @param savedInstanceState a saved instance of the application
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pTitle = view.findViewById(R.id.poll_id);
        pDescription = view.findViewById(R.id.poll_question);
        count1TextView = view.findViewById(R.id.count_1);
        count2TextView = view.findViewById(R.id.count_2);
        count3TextView = view.findViewById(R.id.count_3);
        count4TextView = view.findViewById(R.id.count_4);
        option1Button = view.findViewById(R.id.option1);
        option2Button = view.findViewById(R.id.option2);
        option3Button = view.findViewById(R.id.option3);
        option4Button = view.findViewById(R.id.option4);

        checkPreviouslySelectedButton();

        option1TextView = view.findViewById(R.id.option1_text);
        option2TextView = view.findViewById(R.id.option2_text);
        option3TextView = view.findViewById(R.id.option3_text);
        option4TextView = view.findViewById(R.id.option4_text);

        result1 = view.findViewById(R.id.result1);
        result2 = view.findViewById(R.id.result2);
        result3 = view.findViewById(R.id.result3);
        result4 = view.findViewById(R.id.result4);
        totalVotesTextView = view.findViewById(R.id.total_votes_count);

        pTitle.setText(p.getTitle());
        pDescription.setText(p.getDescription());
        option1Button.setText(p.getOption1());
        option2Button.setText(p.getOption2());
        option3Button.setText(p.getOption3());
        option4Button.setText(p.getOption4());


        option1TextView.setText(p.getOption1());
        option2TextView.setText(p.getOption2());
        option3TextView.setText(p.getOption3());
        option4TextView.setText(p.getOption4());

        handleUnusedOptions();

        count1 = p.getCount1() == -999 ? 0 : p.getCount1();
        count2 = p.getCount2() == -999 ? 0 : p.getCount2();
        count3 = p.getCount3() == -999 ? 0 : p.getCount3();
        count4 = p.getCount4() == -999 ? 0 : p.getCount4();

        count1TextView.setText(String.valueOf(count1));
        count2TextView.setText(String.valueOf(count2));
        count3TextView.setText(String.valueOf(count3));
        count4TextView.setText(String.valueOf(count4));

        long totalVotes = count1 + count2 + count3 + count4;

        result1.setProgress(Math.toIntExact(count1));
        result1.setMax(Math.toIntExact(totalVotes));
        result1.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar_background));
        result2.setProgress(Math.toIntExact(count2));
        result2.setMax(Math.toIntExact(totalVotes));
        result2.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar_background));
        result3.setProgress(Math.toIntExact(count3));
        result3.setMax(Math.toIntExact(totalVotes));
        result3.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar_background));
        result4.setProgress(Math.toIntExact(count4));
        result4.setMax(Math.toIntExact(totalVotes));
        result4.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar_background));

        totalVotesTextView.setText(String.valueOf(totalVotes));

        if (mode == 2) {
            view.findViewById(R.id.results_layout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.buttons_layout).setVisibility(View.GONE);
            view.findViewById(R.id.votes_layout).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.results_layout).setVisibility(View.GONE);
            view.findViewById(R.id.buttons_layout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.votes_layout).setVisibility(View.GONE);
        }

        option1Button.setOnClickListener(this);

        option2Button.setOnClickListener(this);

        option3Button.setOnClickListener(this);

        option4Button.setOnClickListener(this);

    }

    private void handleUnusedOptions() {
        if (p.getOption1().equals("-999")) {
            option1Button.setVisibility(View.GONE);
            option1TextView.setVisibility(View.GONE);
            count1TextView.setVisibility(View.GONE);
            result1.setVisibility(View.GONE);
        }
        if (p.getOption2().equals("-999")) {
            option2Button.setVisibility(View.GONE);
            option2TextView.setVisibility(View.GONE);
            count2TextView.setVisibility(View.GONE);
            result2.setVisibility(View.GONE);
        }
        if (p.getOption3().equals("-999")) {
            option3Button.setVisibility(View.GONE);
            option3TextView.setVisibility(View.GONE);
            count3TextView.setVisibility(View.GONE);
            result3.setVisibility(View.GONE);
        }
        if (p.getOption4().equals("-999")) {
            option4Button.setVisibility(View.GONE);
            option4TextView.setVisibility(View.GONE);
            count4TextView.setVisibility(View.GONE);
            result4.setVisibility(View.GONE);
        }
    }

    private void checkPreviouslySelectedButton() {
        if (previousOption != null) {
            switch (previousOption) {
                case 1:
                    option1Button.setAlpha(0.5f);
                    break;
                case 2:
                    option2Button.setAlpha(0.5f);
                    break;
                case 3:
                    option3Button.setAlpha(0.5f);
                    break;
                case 4:
                    option4Button.setAlpha(0.5f);
                    break;
            }
        }
    }

    private void subtractPreviousOptionCount() {
        if (previousOption != null) {
            Log.v("PREVIOUS", previousOption + "");
            switch (previousOption) {
                case 1:
                    count1--;
                    break;
                case 2:
                    count2--;
                    break;
                case 3:
                    count3--;
                    break;
                case 4:
                    count4--;
                    break;
            }
        }
    }

    /**
     * Takes care of manipulating data located in
     * the Firebase Realtime Database.
     *
     * @param view   the view
     * @param policy the policy
     */
    void updateDatabase(View view, Policy policy) {

        new DataBaseHelper().updatePolicy(key, policy, new DataBaseHelper.DataStatus() {

            /**
             * Function handler for when data is loaded.
             *
             * @param p list of policies
             * @param keys list of policy keys
             */
            @Override
            public void DataIsLoaded(List<Policy> p, List<String> keys) {

            }

            /**
             * Function handler for when data is inserted into database.
             */
            @Override
            public void DataIsInserted() {

            }

            /**
             * Function handler for when data is deleted from database.
             */
            @Override
            public void DataIsDeleted() {

            }

            /**
             * Function handler for when data is updated in database.
             */
            @Override
            public void DataIsUpdated() {
                Log.i("Info", "Data Updated Successfully");
            }
        });
    }

    @Override
    public void onClick(View v) {
        subtractPreviousOptionCount();
        switch (v.getId()) {
            case R.id.option1:
                updater.updatePreviousOptions(key, 1);
                count1++;
                break;
            case R.id.option2:
                updater.updatePreviousOptions(key, 2);
                count2++;
                break;
            case R.id.option3:
                updater.updatePreviousOptions(key, 3);
                count3++;
                break;
            case R.id.option4:
                updater.updatePreviousOptions(key, 4);
                count4++;
                break;
        }
        updateDatabase(getView(), new Policy(p.getTitle(), p.getDescription(), count1, count2, count3, count4, p.getOption1(), p.getOption2(), p.getOption3(), p.getOption4()));

    }

    public interface PreviousOptionsUpdater {
        void updatePreviousOptions(String key, int option);
    }
}
