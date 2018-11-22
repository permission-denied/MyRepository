package com.may.base.view.textview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @date 2018/4/17
 * @auther Limei
 * @description
 */

public class DrawableTextView extends android.support.v7.widget.AppCompatTextView {

    private enum DIRECTION {
        LEFT, TOP, RIGHT, BOTTOM
    }

    public DrawableClickListener drawableClickListener;


    public DrawableTextView(Context context) {
        this(context, null);
    }

    public DrawableTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setClickable(true);
    }

    public void changeDrawable(int resId) {
        changeDrawable(resId, getDrawableDirection());
    }

    public void changeDrawable(int resId, DIRECTION direction) {
        assertHasOneDrawable();
        setDrawables(resId, direction);
    }

    private void setDrawables(int resId, DIRECTION direction) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), resId);
        int drawableWidth = drawable.getMinimumWidth();
        int drawableHeight = drawable.getMinimumHeight();
        drawable.setBounds(0, 0, drawableWidth, drawableHeight);
        if (direction == DIRECTION.LEFT) {
            setCompoundDrawables(drawable, null, null, null);
        } else if (direction == DIRECTION.TOP) {
            setCompoundDrawables(null, drawable, null, null);
        } else if (direction == DIRECTION.RIGHT) {
            setCompoundDrawables(null, null, drawable, null);
        } else if (direction == DIRECTION.BOTTOM) {
            setCompoundDrawables(null, null, null, drawable);
        }
    }

    /**
     * 获取drawable的方向
     *
     * @return
     */
    private DIRECTION getDrawableDirection() {
        assertHasOneDrawable();
        Drawable[] drawables = getCompoundDrawables();
        if (drawables[0] != null) {
            return DIRECTION.LEFT;
        } else if (drawables[1] != null) {
            return DIRECTION.TOP;
        } else if (drawables[2] != null) {
            return DIRECTION.RIGHT;
        } else {
            return DIRECTION.BOTTOM;
        }
    }

    public void setDrawableClick(DrawableClickListener drawableClickListener) {
        this.drawableClickListener = drawableClickListener;
    }

    public interface DrawableClickListener {
        void onDrawableClick(View view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (drawableClickListener != null) {

                    Drawable[] drawables = getCompoundDrawables();

                    if (getNotNullElementCount(drawables) == 0) {
                        return super.onTouchEvent(event);
                    }

                    assertHasOneDrawable();

                    Drawable leftDrawable = drawables[0];
                    if (leftDrawable != null && event.getX() <= leftDrawable.getBounds().width()) {
                        drawableClickListener.onDrawableClick(this);
                        return false;
                    }

                    Drawable topDrawable = drawables[1];
                    if (topDrawable != null && event.getY() <= topDrawable.getBounds().height()) {
                        drawableClickListener.onDrawableClick(this);
                        return false;
                    }

                    Drawable rightDrawable = getCompoundDrawables()[2];
                    if (rightDrawable != null && event.getX() >= (getRight() - getLeft() - rightDrawable.getBounds().width())) {
                        drawableClickListener.onDrawableClick(this);
                        return false;
                    }

                    Drawable bottomDrawable = getCompoundDrawables()[3];
                    if (bottomDrawable != null && event.getY() >= (getBottom() - getTop() - bottomDrawable.getBounds().height())) {
                        drawableClickListener.onDrawableClick(this);
                        return false;
                    }
                }
                break;

        }
        return super.onTouchEvent(event);

    }

    /**
     * 获取drawable的数量
     *
     * @param drawables
     * @return
     */
    private int getNotNullElementCount(Drawable[] drawables) {
        int notNullElementCount = 0;
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                notNullElementCount++;
            }
        }
        return notNullElementCount;
    }

    /**
     * 断言只有一个Drawable
     */
    private void assertHasOneDrawable() {
        int drawableCount = getNotNullElementCount(getCompoundDrawables());

        if (drawableCount == 0) {
            throw new RuntimeException("not has any direction drawable,so you should use TextView ");
        }

        if (getNotNullElementCount(getCompoundDrawables()) > 1) {
            throw new RuntimeException("can only set one drawable,leftDrawble or topDrawable or rightDrawable or bottomDrawable");
        }
    }
}
