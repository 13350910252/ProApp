package com.yinp.proapp.module.wanandroid.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.yinp.proapp.R;
import com.yinp.proapp.base.activity.PresenterBaseFragmentActivity;
import com.yinp.proapp.databinding.ActivityWandroidBinding;
import com.yinp.proapp.module.wanandroid.WanManager;
import com.yinp.proapp.module.wanandroid.bean.WanLoginBean;
import com.yinp.proapp.module.wanandroid.dialog.DialogShow;
import com.yinp.proapp.module.wanandroid.fragment.WanHomeFragment;
import com.yinp.proapp.module.wanandroid.fragment.WanNavigationFragment;
import com.yinp.proapp.module.wanandroid.fragment.WanSquareFragment;
import com.yinp.proapp.module.wanandroid.fragment.WanSystemFragment;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanData;
import com.yinp.proapp.module.wanandroid.web.retrofit.WanObserver;
import com.yinp.proapp.utils.AppUtils;
import com.yinp.proapp.utils.StatusBarUtil;
import com.yinp.proapp.view.viewpager2.SimplePagerTitlePictureView;
import com.yinp.proapp.view.viewpager2.ViewPager2Utils;
import com.yinp.tools.fragment_dialog.BaseDialogFragment;
import com.yinp.tools.fragment_dialog.CommonDialogFragment;
import com.yinp.tools.fragment_dialog.DialogFragmentHolder;
import com.yinp.tools.fragment_dialog.ViewConvertListener;
import com.yinp.tools.shap_view.ShapeTextView;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ???Android?????????
 */
public class WandroidActivity extends PresenterBaseFragmentActivity<ActivityWandroidBinding, WanManager> {

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
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(this));
        bd.header.headerCenterTitle.setText("???Android");
        bd.header.headerEnd.setImageResource(R.mipmap.common_software);
        initClick(this, bd.header.headerBackImg, bd.header.headerEnd, bd.ivMe);
        initIndicator();
    }

    private SparseArray<Fragment> fragments = new SparseArray<>();

    private void initIndicator() {
        fragments.put(0, WanHomeFragment.getInstance());
        fragments.put(1, WanSquareFragment.getInstance());
        fragments.put(2, WanNavigationFragment.getInstance());
        fragments.put(3, WanHomeFragment.getInstance());
        fragments.put(4, WanSystemFragment.getInstance());
        fragments.put(5, WanHomeFragment.getInstance());
        fragments.put(6, WanHomeFragment.getInstance());
        fragments.put(7, WanHomeFragment.getInstance());
        bd.materialViewPager.setAdapter(ViewPager2Utils.getAdapter(this, fragments));

        List<String> titleList = new ArrayList<>(Arrays.asList("??????", "??????", "??????", "??????", "??????", "??????", "?????????", "????????????"));
        bd.materialIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator7 = new CommonNavigator(mContext);
        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titleList == null ? 0 : titleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitlePictureView simplePagerTitleView = new SimplePagerTitlePictureView(context);
                simplePagerTitleView.setText(titleList.get(index));
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.b8b8b8));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.ff4d4d));
                simplePagerTitleView.setOnClickListener(v -> bd.materialViewPager.setCurrentItem(index, false));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 0));
                indicator.setLineWidth(UIUtil.dip2px(context, 56));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(getResources().getColor(R.color.ff4d4d));
                return indicator;
            }
        });
        bd.materialIndicator.setNavigator(commonNavigator7);
        ViewPager2Utils.bind(bd.materialIndicator, bd.materialViewPager);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == bd.header.headerBackImg) {
            finish();
        } else if (v == bd.ivMe) {
            if (AppUtils.isLogin(mContext)) {
                goToActivity(WanMeActivity.class);
            } else {
//                setLoginDialog();
                DialogShow.setLoginDialog(mContext, presenter, true, WanMeActivity.class, getSupportFragmentManager());
            }
        }
    }

    private void setLoginDialog() {
        CommonDialogFragment.newInstance(this).setLayoutId(R.layout.dialog_login).setViewConvertListener(new ViewConvertListener() {
            @Override
            public void convertView(DialogFragmentHolder holder, BaseDialogFragment dialogFragment) {
                TextView tv_title = holder.getView(R.id.tv_title);
                tv_title.setText("???Android??????");

                EditText et_account = holder.getView(R.id.et_account);
                EditText et_password = holder.getView(R.id.et_password);
                ShapeTextView stv_login = holder.getView(R.id.stv_login);

                stv_login.setOnClickListener(v -> {
                    String account = et_account.getText().toString().trim();
                    String password = et_password.getText().toString().trim();
                    if (TextUtils.isEmpty(account)) {
                        showToast("?????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        showToast("?????????????????????");
                        return;
                    }
                    showLoading("?????????...");
                    presenter.login(account, password, new WanObserver<WanData>() {
                        @Override
                        public void onSuccess(WanData o) {
                            hideLoading();
                            dialogFragment.dismiss();
                            JsonElement data = o.getData().getAsJsonObject();
                            if (data.isJsonNull()) {
                                showToast("???????????????????????????????????????");
                                return;
                            }
                            WanLoginBean.saveUserInfo(new Gson().fromJson(data, WanLoginBean.class), mContext);
                            goToActivity(WanMeActivity.class);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewPager2Utils.unBind(bd.materialViewPager);
    }
}