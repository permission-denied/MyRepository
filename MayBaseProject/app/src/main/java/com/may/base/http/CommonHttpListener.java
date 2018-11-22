package com.may.base.http;

import android.content.Context;

public abstract class CommonHttpListener<T> implements OnHttpListener<T> {

    private Context context;

    public CommonHttpListener(Context context){
        this.context = context;
    }



    @Override
    public void onError(ExceptionHandle.ResponeThrowable e) {
        /*if (!TextUtils.isEmpty(e.message)){
            ToastUtils.showShortToast(e.message);
        }
        if (context != null && "9999".equals(e.code)){
            UserManage.logout();
            GotoUtils.gotoMain(context);
            EventBus.getDefault().post(new CommonEvent(Constants.MSG_ACCESS_TOKEN_VALIDE));
            EventBus.getDefault().post(new CommonEvent(Constants.MSG_LOGIN_OUT));
        }*/
    }

}
