package com.may.base.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.may.base.R;


/**
 * Created by Administrator on 2016/10/17.
 */
public class StatusSwitchView extends FrameLayout {

    private View view_switch_loading, view_switch_empty, view_switch_net_error;
    private TextView tvTips;

    public StatusSwitchView(Context context) {
        this(context, null);
    }

    public StatusSwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        view_switch_loading = LayoutInflater.from(context).inflate(R.layout.page_state_loading, this, false);
        view_switch_empty = LayoutInflater.from(context).inflate(R.layout.page_state_empty, this, false);
        view_switch_net_error = LayoutInflater.from(context).inflate(R.layout.page_state_error, this, false);

        tvTips = view_switch_empty.findViewById(R.id.tv_tips);
        addView(view_switch_net_error);
        addView(view_switch_empty);
        addView(view_switch_loading);

        view_switch_empty.setVisibility(View.GONE);
        view_switch_net_error.setVisibility(View.GONE);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //屏蔽下层实体布局的点击事件
            }
        });

    }


    public void setOnRefreshClickListener(OnClickListener onRefreshClickListener) {
        view_switch_net_error.setOnClickListener(onRefreshClickListener);
    }


    public void setEmptyViewText(String text) {
        tvTips.setText(text);
    }

    public void showEmptyView(boolean needAnimation, String emptyText) {
        setEmptyViewText(emptyText);
        showEmptyView(needAnimation);
    }

    public void showEmptyView(boolean needAnimation) {

        this.setVisibility(View.VISIBLE);
        view_switch_net_error.setVisibility(View.GONE);
        if (needAnimation) {
            hideAnimation(view_switch_loading);
        } else {
            view_switch_loading.setVisibility(View.GONE);
        }

        view_switch_empty.setVisibility(View.VISIBLE);
    }


    public void showErrorView(boolean needAnimation) {
        // pb_progressbar.stopAnimation();
        this.setVisibility(View.VISIBLE);
        view_switch_empty.setVisibility(View.GONE);
        if (needAnimation) {
            hideAnimation(view_switch_loading);
        } else {
            view_switch_loading.setVisibility(View.GONE);

        }
        view_switch_net_error.setVisibility(View.VISIBLE);

    }

    public void showLoadingView() {
        // pb_progressbar.showAnimation();
        this.setVisibility(View.VISIBLE);
        view_switch_net_error.setVisibility(View.GONE);
        view_switch_empty.setVisibility(View.GONE);
        view_switch_loading.setVisibility(View.VISIBLE);
    }

    public void setGoneAnimation() {
        hideAnimation(this);
    }


    public void hideAnimation(final View view) {
        if (view.getVisibility() == View.GONE) {
            return;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        objectAnimator.setDuration(500);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                view.setAlpha(1);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }


}
