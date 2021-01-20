package com.sunilpaulmathew.newsmalayalam.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.card.MaterialCardView;
import com.sunilpaulmathew.newsmalayalam.BuildConfig;
import com.sunilpaulmathew.newsmalayalam.R;
import com.sunilpaulmathew.newsmalayalam.activities.SettingsActivity;
import com.sunilpaulmathew.newsmalayalam.adapters.RecycleViewAdapter;
import com.sunilpaulmathew.newsmalayalam.adapters.RecycleViewItem;
import com.sunilpaulmathew.newsmalayalam.utils.Utils;

import java.util.ArrayList;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on January 08, 2020
 */

public class NewsFragment extends Fragment {

    private ArrayList<RecycleViewItem> mData = new ArrayList<>();
    private boolean mExit;
    private Handler mHandler = new Handler();
    private LinearLayout mSplashScreen;
    private MaterialCardView mShare;
    private String mUrl = null;
    private View mRootView;
    private WebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_layout, container, false);

        mSplashScreen = mRootView.findViewById(R.id.splash_screen);
        AppCompatImageButton mNavigation = mRootView.findViewById(R.id.navigation_button);
        AppCompatImageButton mMenu = mRootView.findViewById(R.id.menu_button);
        mShare = mRootView.findViewById(R.id.share_button);
        MaterialCardView mNavigationCard = mRootView.findViewById(R.id.navigation_card);
        ProgressBar mProgress = mRootView.findViewById(R.id.progress);
        mWebView = mRootView.findViewById(R.id.webview);
        SwipeRefreshLayout mSwipeRefreshLayout = mRootView.findViewById(R.id.swipe_layout);
        RecyclerView mRecyclerView = mRootView.findViewById(R.id.recycler_view);

        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setBuiltInZoomControls(true);

        mWebView.setWebViewClient(new WebViewClient());

        if (mUrl == null || mUrl.isEmpty()) {
            mUrl = Utils.getString("home_page", null, getActivity());
        }

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                mProgress.setProgress(progress);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                mSplashScreen.setVisibility(View.GONE);
                mShare.setVisibility(View.VISIBLE);
            }
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (mNavigationCard.getVisibility() == View.VISIBLE) {
                    mNavigationCard.setVisibility(View.GONE);
                    return true;
                }
                mUrl = url;
                mWebView.loadUrl(url);
                return true;
            }
        });

        mWebView.loadUrl(mUrl);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        RecycleViewAdapter mRecycleViewAdapter = new RecycleViewAdapter(mData);
        mRecyclerView.setAdapter(mRecycleViewAdapter);

        mData.add(new RecycleViewItem(getString(R.string.asianet), "https://www.asianetnews.com/"));
        mData.add(new RecycleViewItem(getString(R.string.deepika), "https://www.deepika.com/"));
        mData.add(new RecycleViewItem(getString(R.string.deshabhimani), "https://www.deshabhimani.com/"));
        mData.add(new RecycleViewItem(getString(R.string.indian_express), "https://malayalam.indianexpress.com/"));
        mData.add(new RecycleViewItem(getString(R.string.madhyamam), "https://www.madhyamam.com/"));
        mData.add(new RecycleViewItem(getString(R.string.malayala_manorama), "https://www.manoramaonline.com/home.html"));
        mData.add(new RecycleViewItem(getString(R.string.mangalam), "https://www.mangalam.com/"));
        mData.add(new RecycleViewItem(getString(R.string.mathrubhumi), "https://www.mathrubhumi.com/mobile/"));
        mData.add(new RecycleViewItem(getString(R.string.media_one), "https://www.mediaonetv.in/"));
        mData.add(new RecycleViewItem(getString(R.string.twenty_four), "https://www.twentyfournews.com/"));

        mRecycleViewAdapter.setOnItemClickListener((position, v) -> {
            reloadPage(mData.get(position).getUrl());
            mRecycleViewAdapter.notifyDataSetChanged();
            mNavigationCard.setVisibility(View.GONE);
        });

        mNavigation.setOnClickListener(v -> {
            if (mNavigationCard.getVisibility() == View.VISIBLE) {
                mNavigationCard.setVisibility(View.GONE);
            } else {
                mNavigationCard.setVisibility(View.VISIBLE);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mWebView.reload();
            mWebView.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
        });

        mMenu.setOnClickListener(v -> {
            if (mNavigationCard.getVisibility() == View.VISIBLE) {
                mNavigationCard.setVisibility(View.GONE);
                return;
            }
            Intent settingsMenu = new Intent(requireActivity(), SettingsActivity.class);
            startActivity(settingsMenu);
        });

        mShare.setOnClickListener(v -> {
            if (mNavigationCard.getVisibility() == View.VISIBLE) {
                mNavigationCard.setVisibility(View.GONE);
                return;
            }
            Intent share_news = new Intent();
            share_news.setAction(Intent.ACTION_SEND);
            share_news.putExtra(Intent.EXTRA_SUBJECT, mWebView.getTitle());
            share_news.putExtra(Intent.EXTRA_TEXT, mWebView.getUrl() + "\n\n" + getString(R.string.shared_by_message, BuildConfig.VERSION_NAME));
            share_news.setType("text/plain");
            Intent shareIntent = Intent.createChooser(share_news, getString(R.string.share_with));
            startActivity(shareIntent);
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (mNavigationCard.getVisibility() == View.VISIBLE) {
                    mNavigationCard.setVisibility(View.GONE);
                } else if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else if (mExit) {
                    mExit = false;
                    this.remove();
                    requireActivity().onBackPressed();
                } else {
                    Utils.showSnackbar(mRootView, getString(R.string.press_back));
                    mExit = true;
                    mHandler.postDelayed(() -> mExit = false, 2000);
                }
            }
        });

        return mRootView;
    }

    private void reloadPage(String url) {
        if (url.equals(mWebView.getUrl())) return;
        mUrl = url;
        Utils.saveString("home_page", url, requireActivity());
        mShare.setVisibility(View.GONE);
        mSplashScreen.setVisibility(View.VISIBLE);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                mSplashScreen.setVisibility(View.GONE);
                mShare.setVisibility(View.VISIBLE);
            }
        });
    }

}