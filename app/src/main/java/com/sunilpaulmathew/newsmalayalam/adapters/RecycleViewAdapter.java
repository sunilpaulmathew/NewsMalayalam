package com.sunilpaulmathew.newsmalayalam.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.newsmalayalam.R;
import com.sunilpaulmathew.newsmalayalam.utils.Utils;

import java.util.ArrayList;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on January 11, 2020
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    private static ArrayList<RecycleViewItem> data;

    private static ClickListener mClickListener;

    public RecycleViewAdapter(ArrayList<RecycleViewItem> data) {
        RecycleViewAdapter.data = data;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_start, parent, false);
        return new RecycleViewAdapter.ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {
        try {
            holder.mTitle.setText(data.get(position).getTitle());
            if (Utils.getString("home_page", null, holder.mTitle.getContext())
                    .equals(data.get(position).getUrl())) {
                holder.mTitle.setTextColor(holder.mTitle.getContext().getResources().getColor(R.color.black));
                holder.mCard.setCardBackgroundColor(holder.mTitle.getContext().getResources().getColor(R.color.blue));
            }
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MaterialCardView mCard;
        private MaterialTextView mTitle;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.mCard = view.findViewById(R.id.card);
            this.mTitle = view.findViewById(R.id.title);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecycleViewAdapter.mClickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}