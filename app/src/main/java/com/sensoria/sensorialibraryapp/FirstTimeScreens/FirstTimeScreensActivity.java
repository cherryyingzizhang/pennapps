/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * ATTENTION !!!
 * This class also has information/swipe views
 */

package com.sensoria.sensorialibraryapp.FirstTimeScreens;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.sensoria.sensorialibraryapp.R;
import com.viewpagerindicator.CirclePageIndicator;

public class FirstTimeScreensActivity extends FragmentActivity implements ActionBar.TabListener
{

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;

    //the circle page indicator swipe thing
    public static CirclePageIndicator pageIndicator;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_info_activity);

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

//        Parse.initialize(this, "TsVbzF7jXzY1C0o86V2xxAxgSxvy4jmbyykOabPl", "VzamwWm4WswbDFxrxos2oSerQ2Av4RM6J5mNnNgr");

        final ActionBar actionBar = getActionBar();
        if (actionBar != null)
        {
            actionBar.hide();
            actionBar.setHomeButtonEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);

        ViewPager.SimpleOnPageChangeListener mPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        };

        pageIndicator = (CirclePageIndicator) findViewById(R.id.CPI_pageIndicator);
        pageIndicator.setViewPager(mViewPager);
        pageIndicator.setOnPageChangeListener(mPageChangeListener);
        pageIndicator.setCurrentItem(0);

        //TODO: make circle page indicator look better
        final float density = getResources().getDisplayMetrics().density;
        pageIndicator.setRadius(6 * density);
        pageIndicator.setPageColor(0xFFDBD2D2);
        pageIndicator.setFillColor(0xFFFFFFFF);
        pageIndicator.setStrokeColor(0xFFDBD2D2);
        pageIndicator.setStrokeWidth(1);

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
}
