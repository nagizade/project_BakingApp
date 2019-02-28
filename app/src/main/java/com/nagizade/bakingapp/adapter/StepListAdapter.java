package com.nagizade.bakingapp.adapter;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nagizade.bakingapp.R;
import com.nagizade.bakingapp.activity.StepListActivity;
import com.nagizade.bakingapp.model.Step;
import com.nagizade.bakingapp.utils.ItemClickListener;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class StepListAdapter
        extends RecyclerView.Adapter<StepListAdapter.ViewHolder> {

    private final StepListActivity mParentActivity;
    private final ArrayList<Step> mValues;
    private final boolean             mTwoPane;
    private ItemClickListener mclickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.mclickListener = itemClickListener;
    }

    public StepListAdapter(StepListActivity parent,
                           ArrayList<Step> items,
                           boolean twoPane, ItemClickListener listener) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
        this.mclickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_list_content, parent, false);
        return new ViewHolder(view,mclickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(String.valueOf(mValues.get(position).getId()));
        holder.mContentView.setText(mValues.get(position).getShortDescription());
        holder.itemView.setTag(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView mIdView;
        final TextView mContentView;
        private ItemClickListener itemClickListener;

        ViewHolder(View view, ItemClickListener listener) {
            super(view);
            itemClickListener    = listener;
            mIdView =  view.findViewById(R.id.id_text);
            mContentView = view.findViewById(R.id.content);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v,getAdapterPosition());
        }
    }
}