package com.yinp.proapp.module.customview.activity;

import android.os.Bundle;
import android.view.View;

import com.yinp.proapp.base.activity.AppBaseActivity;
import com.yinp.proapp.databinding.ActivityCustomViewBinding;
import com.yinp.proapp.utils.StatusBarUtil;

/**
 * 自定义的view
 */
public class CustomViewActivity extends AppBaseActivity<ActivityCustomViewBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext));
    }

    @Override
    protected void initViews() {
        initClick(this, bd.tvOne, bd.header.headerBackImg);
        bd.header.headerCenterTitle.setText("自定义view");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == bd.tvOne) {
            goToActivity(TestClipCircleActivity.class);
        } else if (v == bd.header.headerBackImg) {
            finish();
        }
    }
}