package com.yinp.tools.fragment_dialog;

/**
 * 用于dialogFragment和Activity的通讯
 */
public interface ViewConvertListener {
    void convertView(DialogFragmentHolder holder, BaseDialogFragment dialogFragment);
}
