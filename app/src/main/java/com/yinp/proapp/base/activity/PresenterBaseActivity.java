package com.yinp.proapp.base.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.yinp.proapp.base.fragment.AppBaseFragment;
import com.yinp.proapp.manger.BaseManager;

public abstract class PresenterBaseActivity<T extends ViewBinding, P extends BaseManager> extends AppBaseActivity<T> {
    protected P presenter;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
