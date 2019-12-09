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

/**
 * Helper class in the construction of the PollFragment.
 *
 * @author Rayyan Nasr
 * @author Jihad Eddine Al Khrufan
 * @version %I%, %G%
 * @since 1.0
 */
public class PollIndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     *
     */
    private List<String> mList;

    /**
     *
     */
    private View mSelectedView = null;

    /**
     * The Click listener.
     */
    ClickListener clickListener;

    /**
     * Instantiates a new Poll index adapter.
     *
     * @param list the list
     */
    public PollIndexAdapter(List<String> list) {
        this.mList = list;
    }

    /**
     * Sets click listener.
     *
     * @param clickListener the click listener
     */
    public void setClickListener(PollIndexAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder
     * of the given type to represent an item. This new ViewHolder
     * should be constructed with a new View that can represent the
     * items of the given type. You can either create a new View manually
     * or inflate it from an XML layout file.
     *
     * @param viewGroup The ViewGroup into which the new View will be added
     * @param i given position
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.poll_index_holder, viewGroup, false);
        return new PollIndexHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the itemView to reflect the
     * item at the given position.
     *
     * @param viewHolder The ViewGroup into which the new View will be added
     * @param i given position
     */
    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

        ((PollIndexHolder) viewHolder).pollIndex.setText(String.valueOf(i));

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

    /**
     * The interface Click listener.
     */
    public interface ClickListener {
        /**
         * On item click.
         *
         * @param position the position
         */
        void onItemClick(int position);
    }

    /**
     * Sets selected.
     *
     * @param view the view
     */
    @SuppressLint("NewApi")
    public void setSelected(View view) {
        view.performClick();
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * The type Poll index holder.
     */
    public class PollIndexHolder extends RecyclerView.ViewHolder {
        /**
         * The Poll index.
         */
        TextView pollIndex;

        /**
         * Instantiates a new Poll index holder.
         *
         * @param itemView the item view
         */
        PollIndexHolder(@NonNull View itemView) {
            super(itemView);
            pollIndex = itemView.findViewById(R.id.poll_index);
        }
    }
}
