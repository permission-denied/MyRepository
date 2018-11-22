package com.may.base;

import android.support.v4.app.Fragment;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.may.base.module.base.activity.BaseActivity;
import com.may.base.module.loan.fragment.LoanFragment;
import com.may.base.module.other.MainViewPagerAdapter;
import com.may.base.module.repayment.RepaymentFragment;
import com.may.base.module.user.MyFragment;
import com.may.base.utils.StatusBarUtils;
import com.may.base.view.viewpager.NoScrollViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;

public class MainActivity extends BaseActivity {

    @BindView(R.id.noScrollViewPager)
    NoScrollViewPager noScrollViewPager;
    @BindView(R.id.rb_loan)
    RadioButton rbLoan;
    @BindView(R.id.rb_repayment)
    RadioButton rbRepayment;
    @BindView(R.id.rb_my)
    RadioButton rbMy;
    private List<Fragment> fragments = new ArrayList<>();

    public static final int PAGE_LOAN_INDEX = 0;
    public static final int PAGE_REPAYMENT_INDEX = 1;
    public static final int PAGE_MY_INDEX = 2;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        StatusBarUtils.transparentStatusBar(this);
        fragments.add(new LoanFragment());
        fragments.add(new RepaymentFragment());
        fragments.add(new MyFragment());
        noScrollViewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(), fragments));
        noScrollViewPager.setOffscreenPageLimit(2);

    }


    @Override
    protected void getData() {

    }


    @OnCheckedChanged({R.id.rb_loan, R.id.rb_repayment, R.id.rb_my})
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.rb_loan:
                if (isChecked) {
                    noScrollViewPager.setCurrentItem(PAGE_LOAN_INDEX, false);
                }
                break;
            case R.id.rb_repayment:
                if (isChecked) {
                    noScrollViewPager.setCurrentItem(PAGE_REPAYMENT_INDEX, false);
                }
                break;
            case R.id.rb_my:
                if (isChecked) {
                    noScrollViewPager.setCurrentItem(PAGE_MY_INDEX, false);
                }
                break;
        }
    }

}
