package com.example.apple.votingapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apple.votingapp.R;
import com.example.apple.votingapp.classes.Policy;
import com.example.apple.votingapp.components.PollHolder;
import com.example.apple.votingapp.utils.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class PollSessionFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ViewPager pollsViewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poll_session, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.recyclerview_policies);
        pollsViewPager = view.findViewById(R.id.polls_view_pager);
        new DataBaseHelper().readPolicies(new DataBaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Policy> p, List<String> k) {
//                new PollHolder().setConfig(mRecyclerView, getContext(), p, keys);
                final ArrayList<Policy> policies = new ArrayList<>(p);
                final ArrayList<String> keys = new ArrayList<>(k);
                pollsViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
                    @Override
                    public Fragment getItem(int i) {
                        return PollFragment.newInstance(policies.get(i),keys.get(i));
                    }

                    @Override
                    public int getCount() {
                        return keys.size();
                    }
                });
//                detailsPager.setCurrentItem(currentPosition);
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
