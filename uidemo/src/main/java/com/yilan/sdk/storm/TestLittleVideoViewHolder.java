package com.yilan.sdk.storm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yilan.sdk.entity.MediaInfo;
import com.yilan.sdk.ui.R;
import com.yilan.sdk.ui.littlevideo.LittleClickListener;
import com.yilan.sdk.ui.littlevideo.LittleVideoViewHolder;
import com.yilan.sdk.uibase.util.ImageLoader;

/**
 * 小视频item的ViewHolder
 */

public class TestLittleVideoViewHolder extends LittleVideoViewHolder {

    @Override
    public InnerViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.yl_item_videoview, parent, false);
        final InnerViewHolder holder = new InnerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final InnerViewHolder holder, int position, MediaInfo item) {
        if (item == null) return;
        holder.mediaInfo = item;
        holder.title.setText(item.getTitle());
        ImageLoader.loadWithDefault(holder.cover, item.getImage(), android.R.color.black);
        if (item.getProvider() != null) {
            holder.cpName.setVisibility(View.VISIBLE);
            holder.cpName.setText("@" + item.getProvider().getName());
        } else {
            holder.cpName.setVisibility(View.GONE);
        }

    }

}
