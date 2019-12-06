package com.example.apple.votingapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.apple.votingapp.R;
import com.example.apple.votingapp.classes.Policy;
import com.example.apple.votingapp.components.PollIndexAdapter;
import com.example.apple.votingapp.utils.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class PollSessionFragment extends Fragment implements View.OnClickListener {

    private RecyclerView pollsRecyclerView;
    private ViewPager pollsViewPager;
    protected Button buttonBack, buttonFinish;
    private ProgressBar progressBar;
    private int currentPosition = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poll_session, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonBack = view.findViewById(R.id.button_back);
        buttonFinish = view.findViewById(R.id.button_finish);
        progressBar = view.findViewById(R.id.progress_bar);
        pollsRecyclerView = view.findViewById(R.id.polls_recycler_view);
        pollsViewPager = view.findViewById(R.id.polls_view_pager);
        progressBar.setVisibility(View.VISIBLE);
        new DataBaseHelper().readPolicies(new DataBaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Policy> p, List<String> k) {
                final ArrayList<Policy> policies = new ArrayList<>(p);
                final ArrayList<String> keys = new ArrayList<>(k);
                setRecyclerViewAdapter(keys);
                setViewPagerAdapter(keys, policies);
                progressBar.setVisibility(View.GONE);
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
        buttonBack.setOnClickListener(this);
        buttonFinish.setOnClickListener(this);
    }

    private void setViewPagerAdapter(final ArrayList<String> keys, final ArrayList<Policy> policies) {
        pollsViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return PollFragment.newInstance(policies.get(i), keys.get(i));
            }

            @Override
            public int getCount() {
                return keys.size();
            }
        });
        pollsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int pos) {
                currentPosition = pos;
//                if (pollsRecyclerView!=null) {
//                    PollIndexAdapter.PollIndexHolder holder = (PollIndexAdapter.PollIndexHolder) pollsRecyclerView.getChildViewHolder(pollsRecyclerView.getChildAt(i));
//                    if (holder != null && pollsRecyclerView.getAdapter() != null) {
//                        ((PollIndexAdapter) pollsRecyclerView.getAdapter()).setSelected(holder.itemView);
//                    }
//                }
                clickRecyclerView(pos);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        pollsViewPager.setCurrentItem(currentPosition);
    }

    private void clickRecyclerView(int pos) {
        for (int i = 0; i < pollsRecyclerView.getChildCount(); i++) {
            if (i == pos && pollsRecyclerView.getChildViewHolder(pollsRecyclerView.getChildAt(i)) instanceof PollIndexAdapter.PollIndexHolder) {
                PollIndexAdapter.PollIndexHolder holder = (PollIndexAdapter.PollIndexHolder) pollsRecyclerView.getChildViewHolder(pollsRecyclerView.getChildAt(i));
                if (pollsRecyclerView.getAdapter() != null)
                    ((PollIndexAdapter) pollsRecyclerView.getAdapter()).setSelected(holder.itemView);
            }
        }
    }

    private void setRecyclerViewAdapter(ArrayList<String> keys) {
        pollsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        PollIndexAdapter adapter = new PollIndexAdapter(keys);
        adapter.setClickListener(new PollIndexAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                pollsViewPager.setCurrentItem(position);
            }
        });
        pollsRecyclerView.setAdapter(adapter);
        pollsRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                clickRecyclerView(currentPosition);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_finish:
                break;
            case R.id.button_back:
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                }
                break;
        }
    }
}
