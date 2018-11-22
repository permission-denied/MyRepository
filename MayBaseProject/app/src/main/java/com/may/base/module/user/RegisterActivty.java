package com.may.base.module.user;

import com.may.base.R;
import com.may.base.module.base.activity.BaseActivity;
import com.may.base.utils.StatusBarUtils;

/**
 * Created by may on 2018/9/14.
 */

public class RegisterActivty extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        StatusBarUtils.transparentStatusBar(this);
    }
}
