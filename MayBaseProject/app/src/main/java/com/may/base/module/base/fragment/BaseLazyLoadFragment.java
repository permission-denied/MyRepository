package com.may.base.module.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * @date 2017/10/23
 * @auther Limei
 * @description
 */

public abstract class BaseLazyLoadFragment extends BaseLoadStatusFragment {
    private boolean mIsFirstVisible = true;

    private boolean isViewCreated = false;

    private boolean currentVisibleState = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isViewCreated) {
            if (isVisibleToUser && !currentVisibleState) {
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && currentVisibleState) {
                dispatchUserVisibleHint(false);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
        if (!isHidden() && getUserVisibleHint()) {
            dispatchUserVisibleHint(true);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!currentVisibleState && getUserVisibleHint()) {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (currentVisibleState && getUserVisibleHint()) {
            dispatchUserVisibleHint(false);
        }
    }

    /**
     * 统一处理 显示隐藏  做两件事
     * 设置当前 Fragment 可见状态 负责在对应的状态调用第一次可见和可见状态，不可见状态函数
     *
     * @param visible
     */
    private void dispatchUserVisibleHint(boolean visible) {
        //如果fragment的父fragment不可见，则不分发状态
        if (visible && isParentInvisible()) {
            return;
        }

        if (currentVisibleState == visible) {
            return;
        }

        currentVisibleState = visible;
        if (visible) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false;
                onFragmentFirstVisible();
            }
            onFragmentVisible();
        } else {
            onFragmentInvisible();
        }
        dispatchChildUserVisibleHint(visible);
    }

    //分发给子fragment
    private void dispatchChildUserVisibleHint(boolean visible) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> childFragments = childFragmentManager.getFragments();
        if (childFragments != null) {
            for (Fragment fragment : childFragments) {
                if (fragment instanceof BaseLazyLoadFragment && fragment.getUserVisibleHint() && !fragment.isHidden()) {
                    ((BaseLazyLoadFragment) fragment).dispatchUserVisibleHint(visible);
                }
            }
        }
    }

    private boolean isParentInvisible() {
        BaseLazyLoadFragment parentFragment = (BaseLazyLoadFragment) getParentFragment();
        return parentFragment != null && !parentFragment.isSupportVisible();
    }

    //fragment是否可见
    private boolean isSupportVisible() {
        return currentVisibleState;
    }

    /**
     * 该方法与 setUserVisibleHint 对应，调用时机是 show，hide 控制 Fragment 隐藏的时候，
     * 注意的是，只有当 Fragment 被创建后再次隐藏显示的时候才会调用，第一次 show 的时候是不会回调的。
     */
    /*@Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            dispatchUserVisibleHint(false);
        } else {
            dispatchUserVisibleHint(true);
        }
    }*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //当 View 被销毁的时候我们需要重新设置 isViewCreated mIsFirstVisible 的状态
        isViewCreated = false;
        mIsFirstVisible = true;
    }

    /**
     * 对用户第一次可见
     */
    public void onFragmentFirstVisible() {
        loadData();
    }


    /**
     * 对用户可见
     */
    public void onFragmentVisible() {

    }

    /**
     * 对用户不可见
     */
    public void onFragmentInvisible() {

    }
}
