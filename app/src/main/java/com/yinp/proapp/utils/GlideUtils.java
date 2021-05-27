package com.yinp.proapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.yinp.proapp.R;

import java.io.File;

public class GlideUtils {
    public static void into(Context context, String url, ImageView iv, int error) {
        try {
            if (url == null) {
                url = "";
            }
            File file = new File(url);
            if (file.exists()) {
                Glide.with(context).load(file).error(error).placeholder(R.drawable.shape_gray_8).into(iv);
            } else {
                Glide.with(context).load(url).error(error).placeholder(R.drawable.shape_gray_8).into(iv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void into(Context context, int url, ImageView iv) {
        try {
            Glide.with(context).load(url).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void intoRadius(Context context, String url, ImageView iv, int error, int radius) {
        try {
            if (url == null) {
                url = "";
            }
            File file = new File(url);
            if (file.exists()) {
                Glide.with(context).load(file).transform(new CenterCrop(), new GlideRoundTransform(radius)).placeholder(R.drawable.shape_gray_8).error(error).into(iv);
            } else {
                Glide.with(context).load(url).transform(new CenterCrop(), new GlideRoundTransform(radius)).placeholder(R.drawable.shape_gray_8).error(error).into(iv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void intoRadius(Context context, int url, ImageView iv, int radius) {
        try {
            Glide.with(context).load(url).transform(new CenterCrop(), new GlideRoundTransform(radius)).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void intoRadiusNormal(Context context, String url, ImageView iv, int error, int radius) {
        try {
            if (url == null) {
                url = "";
            }
            File file = new File(url);
            if (file.exists()) {
                Glide.with(context).load(file).transform(new GlideRoundTransform(radius)).placeholder(R.drawable.shape_gray_8).error(error).into(iv);
            } else {
                Glide.with(context).load(url).transform(new GlideRoundTransform(radius)).placeholder(R.drawable.shape_gray_8).error(error).into(iv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void intoRadiusNormal(Context context, int url, ImageView iv, int radius) {
        try {
            Glide.with(context).load(url).transform(new GlideRoundTransform(radius)).into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * isCircle和fitCenter不能同时为true，否则没有圆形
     * @param context
     * @param imageView
     * @param placeImage
     * @param url
     * @param transformation
     * @param listener
     * @param isCircle
     * @param fitCenter
     */
    public static void loadUrl(Context context, ImageView imageView, Drawable placeImage, String url, Transformation transformation, final GlideLoadListener listener
            , boolean isCircle, boolean fitCenter) {
        if (imageView == null) {
            return;
        }
        RequestOptions options;
        if (isCircle) {
//            options = RequestOptions.bitmapTransform(new CircleTransform());
            options = RequestOptions.bitmapTransform(new CircleCrop());
        } else {
            options = new RequestOptions();
        }
        options = options.format(DecodeFormat.PREFER_ARGB_8888);
        if (transformation != null) {
            if (imageView.getScaleType() == ImageView.ScaleType.CENTER_CROP)
                options = options.transforms(new CenterCrop(), transformation);
            else
                options = options.transforms(transformation);
        }
        if (fitCenter)
            options.fitCenter();
        if (placeImage != null) {
            options = options.error(placeImage);
            options = options.placeholder(placeImage);
            options = options.fallback(placeImage);
        }
        options = options.disallowHardwareConfig();
        Glide.with(imageView).load(url).apply(options).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                if (listener != null) {
                    listener.failed();
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (listener != null) {
                    listener.success();
                    listener.success(resource);
                }
                return false;
            }
        }).into(imageView);
    }

    public interface GlideLoadListener {
        void failed();

        void success();

        void success(Drawable drawable);
    }
}
