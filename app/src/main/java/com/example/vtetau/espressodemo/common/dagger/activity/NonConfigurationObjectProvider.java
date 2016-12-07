package com.example.vtetau.espressodemo.common.dagger.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface NonConfigurationObjectProvider {

    @Nullable
    Object getNonConfigurationObject(@NonNull String key);

    @Nullable
    Object removeNonConfigurationObject(@NonNull String key);

    void addNonConfigurationObject(@NonNull String key, @NonNull Object value);

    @Nullable
    Object onRetainCustomNonConfigurationInstance();

}
