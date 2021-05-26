package com.yinp.proapp.activity;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;

import com.yinp.proapp.base.activity.AppBaseActivity;
import com.yinp.proapp.databinding.ActivitySplashBinding;
import com.yinp.proapp.utils.AppUtils;

/**
 * 启动页
 */
public class SplashActivity extends AppBaseActivity<ActivitySplashBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.setFullScreen(getWindow());
    }

    @Override
    protected void initViews() {
        bd.lavContent.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                goToActivity(LoginActivity.class);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}