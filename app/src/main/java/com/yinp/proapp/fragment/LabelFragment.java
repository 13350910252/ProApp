package com.yinp.proapp.fragment;

import android.os.Bundle;
import android.view.View;

import com.yinp.proapp.base.fragment.AppBaseFragment;
import com.yinp.proapp.databinding.FragmentLabelBinding;

/**
 * 标签页面
 */
public class LabelFragment extends AppBaseFragment<FragmentLabelBinding> {

    public static LabelFragment getInstance() {
        LabelFragment todayUndeterminedFragment = new LabelFragment();
        Bundle bundle = new Bundle();
        todayUndeterminedFragment.setArguments(bundle);
        return todayUndeterminedFragment;
    }

    @Override
    protected void initViews(View view) {
        initClick(this, bd.noData, bd.noTips);
        bd.noTips.setText("暂无标签，点击添加");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (bd.noData == v || bd.noTips == v) {
        }
    }
}
