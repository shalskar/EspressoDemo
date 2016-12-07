package com.example.vtetau.espressodemo.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.vtetau.espressodemo.common.mvp.MVPView;

public interface VehicleDetailsElement extends MVPView {

    void showOptionDialog(int position, @NonNull String title, @NonNull CharSequence[] options, int selectedOption);

    void showErrorForView(int position, boolean enabled, @Nullable String errorMessage);

    void scrollToViewPosition(int position);

    void finish();

}
