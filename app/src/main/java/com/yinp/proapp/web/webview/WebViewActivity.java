package com.yinp.proapp.web.webview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.yinp.proapp.base.activity.AppBaseActivity;
import com.yinp.proapp.constant.Constant;
import com.yinp.proapp.databinding.ActivityWebViewBinding;
import com.yinp.proapp.utils.StatusBarUtil;

/**
 * 统一webview
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
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra(Constant.KEY_TITLE);
            mUrl = intent.getStringExtra(Constant.KEY_URL);
            bd.header.headerCenterTitle.setText(mTitle);
            bd.header.headerCenterTitle.setSelected(true);
            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(bd.llWebContent, new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator()
                    .createAgentWeb()
                    .ready()
                    .go(mUrl);
        }
    }
}