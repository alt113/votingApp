package com.example.apple.votingapp.components;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apple.votingapp.R;

import java.util.List;

public class PollIndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mList;
    private View mSelectedView = null;
    ClickListener clickListener;

    public PollIndexAdapter(List<String> list) {
        this.mList = list;
    }

    public void setClickListener(PollIndexAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.poll_index_holder, viewGroup, false);
        return new PollIndexHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

        ((PollIndexHolder) viewHolder).pollIndex.setText(String.valueOf(i+1));

        // This block handles selecting one and only one subcategory
        if (mSelectedView == null) {
            if (i == 0) {
                mSelectedView = viewHolder.itemView;
                viewHolder.itemView.setBackground(null);
            } else {
                viewHolder.itemView.setBackgroundResource(R.drawable.white_border_transparent_bkg);
            }
        } else {
            mSelectedView.setBackgroundResource(R.drawable.white_border_transparent_bkg);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectedView != null) {
                    mSelectedView.setBackground(null);
                }
                mSelectedView = viewHolder.itemView;
                mSelectedView.setBackgroundResource(R.drawable.white_border_transparent_bkg);
                clickListener.onItemClick(viewHolder.getAdapterPosition());
            }
        });
    }

    public interface ClickListener {
        void onItemClick(int position);
    }

    @SuppressLint("NewApi")
    public void setSelected(View view) {
        view.performClick();
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class PollIndexHolder extends RecyclerView.ViewHolder {
        TextView pollIndex;

        PollIndexHolder(@NonNull View itemView) {
            super(itemView);
            pollIndex = itemView.findViewById(R.id.poll_index);
        }
    }
}
