package com.yinp.proapp.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.yinp.proapp.utils.StatusBarUtil;
import com.yinp.tools.utils.LoadUtils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AppBaseFragment<T extends ViewBinding> extends BaseFragment {
    //记录按下,防止连续点击
    public boolean isClick = false;
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            isClick = false;
        }
    };

    public T bd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return bindingLayout();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        FitScreenUtil.setCustomDensity(mActivity, mActivity.getApplication());
        StatusBarUtil.setTranslucentStatus(mActivity);
        initViews(view);
    }

    private View bindingLayout() {
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            try {
                Class<T> clazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];

                Method method = clazz.getMethod("inflate", LayoutInflater.class);
                bd = (T) method.invoke(null, getLayoutInflater());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return bd.getRoot();
    }

    protected abstract void initViews(View view);

    protected void bindData() {
    }

    protected void initClick(View.OnClickListener listener, View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setOnClickListener(listener);
        }
    }

    protected void goToActivity(@NotNull Class c) {
        goToActivity(c, null, -1);
    }

    protected void goToActivity(@NotNull Class c, @NotNull Bundle bundle) {
        goToActivity(c, bundle, -1);
    }

    protected void goToActivity(@NotNull Class c, int requestCode) {
        goToActivity(c, null, requestCode);
    }

    protected void goToActivity(@NotNull Class c, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mActivity, c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (requestCode == -1) {
            startActivity(intent);
        } else {
            startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 显示加载框
     */
    public void showLoading(int message) {
        LoadUtils.getInstance().builder(getContext(), getString(message));
    }

    /**
     * 显示加载框
     */
    public void showLoading(String message) {
        LoadUtils.getInstance().builder(getContext(), message);
    }

    /**
     * 隐藏加载框
     */
    public void hideLoading() {
        LoadUtils.getInstance().close();
    }
}
