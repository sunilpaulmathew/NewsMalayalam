package com.sunilpaulmathew.newsmalayalam.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.newsmalayalam.MainActivity;
import com.sunilpaulmathew.newsmalayalam.R;
import com.sunilpaulmathew.newsmalayalam.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on January 08, 2020
 */

public class StartActivity extends AppCompatActivity {

    private ArrayList <RecycleViewItem> mData = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        MaterialTextView mCancel = findViewById(R.id.cancel_button);
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewAdapter mRecycleViewAdapter = new RecycleViewAdapter(mData);
        mRecyclerView.setAdapter(mRecycleViewAdapter);

        mData.add(new RecycleViewItem(getString(R.string.asianet), "https://www.asianetnews.com/"));
        mData.add(new RecycleViewItem(getString(R.string.deepika), "https://www.deepika.com/"));
        mData.add(new RecycleViewItem(getString(R.string.deshabhimani), "https://www.deshabhimani.com/"));
        mData.add(new RecycleViewItem(getString(R.string.indian_express), "https://malayalam.indianexpress.com/"));
        mData.add(new RecycleViewItem(getString(R.string.madhyamam), "https://www.madhyamam.com/"));
        mData.add(new RecycleViewItem(getString(R.string.malayala_manorama), "https://www.manoramaonline.com/"));
        mData.add(new RecycleViewItem(getString(R.string.mangalam), "https://www.mangalam.com/"));
        mData.add(new RecycleViewItem(getString(R.string.mathrubhumi), "https://www.mathrubhumi.com/mobile/"));
        mData.add(new RecycleViewItem(getString(R.string.media_one), "https://www.mediaonetv.in/"));
        mData.add(new RecycleViewItem(getString(R.string.twenty_four), "https://www.twentyfournews.com/"));

        mRecycleViewAdapter.setOnItemClickListener((position, v) -> {
            Utils.saveString("home_page", mData.get(position).getUrl(), this);
            mRecycleViewAdapter.notifyDataSetChanged();
            finish();
            Intent reload = new Intent(this, MainActivity.class);
            startActivity(reload);
        });

        mCancel.setOnClickListener(v -> onBackPressed());
    }

    private static class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

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
            } catch (NullPointerException ignored) {}
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

    private static class RecycleViewItem implements Serializable {
        private String mTitle;
        private String mUrl;

        public RecycleViewItem(String title, String url) {
            this.mTitle = title;
            this.mUrl = url;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getUrl() {
            return mUrl;
        }

    }

}