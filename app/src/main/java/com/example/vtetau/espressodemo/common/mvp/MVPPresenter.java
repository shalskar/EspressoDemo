package com.example.vtetau.espressodemo.common.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

public abstract class MVPPresenter<V extends MVPView> {

    private @Nullable
    WeakReference<V> view;

    public abstract void onDestroy();

    public void bindView(@NonNull V view) {
        this.view = new WeakReference<>(view);
    }

    public void unbindView() {
        view = null;
    }

    @Nullable
    protected V getView() {
        if (view == null) {
            return null;
        } else {
            return view.get();
        }
    }

}
