package com.yinp.proapp.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @author :yinpeng
 * date      :2021/8/13
 * package   :com.yinp.proapp.utils
 * describe  :
 */
public abstract class MTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
