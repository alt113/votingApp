package com.example.apple.votingapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.apple.votingapp.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PolicyTimelineActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policytimeline);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_policies);
        new DBHelper().readPolicies(new DBHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Policy> p, List<String> keys) {
                new RecyclerView_Configuration().setConfig(mRecyclerView, PolicyTimelineActivity.this, p, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsDeleted() {

            }

            @Override
            public void DataIsUpdated() {

            }
        });
    }
}
