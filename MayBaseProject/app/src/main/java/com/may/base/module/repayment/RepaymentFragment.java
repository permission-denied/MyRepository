package com.may.base.module.repayment;

import android.view.View;
import android.widget.TextView;

import com.may.base.R;
import com.may.base.module.base.fragment.BaseLazyLoadFragment;
import com.may.base.utils.StatusBarUtils;

import butterknife.BindView;

/**
 * Created by may on 2018/9/13.
 */

public class RepaymentFragment extends BaseLazyLoadFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.virtualStatusBar)
    View virtualStatusBar;

    @Override
    protected int getLayout() {
        return R.layout.fragment_repayment;
    }

    @Override
    protected void initView(View view) {
        tvTitle.setText(getString(R.string.repayment));
        StatusBarUtils.setViewStatusBarHeight(virtualStatusBar);
    }

    @Override
    protected void loadData() {
        showContentView();
    }


}
