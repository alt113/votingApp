package com.example.apple.votingapp.activity;

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
import android.widget.Toast;

import com.example.apple.votingapp.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static java.sql.DriverManager.println;

public class RecyclerView_Configuration {

    private Context mContext;
    private PolicyAdapter mPolicyAdapter;
    public void setConfig(RecyclerView r, Context c, List<Policy> pList, List<String> kList){
        mContext = c;
        mPolicyAdapter = new PolicyAdapter(pList, kList);
        r.setLayoutManager(new LinearLayoutManager(c));
        r.setAdapter(mPolicyAdapter);
    }


    class PolicyItemView extends RecyclerView.ViewHolder{

        private TextView pTitle;
        private TextView pDescription;
        private TextView voteCount;

        private Button voteUp;
        private Button voteDown;

        private String key;

        public PolicyItemView(final ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.policy_list_item, parent, false));

            pTitle = (TextView) itemView.findViewById(R.id.textView1);
            pDescription = (TextView) itemView.findViewById(R.id.textView2);
            voteCount = (TextView) itemView.findViewById(R.id.voteCount);

            voteUp = (Button) itemView.findViewById(R.id.voteUp);
            voteDown = (Button) itemView.findViewById(R.id.voteDown);

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

        public void bind(Policy p, String key){
            pTitle.setText(p.getTitle());
            pDescription.setText(p.getDescription());
            voteCount.setText(Long.toString(p.getVote()));

            this.key = key;
        }

        public void clickFunction(View view, Policy policy){

            new DBHelper().updatePolicy(key, policy, new DBHelper.DataStatus() {
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

    class PolicyAdapter extends RecyclerView.Adapter<PolicyItemView>{
        private List<Policy> mPolicyList;
        private List<String> mKeys;

        public PolicyAdapter(List<Policy> mPolicyList, List<String> mKeys) {
            this.mPolicyList = mPolicyList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public PolicyItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new PolicyItemView(viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull PolicyItemView policyItemView, int i) {
            policyItemView.bind(mPolicyList.get(i), mKeys.get(i));
        }

        @Override
        public int getItemCount() {
            return mPolicyList.size();
        }
    }
}
