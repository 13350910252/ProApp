package com.yinp.proapp.base.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.yinp.proapp.manger.BaseManager;

public abstract class PresenterBaseFragment<T extends ViewBinding, P extends BaseManager> extends AppBaseFragment<T> {
    protected P presenter;

    protected abstract P createPresenter();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }
}
