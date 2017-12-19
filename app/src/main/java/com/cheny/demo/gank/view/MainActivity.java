package com.cheny.demo.gank.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.cheny.demo.gank.R;
import com.cheny.demo.gank.android.AndroidFragment;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cheny on 2017/11/7.
 */

public class MainActivity extends RxAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppbarLayout;
    @BindView(R.id.coord)
    CoordinatorLayout mCoord;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    AndroidFragment mAndroidFragment;
    FirstFragment mSecondFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolBar();
        initDrawer();
        initViewPage();
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
    }

    private void initDrawer() {
        if (mNavView != null) {
            mNavView.setNavigationItemSelectedListener(this);
            ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);
            mDrawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }
    }

    private void initViewPage() {
        HomePagerAdapter mAdapter = new HomePagerAdapter(getSupportFragmentManager());
        mAndroidFragment = new AndroidFragment();
        mSecondFragment = new FirstFragment();
        mAdapter.addTab(mAndroidFragment, "Android");
        mAdapter.addTab(mSecondFragment, "Test2");
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager, false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      /*  RxDrawer.close(mDrawerLayout)
            .doOnNext(o -> {
                switch (item.getItemId()) {

                }
            })
            .subscribe();*/
        return false;
    }
}
