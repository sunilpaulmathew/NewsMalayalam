package com.sunilpaulmathew.newsmalayalam.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
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
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.sunilpaulmathew.newsmalayalam.BuildConfig;
import com.sunilpaulmathew.newsmalayalam.R;
import com.sunilpaulmathew.newsmalayalam.activities.AboutActivity;
import com.sunilpaulmathew.newsmalayalam.adapters.RecycleViewAdapter;
import com.sunilpaulmathew.newsmalayalam.adapters.RecycleViewItem;
import com.sunilpaulmathew.newsmalayalam.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on January 08, 2020
 */

public class NewsFragment extends Fragment {

    private ArrayList<RecycleViewItem> mData = new ArrayList<>();
    private boolean mExit;
    private Handler mHandler = new Handler();
    private LinearLayout mSplashScreen;
    private MaterialCardView mShare;
    private String mUrl = null, mUrlHome = null;
    private View mRootView;
    private WebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_layout, container, false);

        mSplashScreen = mRootView.findViewById(R.id.splash_screen);
        AppCompatImageButton mNavigation = mRootView.findViewById(R.id.navigation_button);
        AppCompatImageButton mMenu = mRootView.findViewById(R.id.menu_button);
        AppCompatImageButton mInfo = mRootView.findViewById(R.id.info_button);
        mShare = mRootView.findViewById(R.id.share_button);
        MaterialCardView mBack = mRootView.findViewById(R.id.back_button);
        MaterialCardView mNavigationCard = mRootView.findViewById(R.id.navigation_card);
        MaterialCardView mHome = mRootView.findViewById(R.id.home_button);
        MaterialCardView mForward = mRootView.findViewById(R.id.forward_button);
        ProgressBar mProgress = mRootView.findViewById(R.id.progress);
        mWebView = mRootView.findViewById(R.id.webview);
        RecyclerView mRecyclerView = mRootView.findViewById(R.id.recycler_view);

        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient());

        if (Utils.getString("home_page", null, getActivity()) == null) {
            Utils.saveString("home_page", "https://www.mathrubhumi.com/mobile/", getActivity());
        }
        if (mUrl == null || mUrl.isEmpty()) {
            mUrlHome = Utils.getString("home_page", null, getActivity());
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
        mData.add(new RecycleViewItem(getString(R.string.malayala_manorama), "https://www.manoramaonline.com/"));
        mData.add(new RecycleViewItem(getString(R.string.mangalam), "https://www.mangalam.com/"));
        mData.add(new RecycleViewItem(getString(R.string.mathrubhumi), "https://www.mathrubhumi.com/mobile/"));
        mData.add(new RecycleViewItem(getString(R.string.media_one), "https://www.mediaonetv.in/"));
        mData.add(new RecycleViewItem(getString(R.string.twenty_four), "https://www.twentyfournews.com/"));

        mRecycleViewAdapter.setOnItemClickListener((position, v) -> {
            mRecycleViewAdapter.notifyDataSetChanged();
            reloadPage(mData.get(position).getUrl());
            mNavigationCard.setVisibility(View.GONE);
        });

        mNavigation.setOnClickListener(v -> {
            if (mNavigationCard.getVisibility() == View.VISIBLE) {
                mNavigationCard.setVisibility(View.GONE);
            } else {
                mNavigationCard.setVisibility(View.VISIBLE);
            }
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

        mBack.setOnClickListener(v -> {
            if (mNavigationCard.getVisibility() == View.VISIBLE) {
                mNavigationCard.setVisibility(View.GONE);
                return;
            }
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            }
        });

        mHome.setOnClickListener(v -> {
            if (mNavigationCard.getVisibility() == View.VISIBLE) {
                mNavigationCard.setVisibility(View.GONE);
                return;
            }
            if (!Objects.equals(mWebView.getUrl(), mUrlHome)) {
                mUrl = mUrlHome;
                mWebView.loadUrl(mUrl);
            }
        });

        mForward.setOnClickListener(v -> {
            if (mNavigationCard.getVisibility() == View.VISIBLE) {
                mNavigationCard.setVisibility(View.GONE);
                return;
            }
            if (mWebView.canGoForward()) {
                mWebView.goForward();
            }
        });

        mInfo.setOnClickListener(v -> {
            if (mNavigationCard.getVisibility() == View.VISIBLE) {
                mNavigationCard.setVisibility(View.GONE);
                return;
            }
            Intent about = new Intent(requireActivity(), AboutActivity.class);
            startActivity(about);
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
        mUrlHome = url;
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