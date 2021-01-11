package com.sunilpaulmathew.newsmalayalam.adapters;

import java.io.Serializable;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on January 11, 2020
 */

public class RecycleViewItem implements Serializable {
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