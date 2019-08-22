package com.yilan.sdk.storm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.yilan.sdk.entity.MediaInfo;
import com.yilan.sdk.player.UserCallback;
import com.yilan.sdk.player.entity.PlayData;
import com.yilan.sdk.player.utils.Constant;
import com.yilan.sdk.ui.YLUIInit;
import com.yilan.sdk.ui.ad.entity.AdEntity;
import com.yilan.sdk.ui.configs.AdVideoCallback;
import com.yilan.sdk.ui.configs.FeedConfig;
import com.yilan.sdk.ui.configs.LittleVideoConfig;
import com.yilan.sdk.ui.configs.PlayerConfig;
import com.yilan.sdk.uibase.ui.web.WebActivity;

public class UIDemoApplication extends Application {
    private String TAG = UIDemoApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化
        YLUIInit.getInstance()
                .setApplication(this)
//                .setUid("uisdk id")
                .build();

        //Feed流页面配置
        FeedConfig.getInstance()
                .setPlayerStyle(FeedConfig.STYLE_NATIVE);
//                .setViewHolder(new FeedStyleViewHolder())
//                .setOnItemClickListener(new FeedConfig.OnClickListener() {
//                    @Override
//                    public boolean onClick(Context context, MediaInfo info) {
//                        Log.e(TAG, "点击了 " + info.getH5_url());
//                        WebActivity.start(context, info.getH5_url(),"");
//                        return true;
//                    }
//                });
        //播放页配置
//        小视频页面配置
//        LittleVideoConfig.getInstance()
//                .setViewHolder(new TestLittleVideoViewHolder());


        //设置小视频的视频广告回调监听
        LittleVideoConfig.getInstance().setAdVideoCallback(new AdVideoCallback() {
            @Override
            public void onVideoLoad(AdEntity entity) {
                Log.d("adadad", "视频加载成功" + entity.getAdSlotId());
            }

            @Override
            public void onVideoError(int errorCode, AdEntity entity) {
                Log.d("adadad", "视频播放错误：errorCode=" + errorCode + entity.getAdSlotId());
            }

            @Override
            public void onVideoAdStartPlay(AdEntity entity) {
                Log.d("adadad", "视频开始播放" + entity.getAdSlotId());
            }

            @Override
            public void onVideoAdPaused(AdEntity entity) {
                Log.d("adadad", "视频暂停播放" + entity.getAdSlotId());
            }

            @Override
            public void onVideoAdContinuePlay(AdEntity entity) {
                Log.d("adadad", "视频继续播放" + entity.getAdSlotId());
            }
        });

        //设置评论类型
//        PlayerConfig.getInstance().setCommentType(PlayerConfig.DISMISS_COMMENT);
        PlayerConfig.getInstance().setCommentType(PlayerConfig.SHOW_COMMENT_LIST);
//        PlayerConfig.getInstance().setCommentType(PlayerConfig.SHOW_COMMENT_ALL);

    }
}
