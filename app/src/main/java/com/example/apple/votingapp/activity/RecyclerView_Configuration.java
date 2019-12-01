package com.example.apple.votingapp.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apple.votingapp.R;

import java.util.List;

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

        private String key;

        public PolicyItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.policy_list_item, parent, false));

            pTitle = (TextView) itemView.findViewById(R.id.textView1);
            pDescription = (TextView) itemView.findViewById(R.id.textView2);
        }

        public void bind(Policy p, String key){
            pTitle.setText(p.getTitle());
            pDescription.setText(p.getDescription());
            this.key = key;
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
