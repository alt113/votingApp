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
    public PreviousOptionsUpdater updater;

    public static PollFragment newInstance(Policy p, String key, Integer previousOption) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.KEY, key);
        args.putSerializable(Constants.POLICY, p);
        args.putSerializable(Constants.PREVIOUS_OPTION, previousOption);
        PollFragment fragment = new PollFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        updater = (PreviousOptionsUpdater) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            key = (String) getArguments().getSerializable(Constants.KEY);
            p = (Policy) getArguments().getSerializable(Constants.POLICY);
            previousOption = (Integer) getArguments().getSerializable(Constants.PREVIOUS_OPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poll_holder, container, false);
    }

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
        option1TextView = view.findViewById(R.id.option1_text);
        option2TextView = view.findViewById(R.id.option2_text);
        option3TextView = view.findViewById(R.id.option3_text);
        option4TextView = view.findViewById(R.id.option4_text);
        result1 = view.findViewById(R.id.result1);
        result2 = view.findViewById(R.id.result2);
        result3 = view.findViewById(R.id.result3);
        result4 = view.findViewById(R.id.result4);

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

        count1 = p.getCount1() == -999 ? 0 : p.getCount1();
        count2 = p.getCount2() == -999 ? 0 : p.getCount2();
        count3 = p.getCount3() == -999 ? 0 : p.getCount3();
        count4 = p.getCount4() == -999 ? 0 : p.getCount4();

        count1TextView.setText(String.valueOf(count1));
        count2TextView.setText(String.valueOf(count2));
        count3TextView.setText(String.valueOf(count3));
        count4TextView.setText(String.valueOf(count4));

        result1.setProgress(Math.toIntExact(count1));
        result1.setMax(Math.toIntExact(count1 + count2 + count3 + count4));
        result2.setProgress(Math.toIntExact(count2));
        result2.setMax(Math.toIntExact(count1 + count2 + count3 + count4));
        result3.setProgress(Math.toIntExact(count3));
        result3.setMax(Math.toIntExact(count1 + count2 + count3 + count4));
        result4.setProgress(Math.toIntExact(count4));
        result4.setMax(Math.toIntExact(count1 + count2 + count3 + count4));

        option1Button.setOnClickListener(this);

        option2Button.setOnClickListener(this);

        option3Button.setOnClickListener(this);

        option4Button.setOnClickListener(this);

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

    void clickFunction(View view, Policy policy) {

        new DataBaseHelper().updatePolicy(key, policy, new DataBaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Policy> p, List<String> keys) {

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsDeleted() {

            }

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
        clickFunction(getView(), new Policy(p.getTitle(), p.getDescription(), count1, count2, count3, count4, p.getOption1(), p.getOption2(), p.getOption3(), p.getOption4()));

    }

    public interface PreviousOptionsUpdater {
        void updatePreviousOptions(String key, int option);
    }
}
