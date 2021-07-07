package com.yinp.proapp.module.wanandroid.activity;

import com.yinp.proapp.base.activity.AppBaseFragmentActivity;
import com.yinp.proapp.databinding.ActivitySettingBinding;
import com.yinp.proapp.utils.StatusBarUtil;

/**
 * 设置界面
 */
public class SettingActivity extends AppBaseFragmentActivity<ActivitySettingBinding> {

    @Override
    protected void initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext));
        initClick(this,bd.sllLoginOut);
        bd.header.headerCenterTitle.setText("系统设置");
    }

}