/*
 * Copyright (C) 2020-2021 sunilpaulmathew <sunil.kde@gmail.com>
 *
 * This file is part of The Translator, An application to help translate android apps.
 *
 */

package com.sunilpaulmathew.newsmalayalam.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.sunilpaulmathew.newsmalayalam.BuildConfig;
import com.sunilpaulmathew.newsmalayalam.R;
import com.sunilpaulmathew.newsmalayalam.utils.Utils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on January 08, 2020
 */

public class AboutActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        MaterialCardView mDonations = findViewById(R.id.donations);
        MaterialTextView mAppName = findViewById(R.id.app_title);
        MaterialTextView mCancel = findViewById(R.id.cancel_button);
        LinearLayout mSourceCode = findViewById(R.id.source_code);
        LinearLayout mRateUS = findViewById(R.id.play_store);
        LinearLayout mReportIssue = findViewById(R.id.report_issue);

        mAppName.setText(getString(R.string.app_name) + " " + BuildConfig.VERSION_NAME);

        mSourceCode.setOnClickListener(v -> Utils.launchURL("https://github.com/sunilpaulmathew/NewsMalayalam/", this));
        mRateUS.setOnClickListener(v -> Utils.launchURL("https://play.google.com/store/apps/details?id=com.sunilpaulmathew.newsmalayalam", this));
        mReportIssue.setOnClickListener(v -> Utils.launchURL("https://github.com/sunilpaulmathew/NewsMalayalam/issues/new", this));
        mDonations.setOnClickListener(v -> Utils.launchURL("https://smartpack.github.io/donation/", this));
        mCancel.setOnClickListener(v -> onBackPressed());
    }

}