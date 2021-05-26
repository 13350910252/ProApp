package com.yinp.proapp.activity;

import android.os.Bundle;
import android.view.View;

import com.yinp.proapp.base.activity.AppBaseActivity;
import com.yinp.proapp.databinding.ActivityLoginBinding;
import com.yinp.proapp.utils.StatusBarUtil;

/**
 * 登录界面
 */
public class LoginActivity extends AppBaseActivity<ActivityLoginBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext));
        initClick(this, bd.stvLogin);
    }

    @Override
    public void onClick(View v) {
        if (v == bd.stvLogin) {
            goToActivity(MajorActivity.class);
        }
    }
}