package com.example.vtetau.espressodemo;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.vtetau.espressodemo.dagger.components.ApplicationComponent;
import com.example.vtetau.espressodemo.dagger.components.DaggerApplicationComponent;
import com.example.vtetau.espressodemo.dagger.modules.AppModule;
import com.example.vtetau.espressodemo.dagger.util.DaggerApplication;

public class EspressoDemoApp extends Application implements DaggerApplication {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.applicationComponent = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .build();

        this.applicationComponent.inject(this);
    }

    @Override
    @NonNull
    @SuppressWarnings("SuspiciousGetterSetter")
    public ApplicationComponent getComponent() {
        return this.applicationComponent;
    }
}
