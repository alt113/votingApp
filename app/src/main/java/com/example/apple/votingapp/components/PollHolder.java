package com.example.apple.votingapp.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.apple.votingapp.R;
import com.example.apple.votingapp.classes.Policy;
import com.example.apple.votingapp.utils.DataBaseHelper;

import java.util.List;

public class PollHolder {

    private Context mContext;
    private PollAdapter mPollAdapter;

    public void setConfig(RecyclerView r, Context c, List<Policy> pList, List<String> kList) {
        mContext = c;
        mPollAdapter = new PollAdapter(pList, kList);
        r.setLayoutManager(new LinearLayoutManager(c));
        r.setAdapter(mPollAdapter);
    }


    class PollItemView extends RecyclerView.ViewHolder {

        private TextView pTitle;
        private TextView pDescription;
        private TextView voteCount;

        private Button voteUp;
        private Button voteDown;

        private String key;

        PollItemView(final ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.fragment_poll_holder, parent, false));

            pTitle = (TextView) itemView.findViewById(R.id.poll_id);
            pDescription = (TextView) itemView.findViewById(R.id.poll_question);
            voteCount = (TextView) itemView.findViewById(R.id.vote_count);

            voteUp = (Button) itemView.findViewById(R.id.button_upvote);
            voteDown = (Button) itemView.findViewById(R.id.button_downvote);

            voteUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickFunction(parent, new Policy(pTitle.getText().toString(), pDescription.getText().toString(), Long.parseLong(voteCount.getText().toString()) + 1));
                    voteCount.setText(Long.toString(Long.parseLong(voteCount.getText().toString()) + 1));
                }
            });

            voteDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickFunction(parent, new Policy(pTitle.getText().toString(), pDescription.getText().toString(), Long.parseLong(voteCount.getText().toString()) - 1));
                    voteCount.setText(Long.toString(Long.parseLong(voteCount.getText().toString()) - 1));
                }
            });
        }

        void bind(Policy p, String key) {
            pTitle.setText(p.getTitle());
            pDescription.setText(p.getDescription());
            voteCount.setText(Long.toString(p.getVote()));
            this.key = key;
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

    class PollAdapter extends RecyclerView.Adapter<PollItemView> {
        private List<Policy> mPollList;
        private List<String> mKeys;

        PollAdapter(List<Policy> mPollList, List<String> mKeys) {
            this.mPollList = mPollList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public PollItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new PollItemView(viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull PollItemView pollItemView, int i) {
            pollItemView.bind(mPollList.get(i), mKeys.get(i));
        }

        @Override
        public int getItemCount() {
            return mPollList.size();
        }
    }
}
