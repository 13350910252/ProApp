package com.yinp.proapp.module.customview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yinp.proapp.base.activity.AppBaseActivity;
import com.yinp.proapp.databinding.ActivityTestClipCircleBinding;
import com.yinp.proapp.utils.AppUtils;

public class TestClipCircleActivity extends AppBaseActivity<ActivityTestClipCircleBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.setFullScreen(getWindow());
    }

    @Override
    protected void initViews() {

    }
}