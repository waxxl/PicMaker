package com.yd.photoeditor.vv;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class WindowUtils {
    public static void showKeyboard(View view) {
        ((InputMethodManager) view.getContext().getSystemService("input_method")).showSoftInput(view, 0);
    }

    public static void hideKeyboard(View view) {
        ((InputMethodManager) view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
