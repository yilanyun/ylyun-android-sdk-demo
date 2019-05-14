package com.yilan.sdk.storm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.common.util.FSLogcat;
import com.yilan.sdk.entity.MediaInfo;
import com.yilan.sdk.ui.custom.CustomListener;
import com.yilan.sdk.ui.custom.FeedExpress;

import java.util.List;

public class ExpressActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = ExpressActivity.class.getSimpleName();
    private ViewGroup blank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express);
        blank = findViewById(R.id.layout);
        findViewById(R.id.load).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        new FeedExpress().show(blank,null, new CustomListener() {
            @Override
            public void onShow(View view, MediaInfo info) {
                FSLogcat.e(TAG, "onShow");
            }

            @Override
            public void onClick(View view, MediaInfo info) {
                FSLogcat.e(TAG, "onClick");
            }

            @Override
            public void onError(int hashCode, Throwable e) {
                FSLogcat.e(TAG, "onError");
            }

            @Override
            public void noData(int hashCode) {
                FSLogcat.e(TAG, "noData");
            }

            @Override
            public void onSuccess(int hashCode, List<MediaInfo> mediaInfos) {
                FSLogcat.e(TAG, "success");
            }
        });
    }
}
