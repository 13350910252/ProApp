package com.yinp.proapp.fragment.bottompage;

import android.view.View;

import com.yinp.proapp.base.fragment.AppBaseFragment;
import com.yinp.proapp.databinding.FragmentStudyBinding;
import com.yinp.proapp.module.customview.activity.CustomViewActivity;
import com.yinp.proapp.module.wanandroid.activity.WandroidActivity;

/**
 * 学习的位置
 */
public class StudyFragment extends AppBaseFragment<FragmentStudyBinding> {
    @Override
    protected void initViews(View view) {
        initClick(this, bd.stvOne, bd.stvFour);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == bd.stvOne) {
            goToActivity(WandroidActivity.class);
        } else if (v == bd.stvFour) {
            goToActivity(CustomViewActivity.class);
        }
    }
}
