package com.yilan.sdk.storm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.yilan.sdk.entity.MediaInfo;
import com.yilan.sdk.ui.YLUIInit;
import com.yilan.sdk.ui.configs.FeedConfig;

public class UIDemoApplication extends Application {
    private String TAG = UIDemoApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化
        YLUIInit.getInstance()
                .setApplication(this)
                .setAccessKey("")
                .setAccessToken("")
                .setUid("uisdk id")
                .build();

        //Feed流页面配置
//        FeedConfig.getInstance()
//                .setPlayerStyle(FeedConfig.STYLE_NATIVE)
//                .setViewHolder(new FeedStyleViewHolder())
//                .setOnItemClickListener(new FeedConfig.OnClickListener() {
//                    @Override
//                    public boolean onClick(Context context, MediaInfo info) {
//                        Log.e(TAG, "点击了 " + info.getH5_url());
//                        WebActivity.start(context, info.getH5_url(),"");
//                        return false;
//                    }
//                });
        //播放页配置
//        PlayerConfig.getInstance().setPlayerStyle(PlayerConfig.STYLE_NATIVE_FEED);
        //小视频页面配置
//        LittleVideoConfig.getInstance()
//                .setViewHolder(new TestLittleVideoViewHolder())
//                .setPlayerCallback(new UserCallback() {
//                    @Override
//                    public void event(int type, PlayData data, int playerHash) {
//                        switch (type) {
//                            case Constant.STATE_PREPARED:
//                            case Constant.STATE_ERROR:
//                            case Constant.STATE_PLAYING:
//                            case Constant.STATE_COMPLETE:
//                            case Constant.STATE_PAUSED:
//                                Log.e(TAG, "播放器状态" + type);
//                                break;
//                        }
//                    }
//                });

    }
}
