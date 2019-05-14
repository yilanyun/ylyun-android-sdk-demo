package com.yilan.sdk.demo;

import android.app.Application;

import com.yilan.sdk.YLInit;
import com.yilan.sdk.ui.YLUIInit;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        YLUIInit.getInstance()
                .setApplication(this)
                .setAccessKey("ylel2vek386q")
                .setAccessToken("talb5el4cbw3e8ad3jofbknkexi1z8r4")
                .setUid("userId")
                .setWebStyle(2)
                .build();
    }
}
