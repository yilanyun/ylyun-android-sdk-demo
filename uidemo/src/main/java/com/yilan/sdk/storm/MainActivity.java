package com.yilan.sdk.storm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

//import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.entity.Channel;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.ad.entity.AdConfig;
import com.yilan.sdk.ui.category.ChannelFragment;
import com.yilan.sdk.ui.feed.FeedFragment;
import com.yilan.sdk.ui.littlevideo.LittleVideoFragment;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;
    private ChannelFragment mChannelFragment;
    private LittleVideoFragment mLittleVideoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.post(new Runnable() {
            @Override
            public void run() {
                navigation.setSelectedItemId(R.id.navigation_home);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    hideAll();
                    if (mChannelFragment != null) {
                        manager.beginTransaction().show(mChannelFragment).commitAllowingStateLoss();
                    } else {
                        mChannelFragment = new ChannelFragment();
                        manager.beginTransaction().add(R.id.content, mChannelFragment, ChannelFragment.class.getSimpleName()).commitAllowingStateLoss();
                    }
                    return true;
                case R.id.navigation_little:
                    hideAll();
                    if (mLittleVideoFragment != null) {
                        manager.beginTransaction().show(mLittleVideoFragment).commitAllowingStateLoss();
                    } else {
                        mLittleVideoFragment = new LittleVideoFragment();
                        manager.beginTransaction().add(R.id.content, mLittleVideoFragment, LittleVideoFragment.class.getSimpleName()).commitAllowingStateLoss();
                    }
                    return true;
            }
            return false;
        }
    };

    private void hideAll() {
        List<Fragment> fragmentList = manager.getFragments();
        for (Fragment fragment : fragmentList) {
            manager.beginTransaction().hide(fragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void onBackPressed() {

        boolean canback = true;
        if (mChannelFragment != null && mChannelFragment.isVisible()) {
            canback = mChannelFragment.canBack();
        }
        if (!canback) {
            return;
        } else {
            super.onBackPressed();
        }
    }
}
