package com.yinp.proapp.module.wanandroid.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.fragment.app.Fragment;

import com.yinp.proapp.R;
import com.yinp.proapp.base.activity.AppBaseFragmentActivity;
import com.yinp.proapp.databinding.ActivityWandroidBinding;
import com.yinp.proapp.module.wanandroid.fragment.WanHomeFragment;
import com.yinp.proapp.utils.StatusBarUtil;
import com.yinp.proapp.view.viewpager2.SimplePagerTitlePictureView;
import com.yinp.proapp.view.viewpager2.ViewPager2Utils;
import com.yinp.tools.fragment_dialog.CommonDialogFragment;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 玩Android总页面
 */
public class WandroidActivity extends AppBaseFragmentActivity<ActivityWandroidBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(this));
        bd.header.headerCenterTitle.setText("玩Android");
        bd.header.headerEnd.setImageResource(R.mipmap.common_software);
        initClick(this, bd.header.headerBackImg, bd.header.headerEnd);
        initIndicator();
    }

    private SparseArray<Fragment> fragments = new SparseArray<>();
    private WanHomeFragment wanHomeFragment;

    private void initIndicator() {
        wanHomeFragment = WanHomeFragment.getInstance();
        fragments.put(0, WanHomeFragment.getInstance());
        fragments.put(1, WanHomeFragment.getInstance());
        fragments.put(2, WanHomeFragment.getInstance());
        fragments.put(3, WanHomeFragment.getInstance());
        fragments.put(4, WanHomeFragment.getInstance());
        fragments.put(5, WanHomeFragment.getInstance());
        fragments.put(6, WanHomeFragment.getInstance());
        fragments.put(7, WanHomeFragment.getInstance());
        bd.materialViewPager.setAdapter(ViewPager2Utils.getAdapter(this, fragments));

        List<String> titleList = new ArrayList<>(Arrays.asList("首页", "广场", "导航", "问答", "体系", "项目", "公众号", "项目分类"));
        bd.materialIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator7 = new CommonNavigator(mContext);
        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titleList == null ? 0 : titleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitlePictureView simplePagerTitleView = new SimplePagerTitlePictureView(context);
                simplePagerTitleView.setText(titleList.get(index));
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.b8b8b8));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.ff4d4d));
                simplePagerTitleView.setOnClickListener(v -> bd.materialViewPager.setCurrentItem(index, false));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 0));
                indicator.setLineWidth(UIUtil.dip2px(context, 56));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(getResources().getColor(R.color.ff4d4d));
                return indicator;
            }
        });
        bd.materialIndicator.setNavigator(commonNavigator7);
        ViewPager2Utils.bind(bd.materialIndicator, bd.materialViewPager);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == bd.header.headerBackImg) {
            finish();
        }
    }
    private void setCommonWebDialog(){
//        CommonDialogFragment.newInstance(this).setLayoutId(R.layout)
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewPager2Utils.unBind(bd.materialViewPager);
    }
}