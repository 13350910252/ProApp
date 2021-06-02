package com.yinp.tools.utils;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yinp.tools.R;

public class LoadUtils {
    private volatile static LoadUtils loadUtils = null;
    private volatile static Dialog dialog;

    public static LoadUtils getInstance() {
        if (loadUtils == null) {
            synchronized (LoadUtils.class) {
                if (loadUtils == null) {
                    loadUtils = new LoadUtils();
                }
            }
        }
        return loadUtils;
    }

    public void builder(Context context, String text) {
        if (dialog == null) {
            synchronized (loadUtils) {
                if (dialog == null) {
                    // 获取Dialog布局
                    @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
                    TextView tipTextView = view.findViewById(R.id.tipTextView);// 提示文字

                    if (text.isEmpty()) {
                        tipTextView.setVisibility(View.GONE);
                    } else {
                        tipTextView.setVisibility(View.VISIBLE);
                        tipTextView.setText(text);
                    }
                    // 定义Dialog布局和参数
                    dialog = new Dialog(context, R.style.NormalDialogStyle);
                    dialog.setCancelable(true); // 是否可以按“返回键”消失
                    dialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
                    dialog.setContentView(view);// 设置布局
                    /**
                     *将显示Dialog的方法封装在这里面
                     */
                    Window window = dialog.getWindow();
                    assert window != null;
                    WindowManager.LayoutParams lp = window.getAttributes();
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setGravity(Gravity.CENTER);
                    window.setAttributes(lp);
                    if (dialog != null) {
                        dialog.show();
                    }
                }
            }
        }

        Log.d("abcd", "builder: " + dialog);
    }

    public void close() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public void setNull() {
        if (dialog != null) {
            dialog.dismiss();
            dialog.cancel();
            dialog = null;
        }
    }
}
