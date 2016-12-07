package com.example.vtetau.espressodemo.util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {
    public KeyboardUtil() {
    }

    public static void showKeyboard(@NonNull Activity activity, @NonNull View focusedView) {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService("input_method");
        imm.showSoftInput(focusedView, 0);
    }

    public static void showKeyboard(@NonNull Activity activity) {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService("input_method");
        imm.toggleSoftInput(2, 0);
    }

    public static void hideKeyboard(@NonNull Activity activity) {
        InputMethodManager manager = (InputMethodManager)activity.getSystemService("input_method");
        if(null != activity.getCurrentFocus()) {
            manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 2);
        }

    }

    public static void hideKeyboard(@NonNull Activity activity, @NonNull View focusedView) {
        InputMethodManager manager = (InputMethodManager)activity.getSystemService("input_method");
        manager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
    }
}