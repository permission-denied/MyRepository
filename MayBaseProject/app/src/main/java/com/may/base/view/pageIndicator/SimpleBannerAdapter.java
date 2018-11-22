package com.may.base.view.pageIndicator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.may.base.R;

import java.util.List;

/**
 * Created by may on 2018/9/11.
 */

public class SimpleBannerAdapter<T> extends BasePagerAdapter<T> {

    public SimpleBannerAdapter(List<T> bannerDatas, Context context) {
        super(bannerDatas, context);
    }

    public void onBannerClick(T t, int realBannerPos, ImageView imageView) {

    }

    public void loadBannerImage(T t, int realBannerPos, ImageView imageView) {

    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        if (bannerDatas != null) {
            T t = null;
            int bannerSize = getRealBannerCount();
            if (bannerSize > 0) {
                t = bannerDatas.get(position % bannerSize);
            }

            int realPos = bannerSize == 0 ? 0 : position % bannerSize;
            loadBannerImage(t, realPos, imageView);

            final T finalT = t;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalT != null) {
                        onBannerClick(finalT, position % bannerDatas.size(), imageView);
                    }
                }
            });
        }

        container.addView(imageView);
        return imageView;
    }
}
