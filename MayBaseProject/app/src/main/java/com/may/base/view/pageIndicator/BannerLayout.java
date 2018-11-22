package com.may.base.view.pageIndicator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.may.base.view.viewpager.AutoScrollViewPager;

import java.util.List;


/**
 * Created by may on 2018/8/29.
 */

public class BannerLayout extends FrameLayout implements ViewPager.OnPageChangeListener {

    public static final int INITIAL_ITEM_INDEX = 500;

    private IPageIndicator pageIndicatorView;
    private PageIndicatorHelper pageIndicatorHelper;
    private AutoScrollViewPager autoScrollViewPager;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private BasePagerAdapter basePagerAdapter;

    public BannerLayout(@NonNull Context context) {
        this(context, null);
    }

    public BannerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        autoScrollViewPager = new AutoScrollViewPager(getContext());

        addView(autoScrollViewPager, 0,
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        autoScrollViewPager.setOnPageChangeListener(this);

        pageIndicatorView = findPageIndicatorView(this);

        if (pageIndicatorView != null) {
            pageIndicatorHelper = pageIndicatorView.getViewPagerHelper();
        }
    }

    private IPageIndicator findPageIndicatorView(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView instanceof IPageIndicator) {
                return (IPageIndicator) childView;
            } else if (childView instanceof ViewGroup) {
                findPageIndicatorView((ViewGroup) childView);
            }
        }
        return null;
    }

    private void assertAdapterNotNull() {
        if (basePagerAdapter == null) {
            throw new RuntimeException("you should call setViewpagerAdatper method before this action");
        }
    }


    public void setViewpagerAdapter(BasePagerAdapter adapter) {
        if (autoScrollViewPager != null) {
            basePagerAdapter = adapter;
            autoScrollViewPager.setAdapter(adapter);
            autoScrollViewPager.setCurrentItem(INITIAL_ITEM_INDEX);
            if (basePagerAdapter.getRealBannerCount() == 0) {
                autoScrollViewPager.setForbidScroll(true);
            }
        }
    }

    public <T> void setBannerData(List<T> datas) {
        assertAdapterNotNull();
        if (datas == null) {
            return;
        }

        int size = datas.size();
        basePagerAdapter.setBannerDatas(datas);
        basePagerAdapter.notifyDataSetChanged();

        if (size <= 1) {
            autoScrollViewPager.setForbidScroll(true);
            autoScrollViewPager.stopAutoScroll();
        } else {
            autoScrollViewPager.setForbidScroll(false);
            autoScrollViewPager.startAutoScroll();
        }
        if (pageIndicatorView != null) {
            pageIndicatorHelper.setIndicatorState(size,
                    autoScrollViewPager.getCurrentItem() % size);
        }
    }

    public void setPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    /**
     * set  banner scroll once time
     *
     * @param duration
     */
    public void setScrollOnceDuration(int duration) {
        autoScrollViewPager.setScrollOnceDuration(duration);
    }

    /**
     * set banner scroll interval
     *
     * @param interval
     */
    public void setAutoScrollInterval(int interval) {
        autoScrollViewPager.setInterval(interval);
    }

    /**
     * stop scroll
     */
    public void stopAutoScroll() {
        autoScrollViewPager.stopAutoScroll();
    }

    /**
     * start scroll
     */
    public void startAutoScroll() {
        autoScrollViewPager.startAutoScroll();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(position);
        }
        if (pageIndicatorHelper != null) {
            pageIndicatorHelper.onPageSelected(position);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}
