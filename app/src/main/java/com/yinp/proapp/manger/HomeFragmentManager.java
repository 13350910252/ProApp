package com.yinp.proapp.manger;

import android.content.Context;

import com.yinp.proapp.module.wanandroid.web.retrofit.WanObserver;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanData;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanBuildRetrofit;
import com.yinp.proapp.web.retrofit.BaseObserver;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeFragmentManager extends BaseManager {
    private Context context;

    public HomeFragmentManager(Context context) {
        this.context = context;
    }
    /**
     * 获取顶部banner
     * @param baseObserver
     */
    public void getBannerList(WanObserver<WanData> baseObserver) {
        addDisposable(WanBuildRetrofit.getInstance().getWanApiRetrofit().getBannerList(), baseObserver);
    }
}
