package com.yinp.proapp.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.yinp.proapp.R;
import com.yinp.proapp.utils.StatusBarUtil;
import com.yinp.tools.utils.LoadUtils;
import com.yinp.tools.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AppBaseFragmentActivity<T extends ViewBinding> extends BaseFragmentActivity {
    public T bd;
    //记录按下,防止连续点击
    public boolean isClick = false;
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            isClick = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingLayout();
//        FitScreenUtil.setCustomDensity(mActivity, getApplication());
        StatusBarUtil.setTranslucentStatus(mActivity);
        initViews();
    }

    public void bindingLayout() {
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            try {
                Class<T> clazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];

                Method method = clazz.getMethod("inflate", LayoutInflater.class);
                bd = (T) method.invoke(null, getLayoutInflater());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            setContentView(bd.getRoot());
        }
    }

    protected abstract void initViews();

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
     * 设置占位View的高度，主要是用于浸入式状态栏
     *
     * @param height 状态栏高度
     */
    protected void setStatusBarHeight(int height) {
        View view = findViewById(R.id.view_status);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
    }

    /**
     * 显示加载框
     */
    public void showLoading(int message) {
        LoadUtils.getInstance().builder(mContext, getString(message));
    }

    /**
     * 显示加载框
     */
    public void showLoading(String message) {
        LoadUtils.getInstance().builder(mContext, message);
    }

    /**
     * 隐藏加载框
     */
    public void hideLoading() {
        LoadUtils.getInstance().close();
    }

    public void showToast(String msg) {
        ToastUtil.initToast(mContext, msg);
    }
}
