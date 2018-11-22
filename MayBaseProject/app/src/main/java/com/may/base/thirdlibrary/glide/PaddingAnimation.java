package com.may.base.thirdlibrary.glide;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.bumptech.glide.request.animation.GlideAnimation;

/**
 * Created by may on 2018/9/4.
 */

public class PaddingAnimation<T extends Drawable> implements GlideAnimation<T> {
    private final GlideAnimation realAnimation;

    public PaddingAnimation(GlideAnimation animation) {
        this.realAnimation=animation;
    }

    @Override
    public boolean animate(T current, final ViewAdapter adapter) {
        int width = current.getIntrinsicWidth();
        int height = current.getIntrinsicHeight();
        return realAnimation.animate(current, new PaddingViewAdapter(adapter, width, height));
    }

}
