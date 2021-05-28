package com.yinp.proapp.fragment;

import android.os.Bundle;
import android.view.View;

import com.yinp.proapp.base.fragment.AppBaseFragment;
import com.yinp.proapp.databinding.FragmentTodayUndeterminedBinding;

/**
 * 今日待做页面
 */
public class TodayUndeterminedFragment extends AppBaseFragment<FragmentTodayUndeterminedBinding> {

    public static TodayUndeterminedFragment getInstance() {
        TodayUndeterminedFragment todayUndeterminedFragment = new TodayUndeterminedFragment();
        Bundle bundle = new Bundle();
        todayUndeterminedFragment.setArguments(bundle);
        return todayUndeterminedFragment;
    }

    @Override
    protected void initViews(View view) {
        initClick(this, bd.noData, bd.noTips);
        bd.noTips.setText("暂无待做任务，点击添加");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (bd.noData == v || bd.noTips == v) {
        }
    }
}
