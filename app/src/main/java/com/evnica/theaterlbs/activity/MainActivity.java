package com.evnica.theaterlbs.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import com.evnica.theaterlbs.R;
import com.evnica.theaterlbs.fragment.AddTheaterFragment;
import com.evnica.theaterlbs.fragment.DbListFragment;
import com.evnica.theaterlbs.fragment.MapFragment;
import com.evnica.theaterlbs.fragment.TheaterListFragment;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.mainActivityTabHost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.mainActivityTabFrameLayout);

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Theaters"),
                TheaterListFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("DB"),
                DbListFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("OSM"),
                MapFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("Add"),
                AddTheaterFragment.class, null);
    }
}
