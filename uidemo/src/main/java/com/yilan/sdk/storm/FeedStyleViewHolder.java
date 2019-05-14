package com.yilan.sdk.storm;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.yilan.sdk.common.util.FSString;
import com.yilan.sdk.entity.MediaInfo;
import com.yilan.sdk.ui.feed.InnerViewHolder;
import com.yilan.sdk.ui.feed.MediaViewHolder;
import com.yilan.sdk.uibase.util.ImageLoader;

/**
 * Created by lihu on 2018/6/20.
 * 用户自定义样式
 */

public class FeedStyleViewHolder extends MediaViewHolder {
    private int width;

    public FeedStyleViewHolder() {
    }

    @Override
    public InnerViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        //这里的layout可以改成用户自定义的
        View view = inflater.inflate(R.layout.yl_item_media_style1, parent, false);
        final InnerViewHolder holder = new InnerViewHolder(view);

        if (width == 0) {
            width = parent.getWidth();
        }

        int height = width * 9 / 16;

        ViewGroup.LayoutParams params = holder.mMediaCoverIv.getLayoutParams();
        params.width = width;
        params.height = height;
        holder.mMediaCoverIv.setLayoutParams(params);
        return holder;
    }

    @Override
    public void onBindViewHolder(InnerViewHolder holder, int position, MediaInfo item) {
        if (item == null) return;
        //这里必须写，否则点击无效
        holder.mMediaInfo = item;

        holder.mMediaTitleTv.setText(item.getTitle());
        holder.mMediaTimeTv.setText(FSString.formatDuration(item.getDuration()));
        if (holder.mPlayCountDivideView != null) {
            holder.mPlayCountDivideView.setVisibility(View.GONE);//TODO
        }
        if (holder.mPlayCountTv != null) {
            holder.mPlayCountTv.setVisibility(View.GONE);
        }

        loadRoundArch(holder.mMediaCoverIv, item.getImage());

        if (item.getProvider() != null
                && !TextUtils.isEmpty(item.getProvider().getId())
                && !TextUtils.isEmpty(item.getProvider().getName())) {
            holder.mCpNameTv.setVisibility(View.VISIBLE);
            holder.mCpHeaderIv.setVisibility(View.VISIBLE);
            holder.mCpNameTv.setText(item.getProvider().getName());
            ImageLoader.loadCpRound(holder.mCpHeaderIv, item.getProvider().getAvatar());
        } else {
            holder.mCpNameTv.setVisibility(View.GONE);
            holder.mCpHeaderIv.setVisibility(View.GONE);
        }
        holder.mStillLayout.setVisibility(View.VISIBLE);
    }


    private static void loadRoundArch(ImageView iv, String url) {
        RoundArchTransform transform = new RoundArchTransform(iv.getContext());
        transform.setType(RoundArchTransform.LEFT_TOP | RoundArchTransform.RIGHT_TOP);
        RequestOptions options = new RequestOptions()
                .priority(Priority.LOW)
                .transform(transform)
                .error(R.drawable.yl_ui_bg_video_place_holder)
                .placeholder(R.drawable.yl_ui_bg_video_place_holder)
                .dontAnimate();
        Glide.with(iv.getContext())
                .load(url)
                .apply(options)
                .into(iv);
    }

}
