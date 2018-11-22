package com.may.base.view.viewpager;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;


public class DurationScroller extends Scroller {

    public int duration = 1000;//default

    public void setScrollDuration(int duration) {
        this.duration = duration;
    }

    public DurationScroller(Context context) {
        super(context);
    }

    public DurationScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy,
                this.duration);
    }
}
