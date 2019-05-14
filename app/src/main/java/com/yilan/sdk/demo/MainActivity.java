package com.yilan.sdk.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.yilan.sdk.entity.Channel;
import com.yilan.sdk.ui.category.ChannelFragment;
import com.yilan.sdk.ui.littlevideo.KSLittleVideoFragment;
import com.yilan.sdk.ui.littlevideo.LittleVideoFragment;
import com.yilan.sdk.webui.category.ChannelWebFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mContent;
    private FragmentManager manager;
    private ChannelFragment uiFragment;
        private ChannelWebFragment webFragment;
    private LittleVideoFragment littleVideoFragment;
    private MyFragment myFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    hideAll();
                    if (uiFragment != null) {
                        manager.beginTransaction().show(uiFragment).commitAllowingStateLoss();
                    } else {
                        uiFragment = new ChannelFragment();
                        manager.beginTransaction().add(R.id.content, uiFragment, ChannelFragment.class.getSimpleName()).commitAllowingStateLoss();
                    }
                    return true;
                case R.id.navigation_dashboard:
                    hideAll();
                    if (webFragment != null) {
                        manager.beginTransaction().show(webFragment).commitAllowingStateLoss();
                    } else {
                        webFragment = new ChannelWebFragment();
                        manager.beginTransaction().add(R.id.content, webFragment, ChannelWebFragment.class.getSimpleName()).commitAllowingStateLoss();
                    }
                    return true;
                case R.id.navigation_notifications:
                    hideAll();
                    if (myFragment != null) {
                        manager.beginTransaction().show(myFragment).commitAllowingStateLoss();
                    } else {
                        myFragment = new MyFragment();
                        manager.beginTransaction().add(R.id.content, myFragment, MyFragment.class.getSimpleName()).commitAllowingStateLoss();
                    }
                    return true;
                case R.id.navigation_little:
                    hideAll();
                    if (littleVideoFragment != null) {
                        manager.beginTransaction().show(littleVideoFragment).commitAllowingStateLoss();
                        littleVideoFragment.refresh();
                    } else {
                        littleVideoFragment = LittleVideoFragment.newInstance();
                        manager.beginTransaction().add(R.id.content, littleVideoFragment, LittleVideoFragment.class.getSimpleName()).commitAllowingStateLoss();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        try {
//            Class.forName("com.bumptech.glide.request.RequestOptions");
//        } catch (ClassNotFoundException var1) {
//            var1.printStackTrace();
//        }


        mContent = findViewById(R.id.content);
        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        manager = getSupportFragmentManager();
        navigation.post(new Runnable() {
            @Override
            public void run() {
                navigation.setSelectedItemId(R.id.navigation_home);
            }
        });
    }

}
