package com.yilan.sdk.storm;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

//import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.entity.Channel;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.ad.entity.AdConfig;
import com.yilan.sdk.ui.category.ChannelFragment;
import com.yilan.sdk.ui.feed.FeedFragment;
import com.yilan.sdk.ui.littlevideo.LittleVideoFragment;


public class MainActivity extends AppCompatActivity {

    private View mDecorView;
    private FeedFragment feedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.scroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feedFragment != null) {
                    feedFragment.scrollToTop();
                }
            }
        });

        FragmentManager manager = getSupportFragmentManager();
        //带频道导航栏

        ChannelFragment fragment = new ChannelFragment();
        manager.beginTransaction().replace(R.id.content, fragment).commitAllowingStateLoss();

        //单个频道
//        Channel channel = new Channel();
//        channel.setId("1291");
//
//        feedFragment = FeedFragment.newInstance(channel);
//        manager.beginTransaction().replace(R.id.content, feedFragment).commitAllowingStateLoss();


        //小视频
//        LittleVideoFragment littleVideoFragment = LittleVideoFragment.newInstance();
//        manager.beginTransaction().replace(R.id.content, littleVideoFragment).commitAllowingStateLoss();
    }

    private void hideSystemUI() {
        mDecorView = getWindow().getDecorView();
        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
}
