package com.yinp.proapp.fragment;

import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.yinp.proapp.adapter.HomeBannerAdapter;
import com.yinp.proapp.base.fragment.PresenterBaseFragment;
import com.yinp.proapp.bean.BannerEntity;
import com.yinp.proapp.databinding.FragmentHomeBinding;
import com.yinp.proapp.manger.HomeFragmentManager;
import com.yinp.proapp.web.retrofit.BaseObserver;
import com.yinp.proapp.web.retrofit.BaseRetrofitData;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends PresenterBaseFragment<FragmentHomeBinding, HomeFragmentManager> {
    private HomeBannerAdapter bannerAdapter;
    private List<BannerEntity> listBanner = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        initRecycler();
        getBannerList();
    }

    private void initRecycler() {
        bannerAdapter = new HomeBannerAdapter(listBanner, getContext());
        bd.topBanner.setAdapter(bannerAdapter).addBannerLifecycleObserver(this).setIndicator(new CircleIndicator(getContext()));
        bd.topBanner.setOnBannerListener((OnBannerListener<BannerEntity>) (data, position) -> {

        });
    }

    @Override
    protected HomeFragmentManager createPresenter() {
        return new HomeFragmentManager(getContext());
    }

    private void getBannerList() {
        showLoading("加载中");
        presenter.getBannerList(new BaseObserver<BaseRetrofitData>() {
            @Override
            public void onSuccess(BaseRetrofitData o) {
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
}
