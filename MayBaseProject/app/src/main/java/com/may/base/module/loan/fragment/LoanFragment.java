package com.may.base.module.loan.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.may.base.R;
import com.may.base.bean.TestBannerBean;
import com.may.base.http.CommonHttpListener;
import com.may.base.http.ExceptionHandle;
import com.may.base.http.HttpManager;
import com.may.base.http.HttpUtils;
import com.may.base.http.bean.TestResponseBean;
import com.may.base.module.base.fragment.BaseLazyLoadFragment;
import com.may.base.module.user.RegisterActivty;
import com.may.base.utils.GlideUtils;
import com.may.base.utils.GotoUtils;
import com.may.base.view.pageIndicator.BannerLayout;
import com.may.base.view.pageIndicator.SimpleBannerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by may on 2018/9/13.
 */

public class LoanFragment extends BaseLazyLoadFragment {

    @BindView(R.id.bannerLayout)
    BannerLayout bannerLayout;
    @BindView(R.id.tv_repay_amount)
    TextView tvRepayAmount;
    @BindView(R.id.bt_apply_now)
    Button btApplyNow;

    private boolean temp = true;
    private List<TestBannerBean> testBannerBeans = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.fragment_loan_new;
    }

    @Override
    protected void initView(View view) {
        bannerLayout.setViewpagerAdapter(new SimpleBannerAdapter<TestBannerBean>(testBannerBeans, getContext()) {
            public void loadBannerImage(TestBannerBean testBannerBean, int realBannerPos, ImageView imageView) {
                String url = "";
                if (testBannerBean != null) {
                    url = testBannerBean.getBook_cover();
                }
                GlideUtils.loadImageCrossFade(getContext(), url, imageView, R.mipmap.ic_default_banner);
            }

            @Override
            public void onBannerClick(TestBannerBean testBannerBean, int realBannerPos, ImageView imageView) {
                //ToastUtils.showSingleToast("position: " + realBannerPos);
                Toast.makeText(getContext(), "realBannerPos:==" + realBannerPos, Toast.LENGTH_SHORT).show();
            }
        });
        tvRepayAmount.setText(getResources().getString(R.string.amount_payable_at_maturity, ""));


    }

    @Override
    protected void loadData() {
        getBannerData();
    }


    public void getBannerData() {
        HttpManager.post(this, HttpUtils.getBannerData(), new CommonHttpListener<TestResponseBean<List<TestBannerBean>>>(getContext()) {

            @Override
            public void onSuccess(TestResponseBean<List<TestBannerBean>> listTestResponseBean) {
                showContentView();
                if (listTestResponseBean.getData() != null && listTestResponseBean.getData().size() > 0) {
                    if (temp) {
                        listTestResponseBean.getData().add(getTestBannerBean());
                    }
                    bannerLayout.setBannerData(listTestResponseBean.getData());
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                super.onError(e);
            }
        });
    }


    public TestBannerBean getTestBannerBean() {
        TestBannerBean testBannerBean = new TestBannerBean();
        testBannerBean.setBook_cover("http://img5.imgtn.bdimg.com/it/u=115219824,4178496911&fm=15&gp=0.jpg");
        return testBannerBean;
    }


    @OnClick({R.id.bt_apply_now})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_apply_now:
                GotoUtils.gotoActivity(getContext(), RegisterActivty.class);
                break;
        }
    }

    @Override
    public void onFragmentInvisible() {
        if (bannerLayout != null) {
            bannerLayout.stopAutoScroll();
        }
    }

    @Override
    public void onFragmentVisible() {
        if (bannerLayout != null) {
            bannerLayout.startAutoScroll();
        }
    }
}
