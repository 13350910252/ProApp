package com.yinp.proapp.activity;

import android.os.Bundle;

import com.yinp.proapp.base.activity.AppBaseActivity;
import com.yinp.proapp.databinding.ActivityAddUndeterminedBinding;
import com.yinp.proapp.utils.StatusBarUtil;

/**
 * 添加待做的任务，或者其他的什么东西
 */
public class AddUndeterminedActivity extends AppBaseActivity<ActivityAddUndeterminedBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext));
    }
}