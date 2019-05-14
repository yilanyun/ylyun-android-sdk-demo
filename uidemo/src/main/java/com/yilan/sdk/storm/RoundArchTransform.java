package com.yilan.sdk.storm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import java.security.MessageDigest;

/**
 * Created by lihu on 2017/12/18.
 * 带圆角的矩形图片
 * <p> type：
 * 左上 1
 * 右上 2
 * 左下 4
 * 右下 8
 */

public class RoundArchTransform implements Transformation<Bitmap> {
    public static final int LEFT_TOP = 0x1;
    public static final int RIGHT_TOP = 0x2;
    public static final int LEFT_DOWN = 0x4;
    public static final int RIGHT_DOWN = 0x8;
    public static final int ALL = LEFT_TOP | RIGHT_TOP | LEFT_DOWN | RIGHT_DOWN;


    private final BitmapPool mBitmapPool;
    private int roundWidth = 20;
    private int roundHeight = 20;
    private Paint mPathPaint;
    private int width;
    private int height;
    private int mType = ALL;

    public RoundArchTransform(Context context) {
        this(Glide.get(context).getBitmapPool());
    }

    public RoundArchTransform(BitmapPool pool) {
        this.mBitmapPool = pool;
    }

    public RoundArchTransform setType(int type) {
        this.mType = type;
        return this;
    }


    private void drawLeftTop(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, roundHeight);
        path.lineTo(0, 0);
        path.lineTo(roundWidth, 0);
        path.arcTo(new RectF(0, 0, roundWidth * 2, roundHeight * 2), 270, -90);
        path.close();
        canvas.drawPath(path, mPathPaint);
    }

    private void drawLeftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, height - roundHeight);
        path.lineTo(0, height);
        path.lineTo(roundWidth, height);
        path.arcTo(new RectF(0, height - roundHeight * 2, roundWidth * 2, height), 90, 90);
        path.close();
        canvas.drawPath(path, mPathPaint);
    }

    private void drawRightDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(width - roundWidth, height);
        path.lineTo(width, height);
        path.lineTo(width, height - roundHeight);
        path.arcTo(new RectF(width - roundWidth * 2, height - roundHeight * 2, width, height), 0, 90);
        path.close();
        canvas.drawPath(path, mPathPaint);
    }

    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(width, roundHeight);
        path.lineTo(width, 0);
        path.lineTo(width - roundWidth, 0);
        path.arcTo(new RectF(width - roundWidth * 2, 0, width, 0 + roundHeight * 2), 270, 90);
        path.close();
        canvas.drawPath(path, mPathPaint);
    }

    @NonNull
    @Override
    public Resource<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();
        width = source.getWidth();
        height = source.getHeight();

        mPathPaint = new Paint();
        mPathPaint.setAntiAlias(true);
        mPathPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        Bitmap bitmap = this.mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        BitmapShader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        Rect mSrcRect = new Rect(0, 0, source.getWidth(), source.getHeight());
        canvas.drawRect(mSrcRect, paint);

        if ((mType & LEFT_TOP) == LEFT_TOP) {
            drawLeftTop(canvas);
        }

        if ((mType & LEFT_DOWN) == LEFT_DOWN) {
            drawLeftDown(canvas);
        }

        if ((mType & RIGHT_TOP) == RIGHT_TOP) {
            drawRightUp(canvas);
        }

        if ((mType & RIGHT_DOWN) == RIGHT_DOWN) {
            drawRightDown(canvas);
        }
        return BitmapResource.obtain(bitmap, this.mBitmapPool);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
