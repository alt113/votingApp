package com.example.apple.votingapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class PollFragment extends Fragment {
    private TextView pTitle;
    private TextView pDescription;
    private String key;
    private Policy p;

    private TextView count1TextView;
    private TextView count2TextView;
    private TextView count3TextView;
    private TextView count4TextView;
    private Button option1Button;
    private Button option2Button;
    private Button option3Button;
    private Button option4Button;

    /**
     * New instance poll fragment.
     *
     * @param p   the p
     * @param key the key
     * @return the poll fragment
     */
    public static PollFragment newInstance(Policy p, String key) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.KEY, key);
        args.putSerializable(Constants.POLICY, p);
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
     * @param  savedInstanceState  a saved instance of the application
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            key = (String) getArguments().getSerializable(Constants.KEY);
            p = (Policy) getArguments().getSerializable(Constants.POLICY);
        }
    }

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
        return inflater.inflate(R.layout.fragment_poll_holder, container, false);
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

        /**
         * Setting the value of the following elements:
         * <ul>
         *     <li>
         *         title
         *     </li>
         *     <li>
         *         description
         *     </li>
         *     <li>
         *         counts 1, 2, 3, 4
         *     </li>
         *     <li>
         *         options 1, 2, 3, 4
         *     </li>
         * </ul>
         */
        pTitle.setText(p.getTitle());
        pDescription.setText(p.getDescription());
        option1Button.setText(p.getOption1());
        option2Button.setText(p.getOption2());
        option3Button.setText(p.getOption3());
        option4Button.setText(p.getOption4());
        count1TextView.setText(String.valueOf(p.getCount1()));
        count2TextView.setText(String.valueOf(p.getCount2()));
        count3TextView.setText(String.valueOf(p.getCount3()));
        count4TextView.setText(String.valueOf(p.getCount4()));

        final Long count1 = Long.parseLong(count1TextView.getText().toString());
        final Long count2 = Long.parseLong(count2TextView.getText().toString());
        final Long count3 = Long.parseLong(count3TextView.getText().toString());
        final Long count4 = Long.parseLong(count4TextView.getText().toString());

        /**
         * Sets the onClick function for the option 1 button to
         * increment option 1's equivalent count value (count 1)
         */
        option1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFunction(getView(), new Policy(p.getTitle(), p.getDescription().toString(), count1 + 1, count2, count3, count4, p.getOption1(), p.getOption2(), p.getOption3(), p.getOption4()));
            }
        });

        /**
         * Sets the onClick function for the option 2 button to
         * increment option 2's equivalent count value (count 2)
         */
        option2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFunction(getView(), new Policy(p.getTitle(), p.getDescription().toString(), count1, count2 + 1, count3, count4, p.getOption1(), p.getOption2(), p.getOption3(), p.getOption4()));
            }
        });

        /**
         * Sets the onClick function for the option 3 button to
         * increment option 3's equivalent count value (count 3)
         */
        option3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFunction(getView(), new Policy(p.getTitle(), p.getDescription().toString(), count1, count2, count3 + 1, count4, p.getOption1(), p.getOption2(), p.getOption3(), p.getOption4()));
            }
        });

        /**
         * Sets the onClick function for the option 4 button to
         * increment option 4's equivalent count value (count 4)
         */
        option4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFunction(getView(), new Policy(p.getTitle(), p.getDescription().toString(), count1, count2, count3, count4 + 1, p.getOption1(), p.getOption2(), p.getOption3(), p.getOption4()));
            }
        });

    }

    /**
     * Takes care of manipulating data located in
     * the Firebase Realtime Database.
     *
     * @param view   the view
     * @param policy the policy
     */
    void clickFunction(View view, Policy policy) {

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
}
