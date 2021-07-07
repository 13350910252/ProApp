package com.yinp.proapp.module.wanandroid.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yinp.proapp.R;
import com.yinp.proapp.base.activity.PresenterBaseFragmentActivity;
import com.yinp.proapp.databinding.ActivityWanMeBinding;
import com.yinp.proapp.module.wanandroid.WanManager;
import com.yinp.proapp.module.wanandroid.bean.IntegralBean;
import com.yinp.proapp.module.wanandroid.bean.WanLoginBean;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanData;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanData2;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanObserver;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanObserver2;
import com.yinp.proapp.utils.AppUtils;
import com.yinp.proapp.utils.JumpWebUtils;
import com.yinp.proapp.utils.StatusBarUtil;
import com.yinp.tools.fragment_dialog.BaseDialogFragment;
import com.yinp.tools.fragment_dialog.CommonDialogFragment;
import com.yinp.tools.fragment_dialog.DialogFragmentHolder;
import com.yinp.tools.fragment_dialog.ViewConvertListener;
import com.yinp.tools.shap_view.ShapeTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WanMeActivity extends PresenterBaseFragmentActivity<ActivityWanMeBinding, WanManager> {
    private boolean isLogin;

    @Override
    protected WanManager createPresenter() {
        return new WanManager(mContext);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext));
        bd.header.headerCenterTitle.setText("我的");
        initClick(this, bd.tvNickName, bd.llJoinOpenSource, bd.llOpenSourceWeb, bd.llSetting, bd.llIntegralRank, bd.llCollect, bd.sllLoginOut);
        bindData();
    }

    @Override
    protected void bindData() {
        super.bindData();
        getIntegral();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUserInfo();
    }

    private void initUserInfo() {
        WanLoginBean wanLoginBean = WanLoginBean.getUserInfo(mContext);
        if (wanLoginBean != null) {
            if (TextUtils.isEmpty(wanLoginBean.getUsername())) {
                bd.cuaUserHead.setUserName("登录");
                bd.tvNickName.setText("请先登录~");
                isLogin = false;
            } else {
                isLogin = true;
                bd.cuaUserHead.setUserName(AppUtils.getDecodeName(wanLoginBean.getNickname()));
                bd.tvNickName.setText(wanLoginBean.getNickname());
            }
        } else {
            bd.cuaUserHead.setUserName("登录");
            bd.tvNickName.setText("请先登录~");
            isLogin = false;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == bd.tvNickName) {
            if (!isLogin) {
                setLoginDialog();
            }
        } else if (v == bd.llOpenSourceWeb) {
            JumpWebUtils.startWebView(mContext,
                    "玩Android",
                    "https://www.wanandroid.com/");
        } else if (v == bd.llJoinOpenSource) {
            JumpWebUtils.startWebView(mContext,
                    "WanAndroid——WJX",
                    "https://github.com/wangjianxiandev/WanAndroidMvp");
        } else if (v == bd.llIntegralRank) {
            goToActivity(WanRankActivity.class);
        } else if (v == bd.llCollect) {
            goToActivity(WanCollectionActivity.class);
        } else if (v == bd.sllLoginOut) {
            loginOutDialog();
        }
    }

    private void getIntegral() {
        presenter.getIntegral(new WanObserver<WanData>() {
            @Override
            public void onSuccess(WanData o) {
                JsonObject data = o.getData().isJsonNull() ? null : o.getData().getAsJsonObject();
                if (data.isJsonNull()) {
                    return;
                }
                IntegralBean integralBean = new Gson().fromJson(data, IntegralBean.class);
                bd.tvIntegralRanking.setText("积分：" + integralBean.getCoinCount() + " 排行：" + AppUtils.getText(integralBean.getRank()));
            }

            @Override
            public void onError(String msg) {
                bd.tvIntegralRanking.setText("积分:--" + " 排行:--");
            }

            @Override
            public void onCodeFail(String msg) {
                bd.tvIntegralRanking.setText("积分:--" + " 排行:--");
            }
        });
    }

    private void setLoginDialog() {
        showLoading("登录中...");
        CommonDialogFragment.newInstance(this).setLayoutId(R.layout.dialog_login).setViewConvertListener(new ViewConvertListener() {
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
                        showToast("账号还没有填写");
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        showToast("密码还没有填写");
                        return;
                    }
                    presenter.login(account, password, new WanObserver<WanData>() {
                        @Override
                        public void onSuccess(WanData o) {
                            getIntegral();
                            hideLoading();
                            dialogFragment.dismiss();
                            JsonElement data = o.getData().getAsJsonObject();
                            if (data.isJsonNull()) {
                                showToast("没有获取到登录信息，请重试");
                                return;
                            }
                            WanLoginBean wanLoginBean = new Gson().fromJson(data, WanLoginBean.class);
                            WanLoginBean.saveUserInfo(wanLoginBean, mContext);
                            bd.tvNickName.setText(wanLoginBean.getNickname());
                            bd.cuaUserHead.setUserName(AppUtils.getDecodeName(wanLoginBean.getNickname()));
                        }

                        @Override
                        public void onError(String msg) {
                            hideLoading();
                            showToast(msg);
                        }

                        @Override
                        public void onCodeFail(String msg) {
                            hideLoading();
                            showToast(msg);
                        }
                    });
                });
            }
        }).setGravity(BaseDialogFragment.CENTER).setAnimStyle(R.style.CenterDialogAnimation).setPercentSize(0.8f, 0).show(getSupportFragmentManager());
    }

    private void loginOutDialog() {
        CommonDialogFragment.newInstance(this).setLayoutId(R.layout.dialog_tips).setViewConvertListener(new ViewConvertListener() {
            @Override
            public void convertView(DialogFragmentHolder holder, BaseDialogFragment dialogFragment) {
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_left = holder.getView(R.id.tv_left);
                TextView tv_right = holder.getView(R.id.tv_right);
                tv_left.setOnClickListener(v -> {
                    dialogFragment.dismiss();
                });
                tv_right.setOnClickListener(v -> loginOut());
            }
        }).setGravity(BaseDialogFragment.CENTER).setAnimStyle(R.style.CenterDialogAnimation).setPercentSize(0.8f, 0).show(getSupportFragmentManager());
    }

    private void loginOut() {
        presenter.loginOut(new WanObserver2<WanData2<String>>() {
            @Override
            public void onSuccess(WanData2<String> o) {
                showToast("登出成功");
            }

            @Override
            public void onError(String msg) {
                showToast("登出失败");
            }

            @Override
            public void onCodeFail(String msg) {
                showToast("登出失败");
            }
        });
    }

    /**
     * 登出
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginOut() {
        bd.tvIntegralRanking.setText("积分:--" + " 排行:--");
    }

    /**
     * 登录
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogin() {
        setLoginDialog();
    }
}