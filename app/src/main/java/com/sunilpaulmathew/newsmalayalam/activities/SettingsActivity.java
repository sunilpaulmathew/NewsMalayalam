package com.sunilpaulmathew.newsmalayalam.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.newsmalayalam.BuildConfig;
import com.sunilpaulmathew.newsmalayalam.R;
import com.sunilpaulmathew.newsmalayalam.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on January 14, 2021
 */

public class SettingsActivity extends AppCompatActivity {

    private ArrayList <RecycleViewItem> mData = new ArrayList<>();

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        AppCompatImageButton mBack = findViewById(R.id.back_button);
        LinearLayout mAppInfo = findViewById(R.id.app_info);
        MaterialTextView mAppTitle = findViewById(R.id.title);
        MaterialTextView mAppDescription = findViewById(R.id.description);
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);

        mAppTitle.setText(getString(R.string.app_name) + " " + BuildConfig.VERSION_NAME);
        mAppTitle.setTextColor(Utils.isDarkTheme(this) ? Color.WHITE : Color.BLACK);
        mAppDescription.setText(getString(R.string.about_summary));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        RecycleViewAdapter mRecycleViewAdapter = new RecycleViewAdapter(mData);
        mRecyclerView.setAdapter(mRecycleViewAdapter);

        mAppInfo.setOnClickListener(v -> Utils.goToSettings(this));

        mData.add(new RecycleViewItem(null, getString(R.string.app_description), null));
        mData.add(new RecycleViewItem(getString(R.string.support_development), getString(R.string.support_development_summary), getResources().getDrawable(R.drawable.ic_donate)));
        mData.add(new RecycleViewItem(getString(R.string.invite_friends), getString(R.string.invite_friends_Summary), getResources().getDrawable(R.drawable.ic_share)));
        mData.add(new RecycleViewItem(getString(R.string.source_code), getString(R.string.source_code_summary), getResources().getDrawable(R.drawable.ic_github)));
        mData.add(new RecycleViewItem(getString(R.string.rate_us), getString(R.string.rate_us_Summary), getResources().getDrawable(R.drawable.ic_playstore)));
        mData.add(new RecycleViewItem(getString(R.string.report_issue), getString(R.string.report_issue_summary), getResources().getDrawable(R.drawable.ic_bug)));

        mRecycleViewAdapter.setOnItemClickListener((position, v) -> {
            if (position == 0) {
                Utils.goToSettings(this);
            } else if (position == 1) {
                Utils.launchURL("https://smartpack.github.io/donation/", this);
            } else if (position == 2) {
                Intent share_app = new Intent();
                share_app.setAction(Intent.ACTION_SEND);
                share_app.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                share_app.putExtra(Intent.EXTRA_TEXT, getString(R.string.shared_by_message, BuildConfig.VERSION_NAME));
                share_app.setType("text/plain");
                Intent shareIntent = Intent.createChooser(share_app, getString(R.string.share_with));
                startActivity(shareIntent);
            } else if (position == 3) {
                Utils.launchURL("https://github.com/sunilpaulmathew/NewsMalayalam/", this);
            } else if (position == 4) {
                Utils.launchURL("https://play.google.com/store/apps/details?id=com.sunilpaulmathew.newsmalayalam/", this);
            } else if (position == 5) {
                Utils.launchURL("https://github.com/sunilpaulmathew/NewsMalayalam/issues/new/", this);
            }
        });

        mBack.setOnClickListener(v -> onBackPressed());
    }

    private static class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

        private ArrayList<RecycleViewItem> data;

        private static ClickListener mClickListener;

        public RecycleViewAdapter(ArrayList<RecycleViewItem> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_settings, parent, false);
            return new RecycleViewAdapter.ViewHolder(rowItem);
        }

        @Override
        public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {
            if (this.data.get(position).getTitle() != null) {
                holder.mTitle.setText(this.data.get(position).getTitle());
                holder.mTitle.setVisibility(View.VISIBLE);
                holder.mTitle.setTextColor(Utils.isDarkTheme(holder.mIcon.getContext()) ? Color.WHITE : Color.BLACK);
            }
            if (this.data.get(position).getDescription() != null) {
                holder.mDescription.setText(this.data.get(position).getDescription());
                holder.mDescription.setVisibility(View.VISIBLE);
            }
            if (this.data.get(position).getIcon() != null) {
                holder.mIcon.setImageDrawable(this.data.get(position).getIcon());
                holder.mIcon.setColorFilter(Utils.isDarkTheme(holder.mIcon.getContext()) ? Color.WHITE : Color.BLACK);
                holder.mIcon.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return this.data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private AppCompatImageView mIcon;
            private MaterialTextView mTitle;
            private MaterialTextView mDescription;

            public ViewHolder(View view) {
                super(view);
                view.setOnClickListener(this);
                this.mIcon = view.findViewById(R.id.icon);
                this.mTitle = view.findViewById(R.id.title);
                this.mDescription = view.findViewById(R.id.description);
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
        private String mDescription;
        private Drawable mIcon;

        public RecycleViewItem(String title, String description, Drawable icon) {
            this.mTitle = title;
            this.mDescription = description;
            this.mIcon = icon;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getDescription() {
            return mDescription;
        }

        public Drawable getIcon() {
            return mIcon;
        }
    }

}