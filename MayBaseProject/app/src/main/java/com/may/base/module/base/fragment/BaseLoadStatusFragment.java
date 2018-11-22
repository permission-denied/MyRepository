package com.may.base.module.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.may.base.R;
import com.may.base.view.StatusSwitchView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @date 2018/5/16
 * @auther Limei
 * @description
 */

public abstract class BaseLoadStatusFragment extends BaseFragment {


    private Unbinder unbinder;
    protected boolean needLoadStatusLayout = true;
    protected StatusSwitchView statusSwitchView;

    protected abstract int getLayout();

    protected abstract void initView(View view);

    protected void initListener() {

    }

    protected abstract void loadData();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View resultView;
        View contentView = inflater.inflate(getLayout(), container, false);
        if (needLoadStatusLayout) {
            View parentView = inflater.inflate(R.layout.base_load_status_layout, container, false);
            addContentView(parentView, contentView);
            resultView = parentView;
        } else {
            resultView = contentView;
        }
        unbinder = ButterKnife.bind(this, resultView);
        initView(resultView);
        initListener();
        return resultView;
    }

    private void addContentView(View parentView, View contentView) {
        FrameLayout contentViewParent = parentView.findViewById(R.id.contentView);
        statusSwitchView = parentView.findViewById(R.id.statusSwitchView);
        statusSwitchView.setOnRefreshClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusSwitchView.showLoadingView();
                loadData();
            }
        });
        contentViewParent.addView(contentView);
    }


    protected void showContentView() {
        if (statusSwitchView != null) {
            statusSwitchView.setGoneAnimation();
        }
    }

    protected void showLoadingView() {
        if (statusSwitchView != null) {
            statusSwitchView.showLoadingView();
        }
    }

    protected void showEmptyView() {
        showEmptyView("");
    }

    protected void showEmptyView(String emptyText) {
        if (statusSwitchView != null) {
            if (!TextUtils.isEmpty(emptyText)) {
                statusSwitchView.setEmptyViewText(emptyText);
            }
            statusSwitchView.showEmptyView(true);
        }
    }

    protected void showErrorView() {
        if (statusSwitchView != null) {
            statusSwitchView.showErrorView(true);
        }
    }

    @Override
    public void onDetach() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDetach();
    }
}
