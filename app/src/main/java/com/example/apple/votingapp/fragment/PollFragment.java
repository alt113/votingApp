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
    private TextView voteCount;
    private Button voteUp;
    private Button voteDown;
    private String key;
    private Policy p;

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
        voteCount = view.findViewById(R.id.vote_count);
        voteUp = view.findViewById(R.id.button_upvote);
        voteDown = view.findViewById(R.id.button_downvote);

        voteUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFunction(getView(), new Policy(pTitle.getText().toString(), pDescription.getText().toString(), Long.parseLong(voteCount.getText().toString()) + 1));
                voteCount.setText(Long.toString(Long.parseLong(voteCount.getText().toString()) + 1));
            }
        });

        voteDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFunction(getView(), new Policy(pTitle.getText().toString(), pDescription.getText().toString(), Long.parseLong(voteCount.getText().toString()) - 1));
                voteCount.setText(Long.toString(Long.parseLong(voteCount.getText().toString()) - 1));
            }
        });

        pTitle.setText(p.getTitle());
        pDescription.setText(p.getDescription());
        voteCount.setText(Long.toString(p.getVote()));
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
