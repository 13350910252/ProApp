package com.yinp.proapp.module.wanandroid.dialog;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.yinp.proapp.R;
import com.yinp.proapp.module.wanandroid.WanManager;
import com.yinp.proapp.module.wanandroid.bean.WanLoginBean;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanData;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanObserver;
import com.yinp.tools.fragment_dialog.BaseDialogFragment;
import com.yinp.tools.fragment_dialog.CommonDialogFragment;
import com.yinp.tools.fragment_dialog.DialogFragmentHolder;
import com.yinp.tools.fragment_dialog.ViewConvertListener;
import com.yinp.tools.shap_view.ShapeTextView;
import com.yinp.tools.utils.LoadUtils;
import com.yinp.tools.utils.ToastUtil;

public class DialogShow {
    /**
     * 显示加载框
     */
    private static void showLoading(Context context, int message) {
        LoadUtils.getInstance().builder(context, context.getString(message));
    }

    /**
     * 显示加载框
     */
    private static void showLoading(Context context, String message) {
        LoadUtils.getInstance().builder(context, message);
    }

    /**
     * 隐藏加载框
     */
    private static void hideLoading() {
        LoadUtils.getInstance().close();
    }

    private static void showToast(Context context, String msg) {
        ToastUtil.initToast(context, msg);
    }

    public static void setLoginDialog(Context context, WanManager presenter, boolean isSkip, Class clazz, FragmentManager manager) {
        CommonDialogFragment.newInstance(context).setLayoutId(R.layout.dialog_login).setViewConvertListener(new ViewConvertListener() {
            @Override
            public void convertView(DialogFragmentHolder holder, BaseDialogFragment dialogFragment) {
                TextView tv_title = holder.getView(R.id.tv_title);
                tv_title.setText("玩Android登录");

                EditText et_account = holder.getView(R.id.et_account);
                EditText et_password = holder.getView(R.id.et_password);
                ShapeTextView stv_login = holder.getView(R.id.stv_login);

                stv_login.setOnClickListener(v -> {
                    String account = et_account.getText().toString().trim();
                    String password = et_password.getText().toString().trim();
                    if (TextUtils.isEmpty(account)) {
                        showToast(context, "账号还没有填写");
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        showToast(context, "密码还没有填写");
                        return;
                    }
                    showLoading(context, "登录中...");
                    presenter.login(account, password, new WanObserver<WanData>() {
                        @Override
                        public void onSuccess(WanData o) {
                            hideLoading();
                            dialogFragment.dismiss();
                            JsonElement data = o.getData().getAsJsonObject();
                            if (data.isJsonNull()) {
                                showToast(context, "没有获取到登录信息，请重试");
                                return;
                            }
                            WanLoginBean.saveUserInfo(new Gson().fromJson(data, WanLoginBean.class), context);
                            if (isSkip) {
                                Intent intent = new Intent(context, clazz);
                                context.startActivity(intent);
                            }
                        }

                        @Override
                        public void onError(String msg) {
                            hideLoading();
                            showToast(context,msg);
                        }

                        @Override
                        public void onCodeFail(String msg) {
                            hideLoading();
                            showToast(context,msg);
                        }
                    });
                });
            }
        }).setGravity(BaseDialogFragment.CENTER).setAnimStyle(BaseDialogFragment.CENTER).setPercentSize(0.8f, 0).show(manager);
    }
}
