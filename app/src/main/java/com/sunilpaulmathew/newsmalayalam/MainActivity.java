package com.sunilpaulmathew.newsmalayalam;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sunilpaulmathew.newsmalayalam.activities.StartActivity;
import com.sunilpaulmathew.newsmalayalam.fragments.NewsFragment;
import com.sunilpaulmathew.newsmalayalam.utils.Utils;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on January 08, 2020
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize App Theme
        Utils.initializeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Utils.getString("home_page", null, this) == null) {
            Intent setDefault = new Intent(this, StartActivity.class);
            startActivity(setDefault);
            finish();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new NewsFragment()).commit();
        }
    }

}