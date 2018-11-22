package com.may.base.view.pageIndicator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by may on 2018/9/11.
 */

public abstract class BasePagerAdapter<T> extends PagerAdapter {
    protected List<T> bannerDatas;

    protected Context mContext;

    public BasePagerAdapter(List<T> bannerDatas, Context context) {
        this.bannerDatas = bannerDatas;
        this.mContext = context;
    }

    /**
     * set banner datas
     *
     * @param bannerDatas
     */
    public void setBannerDatas(List<T> bannerDatas) {
        this.bannerDatas = bannerDatas;
    }

    public List<T> getBannerDatas() {
        return this.bannerDatas;
    }

    public int getRealBannerCount() {
        return bannerDatas == null ? 0 : bannerDatas.size();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
