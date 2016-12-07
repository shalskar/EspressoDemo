package com.example.vtetau.espressodemo.common.dagger;

import android.support.annotation.Nullable;

public interface DaggerCallback<C> {

    @Nullable
    C createComponent();

    void inject(@Nullable C component);

}
