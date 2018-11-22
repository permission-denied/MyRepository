package com.may.base.module.user;

import android.view.View;

import com.may.base.R;
import com.may.base.module.base.fragment.BaseLazyLoadFragment;

/**
 * Created by may on 2018/9/13.
 */

public class MyFragment extends BaseLazyLoadFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void loadData() {
        showContentView();
    }
}
