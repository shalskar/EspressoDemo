package com.example.vtetau.espressodemo.common.Util;

import android.support.annotation.Nullable;

public class StringUtil {

    public static boolean emptyString(@Nullable CharSequence text) {
        return (text == null || text.toString().trim().isEmpty());
    }
}
