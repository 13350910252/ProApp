package com.yinp.proapp.base.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.yinp.proapp.base.fragment.AppBaseFragment;
import com.yinp.proapp.manger.BaseManager;

public abstract class PresenterBaseFragmentActivity<T extends ViewBinding, P extends BaseManager> extends AppBaseFragmentActivity<T> {
    protected P presenter;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
