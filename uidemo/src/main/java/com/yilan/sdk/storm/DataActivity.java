package com.yilan.sdk.storm;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yilan.sdk.entity.Channel;
import com.yilan.sdk.entity.ChannelList;
import com.yilan.sdk.net.request.NSubscriber;
import com.yilan.sdk.net.request.YLFeedRequest;
import com.yilan.sdk.ui.littlevideo.LittleVideoFragment;

import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity {

    TabLayout tablayout;
    ViewPager viewpager;

    List<Channel> mChannelList = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        initViews();
        requestChannel();

        initListeners();
    }

    private void initViews() {
        viewpager = findViewById(R.id.viewpager);
        tablayout = findViewById(R.id.tablayout);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                LittleVideoFragment littleVideoFragment = new LittleVideoFragment();
//                littleVideoFragment.setChannel(mChannelList.get(position));
                return littleVideoFragment;
            }

            @Override
            public int getCount() {
                return mChannelList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mChannelList.get(position).getName();
            }
        };
        viewpager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewpager);

    }

    private void initListeners() {
    }


    private void requestChannel() {
        YLFeedRequest.instance().getChannels(new NSubscriber<ChannelList>() {
            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                String error = "category list onError:" + throwable.getMessage();
                showToast(error);
            }

            @Override
            public void onNext(ChannelList categoryList) {
                super.onNext(categoryList);
                mChannelList = categoryList.getData();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void showToast(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

}
