package com.yinp.proapp.web.webview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.yinp.proapp.base.activity.AppBaseActivity;
import com.yinp.proapp.constant.Constant;
import com.yinp.proapp.databinding.ActivityWebViewBinding;
import com.yinp.proapp.utils.StatusBarUtil;

/**
 * 统一webview
 * <p>
 * 支持进度条以及自定义进度条
 * 支持文件下载
 * 支持文件下载断点续传
 * 支持下载通知形式提示进度
 * 简化 Javascript 通信
 * 支持 Android 4.4 Kitkat 以及其他版本文件上传
 * 支持注入 Cookies
 * 加强 Web 安全
 * 支持全屏播放视频
 * 兼容低版本 Js 安全通信
 * 更省电 。
 * 支持调起微信支付
 * 支持调起支付宝（请参照sample）
 * 默认支持定位
 * 支持传入 WebLayout（下拉回弹效果）
 * 支持自定义 WebView
 * 支持 JsBridge
 * <p>
 * 作者：因为我的心
 * 链接：https://www.jianshu.com/p/789f3a473c67
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * <p>
 * <p>
 * 如果你的应用需要用到视频 ， 那么请你在使用 AgentWeb 的 Activity 对应的清单文件里加入如下配置
 * android:hardwareAccelerated="true"
 * android:configChanges="orientation|screenSize"
 */
public class WebViewActivity extends AppBaseActivity<ActivityWebViewBinding> {
    private AgentWeb mAgentWeb;
    private String mTitle;

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        setStatusBarHeight(StatusBarUtil.getStatusBarHeight(mContext));
        initClick(this, bd.header.headerBackImg);

        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra(Constant.KEY_TITLE);
            mUrl = intent.getStringExtra(Constant.KEY_URL);
            bd.header.headerCenterTitle.setText(mTitle);
            bd.header.headerCenterTitle.setSelected(true);
            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(bd.llWebContent, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                    .useDefaultIndicator()// 使用默认进度条
                    .createAgentWeb()
                    .ready()
                    .go(mUrl);

        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == bd.header.headerBackImg) {
            //直接推出整个页面
            finish();
        }
    }

    /**
     * 页面内部返回
     */
    @Override
    public void onBackPressed() {
        if (!mAgentWeb.back()) {
            WebViewActivity.this.finish();
        }
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}