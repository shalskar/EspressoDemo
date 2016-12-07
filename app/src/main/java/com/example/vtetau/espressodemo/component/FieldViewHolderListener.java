package com.example.vtetau.espressodemo.component;

import android.support.annotation.NonNull;

public interface FieldViewHolderListener {
    void onValueChanged(@NonNull FieldViewHolder viewHolder, @NonNull String value);
    void onViewFocusChanged(@NonNull FieldViewHolder viewHolder, boolean focused);
}