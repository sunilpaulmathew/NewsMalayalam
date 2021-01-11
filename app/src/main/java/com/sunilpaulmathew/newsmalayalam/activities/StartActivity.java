package com.sunilpaulmathew.newsmalayalam.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.newsmalayalam.MainActivity;
import com.sunilpaulmathew.newsmalayalam.R;
import com.sunilpaulmathew.newsmalayalam.adapters.RecycleViewAdapter;
import com.sunilpaulmathew.newsmalayalam.adapters.RecycleViewItem;
import com.sunilpaulmathew.newsmalayalam.utils.Utils;

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

}