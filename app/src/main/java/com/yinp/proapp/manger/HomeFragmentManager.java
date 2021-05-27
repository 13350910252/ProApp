package com.yinp.proapp.manger;

import android.content.Context;

import com.yinp.proapp.web.retrofit.BaseObserver;
import com.yinp.proapp.web.retrofit.BaseRetrofitData;
import com.yinp.proapp.web.retrofit.BuildRetrofit;

public class HomeFragmentManager extends BaseManager {
    private Context context;

    public HomeFragmentManager(Context context) {
        this.context = context;
    }

    /**
     * 获取顶部banner
     * @param baseObserver
     */
    public void getBannerList(BaseObserver<BaseRetrofitData> baseObserver) {
        addDisposable(BuildRetrofit.getInstance(BuildRetrofit.BASE_URL)
                .getApiRetrofit().getBannerList(), baseObserver);
    }
}
