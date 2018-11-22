package com.may.base.view.pageIndicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.may.base.R;
import com.may.base.utils.LogUtils;
import com.may.base.utils.ScreenUtils;

import java.util.logging.Logger;


/**
 * @date 2018/3/21
 * @auther Limei
 * @description
 */

public class SmallRectPageIndicator extends View implements IPageIndicator {

    private Paint rectPaint;
    private int rectHeight;
    private int unSelectedRectWidth;
    private int selectedRectWidth;
    private int selectedRectColor;
    private int unSelectedRectColor;

    private int spaceBetweenRect;//指示器之间的间距
    private PageIndicatorHelper pageIndicatorHelper;


    public SmallRectPageIndicator(Context context) {
        this(context, null);
    }

    public SmallRectPageIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmallRectPageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        pageIndicatorHelper = new PageIndicatorHelper(this);
        int defaultRectHeight = ScreenUtils.dpToPxInt(5);
        int defaultSelectedRectColor = Color.WHITE;
        int defaultUnselectedRectColor = Color.argb(255 / 2, 255, 255, 255);
        int defaultSelectedRectWidth = ScreenUtils.dpToPxInt(12);
        int defaultUnselectedRectWidth = ScreenUtils.dpToPxInt(8);
        int defaultSpaceBetweenRect = ScreenUtils.dpToPxInt(4);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SmallRectPageIndicator);
        rectHeight = ta.getDimensionPixelOffset(R.styleable.SmallRectPageIndicator_rectHeight, defaultRectHeight);
        selectedRectColor = ta.getColor(R.styleable.SmallRectPageIndicator_selectedRectColor, defaultSelectedRectColor);
        unSelectedRectColor = ta.getColor(R.styleable.SmallRectPageIndicator_unselectedRectColor, defaultUnselectedRectColor);
        selectedRectWidth = ta.getDimensionPixelOffset(R.styleable.SmallRectPageIndicator_selectedRectWidth, defaultSelectedRectWidth);
        unSelectedRectWidth = ta.getDimensionPixelOffset(R.styleable.SmallRectPageIndicator_unSelectedRectWidth, defaultUnselectedRectWidth);
        spaceBetweenRect = ta.getDimensionPixelOffset(R.styleable.SmallRectPageIndicator_spaceBetweenRect, defaultSpaceBetweenRect);
        ta.recycle();
        initPaint();
    }

    private void initPaint() {
        rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setColor(selectedRectColor);
        rectPaint.setStrokeWidth(rectHeight);
        rectPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int resultWithSize = 0;
        int resultHeithSize = 0;

        int realPageCount = pageIndicatorHelper.getRealPageCount();


        if (realPageCount != 0) {
            resultWithSize = (selectedRectWidth + (realPageCount - 1) * unSelectedRectWidth + (realPageCount - 1) * spaceBetweenRect);
            resultHeithSize = rectHeight;
        }

        setMeasuredDimension(resultWithSize, resultHeithSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < pageIndicatorHelper.getRealPageCount(); i++) {
            float currentDrawWidth;
            if (i > pageIndicatorHelper.getCurrentPosition()) {
                currentDrawWidth = i * (unSelectedRectWidth + spaceBetweenRect) + (selectedRectWidth - unSelectedRectWidth);
            } else {
                currentDrawWidth = i * (unSelectedRectWidth + spaceBetweenRect);
            }
            if (i == pageIndicatorHelper.getCurrentPosition()) {
                rectPaint.setColor(selectedRectColor);
                canvas.drawLine(currentDrawWidth, 0,
                        currentDrawWidth + selectedRectWidth, 0, rectPaint);
            } else {
                rectPaint.setColor(unSelectedRectColor);
                canvas.drawLine(currentDrawWidth, 0, currentDrawWidth + unSelectedRectWidth, 0, rectPaint);
            }
        }
    }


    @Override
    public PageIndicatorHelper getViewPagerHelper() {
        return pageIndicatorHelper;
    }

}
