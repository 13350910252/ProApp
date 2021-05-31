package com.yinp.proapp.fragment.bottompage;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.yinp.proapp.R;
import com.yinp.proapp.adapter.HomeBannerAdapter;
import com.yinp.proapp.base.fragment.PresenterBaseFragment;
import com.yinp.proapp.bean.BannerEntity;
import com.yinp.proapp.databinding.FragmentHomeBinding;
import com.yinp.proapp.fragment.LabelFragment;
import com.yinp.proapp.fragment.TodayUndeterminedFragment;
import com.yinp.proapp.manger.HomeFragmentManager;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanObserver;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanData;
import com.yinp.proapp.utils.JumpWebUtils;
import com.yinp.proapp.view.viewpager2.SimplePagerTitlePictureView;
import com.yinp.proapp.view.viewpager2.ViewPager2Utils;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends PresenterBaseFragment<FragmentHomeBinding, HomeFragmentManager> {
    private HomeBannerAdapter bannerAdapter;
    private List<BannerEntity> listBanner = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        initRecycler();
        initIndicator();
        getBannerList();
    }

    private void initRecycler() {
        bannerAdapter = new HomeBannerAdapter(listBanner, getContext());
        bd.topBanner.setAdapter(bannerAdapter).addBannerLifecycleObserver(this).setIndicator(new CircleIndicator(getContext()));
        bd.topBanner.setOnBannerListener((OnBannerListener<BannerEntity>) (data, position) -> {
            JumpWebUtils.startWebView(getContext(), data.getTitle(), data.getUrl());
        });
    }

    @Override
    protected HomeFragmentManager createPresenter() {
        return new HomeFragmentManager(getContext());
    }

    private TodayUndeterminedFragment todayUndeterminedFragment;
    private LabelFragment labelFragment;
    private SparseArray<Fragment> fragments = new SparseArray<>();

    private void initIndicator() {
        todayUndeterminedFragment = TodayUndeterminedFragment.getInstance();
        labelFragment = LabelFragment.getInstance();
        fragments.put(0, todayUndeterminedFragment);
        fragments.put(1, labelFragment);
        bd.materialViewPager.setAdapter(ViewPager2Utils.getAdapter(this, fragments));

        List<String> titleList = new ArrayList<>(Arrays.asList("今日待做", "标签"));
        bd.materialIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator7 = new CommonNavigator(getContext());
        commonNavigator7.setAdjustMode(true);
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
                switch (index) {
                    case 0:
                        simplePagerTitleView.setmNormalDrawable(R.mipmap.task);
                        simplePagerTitleView.setmSelectedDrawable(R.mipmap.task_s);
                        break;
                    case 1:
                        simplePagerTitleView.setmNormalDrawable(R.mipmap.label);
                        simplePagerTitleView.setmSelectedDrawable(R.mipmap.label_s);
                        break;
                }
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
                indicator.setColors(getResources().getColor(R.color.fafafa));
                return indicator;
            }
        });
        bd.materialIndicator.setNavigator(commonNavigator7);
        ViewPager2Utils.bind(bd.materialIndicator, bd.materialViewPager);
    }

    private void getBannerList() {
        showLoading("加载中");
        presenter.getBannerList(new WanObserver<WanData>() {
            @Override
            public void onSuccess(WanData o) {
                hideLoading();
                if (o == null) {
                    return;
                }
                JsonElement jsonElement = o.getData();
                if (jsonElement.isJsonNull()) {
                    return;
                }
                Type type = new TypeToken<ArrayList<BannerEntity>>() {
                }.getType();
                listBanner = new Gson().fromJson(jsonElement, type);
                bannerAdapter.setDatas(listBanner);
                bannerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg) {
                hideLoading();
            }

            @Override
            public void onCodeFail(String msg) {
                hideLoading();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewPager2Utils.unBind(bd.materialViewPager);
    }
}
