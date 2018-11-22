package com.may.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.may.base.http.HttpConstants;
import com.may.base.thirdlibrary.glide.GlideRoundTransform;
import com.may.base.thirdlibrary.glide.PaddingAnimation;


public class GlideUtils {
    public static final int DEFAULT_CROSS_FADE_DURATION = 1500;

    public static void loadImage(Context context, String url, ImageView image) {
        if (!isEnableLoad(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .crossFade(DEFAULT_CROSS_FADE_DURATION)
                .into(image);

    }

    public static void loadRoundCornerImage(Context context, String url, ImageView image, int radius, int defaultImgResId) {
        if (!isEnableLoad(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .placeholder(defaultImgResId)
                .error(defaultImgResId)
                .transform(new CenterCrop(context), new GlideRoundTransform(context, radius))
                .into(image);

    }

    public static void loadImageNoAnim(Context context, String url, ImageView image) {
        if (!isEnableLoad(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .into(image);
    }

    public static void loadImageNotCache(Context context, String url, ImageView image) {
        if (!isEnableLoad(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .crossFade(DEFAULT_CROSS_FADE_DURATION)
                .skipMemoryCache(true)   //验证码不缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(image);

    }

    public static void loadImage(Context context, String url, ImageView image, String cookie) {
        if (!isEnableLoad(context)) {
            return;
        }
        Glide.with(context)
                .load(getUrl(url, cookie))
                .crossFade(DEFAULT_CROSS_FADE_DURATION)
                .into(image);
    }


    public static void loadImage(Context context, String url, ImageView imageView, int defaultRes) {
        if (!isEnableLoad(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .placeholder(defaultRes)
                .error(defaultRes)
                .crossFade(DEFAULT_CROSS_FADE_DURATION)
                .into(imageView);
    }

    public static void loadImageCrossFade(Context context, String url, ImageView imageView, int defaultRes) {
        Glide.with(context)
                .load(url)
                .placeholder(defaultRes)
                .crossFade(DEFAULT_CROSS_FADE_DURATION)
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation animation) {
                        super.onResourceReady(resource, new PaddingAnimation(animation));
                    }
                });
    }

    public static void loadImageTarget(Context context, String url, final ImageView imageView, int defaultRes) {
        if (!isEnableLoad(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(defaultRes)
                .error(defaultRes)
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imageView.setImageBitmap(resource);
                    }
                });
    }

    public static void loadImageNotAnim(Context context, String url, ImageView imageView, int defaultRes) {
        if (!isEnableLoad(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .dontAnimate()
                .placeholder(defaultRes)
                .into(imageView);
    }

    public static void loadImageUseImgaeSize(final Context context, String url, final ImageView imageView) {
        //获取图片真正的宽高
        if (!isEnableLoad(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .asBitmap()//强制Glide返回一个Bitmap对象
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        params.width = width;
                        params.height = height;
                        if (params.width >= ScreenUtils.getScreenWidth()) {
                            params.width = ScreenUtils.getScreenWidth();
                            params.height = (int) (height * 1.0f * ScreenUtils.getScreenWidth() / width);
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        } else {
                            imageView.setScaleType(ImageView.ScaleType.MATRIX);
                        }
                        imageView.setLayoutParams(params);
                        imageView.setImageBitmap(bitmap);
                    }
                });
    }

    public static void load(Context context, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if (!isEnableLoad(context)) {
            return;
        }
        Glide.with(context).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
    }

    public static void loadDetailImg(Context context, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if (!isEnableLoad(context)) {
            return;
        }
        Glide.with(context).load(url).asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);
    }

    public static void loadLocalImage(Context context, int resId, ImageView iv) {
        if (!isEnableLoad(context)) {
            return;
        }

        Glide.with(context).load(resId).asBitmap().skipMemoryCache(true).into(iv);
    }


    public static void loadLocalImageFitCenter(Context context, int resId, ImageView iv) {
        if (!isEnableLoad(context)) {
            return;
        }
        if (iv.getScaleType() != ImageView.ScaleType.FIT_CENTER) {
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        Glide.with(context).load(resId).asBitmap().skipMemoryCache(true).into(iv);
    }

    public static void loadLocalImageCenterCrop(Context context, int resId, ImageView iv) {
        if (!isEnableLoad(context)) {
            return;
        }
        if (iv.getScaleType() != ImageView.ScaleType.CENTER_CROP) {
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        Glide.with(context).load(resId).asBitmap().skipMemoryCache(true).into(iv);
    }

    private static GlideUrl getUrl(String url) {
        if (TextUtils.isEmpty(url) || !url.contains("http")) {
            url = HttpConstants.BASE_URL + url;
        }
        return new GlideUrl(url);
    }

    private static GlideUrl getUrl(String url, String cookie) {
        if (TextUtils.isEmpty(url) || !url.contains("http")) {
            url = HttpConstants.BASE_URL + url;
        }
        return new GlideUrl(url, new LazyHeaders.Builder().addHeader("Cookie", cookie).build());
    }

    public static boolean isEnableLoad(Context context) {

        if (context == null) {
            return false;
        }
        if (context instanceof Activity && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && ((Activity) context).isDestroyed()) {
            return false;
        }

        return true;
    }

}
