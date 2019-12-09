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

    public static PollFragment newInstance(Policy p, String key) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.KEY, key);
        args.putSerializable(Constants.POLICY, p);
        PollFragment fragment = new PollFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            key = (String) getArguments().getSerializable(Constants.KEY);
            p = (Policy) getArguments().getSerializable(Constants.POLICY);
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

        option1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFunction(getView(), new Policy(p.getTitle(), p.getDescription().toString(), count1 + 1, count2, count3, count4, p.getOption1(), p.getOption2(), p.getOption3(), p.getOption4()));
//                count1TextView.setText(Long.toString(Long.parseLong(count1TextView.getText().toString()) + 1));
            }
        });

        option2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFunction(getView(), new Policy(p.getTitle(), p.getDescription().toString(), count1, count2 + 1, count3, count4, p.getOption1(), p.getOption2(), p.getOption3(), p.getOption4()));
//                count1TextView.setText(Long.toString(Long.parseLong(count1TextView.getText().toString()) - 1));
            }
        });

        option3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFunction(getView(), new Policy(p.getTitle(), p.getDescription().toString(), count1, count2, count3 + 1, count4, p.getOption1(), p.getOption2(), p.getOption3(), p.getOption4()));
//                count1TextView.setText(Long.toString(Long.parseLong(count1TextView.getText().toString()) - 1));
            }
        });

        option4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFunction(getView(), new Policy(p.getTitle(), p.getDescription().toString(), count1, count2, count3, count4 + 1, p.getOption1(), p.getOption2(), p.getOption3(), p.getOption4()));
//                count1TextView.setText(Long.toString(Long.parseLong(count1TextView.getText().toString()) - 1));
            }
        });

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
}
