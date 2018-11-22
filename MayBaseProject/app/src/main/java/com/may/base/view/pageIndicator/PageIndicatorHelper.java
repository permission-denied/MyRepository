package com.may.base.view.pageIndicator;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by may on 2018/8/29.
 */

public class PageIndicatorHelper {
    private int realPageCount;
    private int currentPosition;
    private PageIndicatorCallback pageIndicatorCallback;
    private View pageIndicatorView;


    public PageIndicatorHelper(View pageIndicatorView) {
        this.pageIndicatorView = pageIndicatorView;
    }

    public void setPageIndicatorCallback(PageIndicatorCallback pageIndicatorCallback) {
        this.pageIndicatorCallback = pageIndicatorCallback;
    }

    public void setRealPageCount(int realPageCount) {
        this.realPageCount = realPageCount;

        if (pageIndicatorCallback != null) {
            pageIndicatorCallback.setRealPageCount(realPageCount);
        } else if (pageIndicatorView != null) {
            pageIndicatorView.requestLayout();
        }
    }


    public void setCurrentPosition(int position) {
        this.currentPosition = position > realPageCount - 1 ? realPageCount - 1 : position;
        if (pageIndicatorCallback != null) {
            pageIndicatorCallback.setCurrentPosition(currentPosition);
        } else if (pageIndicatorView != null) {
            pageIndicatorView.invalidate();
        }
    }

    public int getRealPageCount() {
        return realPageCount;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void onPageSelected(int position) {
        if (realPageCount == 0) {
            return;
        }
        currentPosition = position % realPageCount;
        pageIndicatorView.invalidate();
    }

    public void setIndicatorState(int bannerSize) {
        setIndicatorState(bannerSize, 0);
    }

    public void setIndicatorState(int bannerSize, int indicatorViewCurPos) {
        if (bannerSize == realPageCount) {
            return;
        }

        if (bannerSize <= 1) {
            pageIndicatorView.setVisibility(View.GONE);
        } else {
            pageIndicatorView.setVisibility(View.VISIBLE);
            setRealPageCount(bannerSize);
            setCurrentPosition(indicatorViewCurPos);
        }
    }
}
