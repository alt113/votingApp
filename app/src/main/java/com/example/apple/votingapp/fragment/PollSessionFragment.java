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
import java.util.HashMap;
import java.util.List;

/**
 * PollSession Fragment presents the loaded policies
 * in a scrollable RecyclerView.
 *
 * @author Rayyan Nasr
 * @author Jihad Eddine Al Khrufan
 * @version %I%, %G%
 * @since 1.0
 */
public class PollSessionFragment extends Fragment implements View.OnClickListener {

    /**
     * Recycler view to present polls
     */
    private RecyclerView pollsRecyclerView;

    /**
     * To animate screen slides automatically.
     */
    private ViewPager pollsViewPager;

    /**
     * Back and Finish buttons
     */
    protected Button buttonBack, buttonFinish;

    /**
     * Progress bar object for data loading
     */
    private ProgressBar progressBar;

    /**
     * Position value to keep track of paging
     */
    private int currentPosition = 0;

    /**
     * Saves previous option for each poll to user does not select multiple options
     */
    private HashMap<String, Integer> previousSelections = new HashMap<>();


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poll_session, container, false);
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
        buttonBack = view.findViewById(R.id.button_back);
        buttonFinish = view.findViewById(R.id.button_finish);
        progressBar = view.findViewById(R.id.progress_bar);
        pollsRecyclerView = view.findViewById(R.id.polls_recycler_view);
        pollsViewPager = view.findViewById(R.id.polls_view_pager);
        progressBar.setVisibility(View.VISIBLE);
        new DataBaseHelper().readPolicies(new DataBaseHelper.DataStatus() {

            /**
             * Function handler for when data is loaded.
             *
             * @param p list of policies
             * @param k list of policy keys
             */
            @Override
            public void DataIsLoaded(List<Policy> p, List<String> k) {
                final ArrayList<Policy> policies = new ArrayList<>(p);
                final ArrayList<String> keys = new ArrayList<>(k);
                setRecyclerViewAdapter(keys);
                setViewPagerAdapter(keys, policies);
                progressBar.setVisibility(View.GONE);
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

            }
        });
        buttonBack.setOnClickListener(this);
        buttonFinish.setOnClickListener(this);
    }

    /**
     * Essentially a construction function that sets the
     * Pager Adapter with content.
     *
     * @param keys     list of policy key values
     * @param policies list of policies
     */
    private void setViewPagerAdapter(final ArrayList<String> keys, final ArrayList<Policy> policies) {
        if (!isAdded()) return;
        pollsViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return PollFragment.newInstance(policies.get(i), keys.get(i), previousSelections.get(keys.get(i)));
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
                clickRecyclerView(pos);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        pollsViewPager.setCurrentItem(currentPosition);
    }

    /**
     * Defines an onClick behavior for the cells
     * of the RecyclerView.
     *
     * @param pos position of cell clicked
     */
    private void clickRecyclerView(int pos) {
        for (int i = 0; i < pollsRecyclerView.getChildCount(); i++) {
            if (i == pos && pollsRecyclerView.getChildViewHolder(pollsRecyclerView.getChildAt(i)) instanceof PollIndexAdapter.PollIndexHolder) {
                PollIndexAdapter.PollIndexHolder holder = (PollIndexAdapter.PollIndexHolder) pollsRecyclerView.getChildViewHolder(pollsRecyclerView.getChildAt(i));
                if (pollsRecyclerView.getAdapter() != null)
                    ((PollIndexAdapter) pollsRecyclerView.getAdapter()).setSelected(holder.itemView);
            }
        }
    }

    /**
     * Sets the layout of the RecyclerView Adapter.
     *
     * @param keys list of keys
     */
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

    /**
     * When the user clicks a button, the Button object receives an on-click event.
     * <p>
     * If you use this event handler in your code, make sure that you are
     * having that button in your MainActivity. It won’t work if you use this event
     * handler in fragment because onClick attribute only works in Activity.
     *
     * @param v a view of the application
     */
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

    public void updateOptionsList(String key, Integer option) {
        previousSelections.put(key, option);
    }
}
