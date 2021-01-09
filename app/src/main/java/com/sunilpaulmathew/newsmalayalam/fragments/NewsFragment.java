package com.sunilpaulmathew.newsmalayalam.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.sunilpaulmathew.newsmalayalam.BuildConfig;
import com.sunilpaulmathew.newsmalayalam.R;
import com.sunilpaulmathew.newsmalayalam.activities.AboutActivity;
import com.sunilpaulmathew.newsmalayalam.utils.Utils;

import java.util.Objects;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on January 08, 2020
 */

public class NewsFragment extends Fragment {

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
        AppCompatImageButton mMenu = mRootView.findViewById(R.id.menu_button);
        AppCompatImageButton mInfo = mRootView.findViewById(R.id.info_button);
        mShare = mRootView.findViewById(R.id.share_button);
        MaterialCardView mBack = mRootView.findViewById(R.id.back_button);
        MaterialCardView mHome = mRootView.findViewById(R.id.home_button);
        MaterialCardView mForward = mRootView.findViewById(R.id.forward_button);
        mWebView = mRootView.findViewById(R.id.webview);

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

        mWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                mSplashScreen.setVisibility(View.GONE);
                mShare.setVisibility(View.VISIBLE);
            }
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mUrl = url;
                mWebView.loadUrl(url);
                return true;
            }
        });

        mWebView.loadUrl(mUrl);

        mMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireActivity(), mMenu);
            Menu menu = popupMenu.getMenu();
            menu.add(Menu.NONE, 0, Menu.NONE, R.string.mathrubhumi);
            menu.add(Menu.NONE, 1, Menu.NONE, R.string.malayala_manorama);
            menu.add(Menu.NONE, 2, Menu.NONE, R.string.indian_express);
            menu.add(Menu.NONE, 3, Menu.NONE, R.string.deshabhimani);
            menu.add(Menu.NONE, 4, Menu.NONE, R.string.madhyamam);
            menu.add(Menu.NONE, 5, Menu.NONE, R.string.twenty_four);
            menu.add(Menu.NONE, 6, Menu.NONE, R.string.mangalam);
            menu.add(Menu.NONE, 7, Menu.NONE, R.string.media_one);
            menu.add(Menu.NONE, 8, Menu.NONE, R.string.deepika);
            menu.add(Menu.NONE, 9, Menu.NONE, R.string.asianet);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case 0:
                        reloadPage("https://www.mathrubhumi.com/mobile/");
                        break;
                    case 1:
                        reloadPage("https://www.manoramaonline.com/");
                        break;
                    case 2:
                        reloadPage("https://malayalam.indianexpress.com/");
                        break;
                    case 3:
                        reloadPage("https://www.deshabhimani.com/");
                        break;
                    case 4:
                        reloadPage("https://www.madhyamam.com/");
                        break;
                    case 5:
                        reloadPage("https://www.twentyfournews.com/");
                        break;
                    case 6:
                        reloadPage("https://www.mangalam.com/");
                        break;
                    case 7:
                        reloadPage("https://www.mediaonetv.in/");
                        break;
                    case 8:
                        reloadPage("https://www.deepika.com/");
                        break;
                    case 9:
                        reloadPage("https://www.asianetnews.com/");
                        break;
                }
                return false;
            });
            popupMenu.show();
        });

        mShare.setOnClickListener(v -> {
            Intent share_news = new Intent();
            share_news.setAction(Intent.ACTION_SEND);
            share_news.putExtra(Intent.EXTRA_SUBJECT, mWebView.getTitle());
            share_news.putExtra(Intent.EXTRA_TEXT, mWebView.getUrl() + "\n\n" + getString(R.string.shared_by_message, BuildConfig.VERSION_NAME));
            share_news.setType("text/plain");
            Intent shareIntent = Intent.createChooser(share_news, getString(R.string.share_with));
            startActivity(shareIntent);
        });

        mBack.setOnClickListener(v -> {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            }
        });

        mHome.setOnClickListener(v -> {
            if (!Objects.equals(mWebView.getUrl(), mUrlHome)) {
                mUrl = mUrlHome;
                mWebView.loadUrl(mUrl);
            }
        });

        mForward.setOnClickListener(v -> {
            if (mWebView.canGoForward()) {
                mWebView.goForward();
            }
        });

        mInfo.setOnClickListener(v -> {
            Intent about = new Intent(requireActivity(), AboutActivity.class);
            startActivity(about);
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (mWebView.canGoBack()) {
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