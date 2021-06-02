package com.yinp.proapp.module.wanandroid.activity;

import android.os.Bundle;
import android.view.View;

import com.yinp.proapp.base.activity.PresenterBaseActivity;
import com.yinp.proapp.databinding.ActivityWanSysBinding;
import com.yinp.proapp.module.wanandroid.WanManager;
import com.yinp.proapp.utils.StatusBarUtil;

/**
 * 从体系过来得
 */
public class WanSysActivity extends PresenterBaseActivity<ActivityWanSysBinding, WanManager> {

    @Override
    protected WanManager createPresenter() {
        return new WanManager(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext));
        initClick(this, bd.header.headerBackImg);
        bindData();
    }

    @Override
    protected void bindData() {
        super.bindData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == bd.header.headerBackImg) {
            finish();
        }
    }
}