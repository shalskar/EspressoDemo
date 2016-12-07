package com.example.vtetau.espressodemo.dagger.util;

import android.content.Context;

import com.example.vtetau.espressodemo.dagger.components.ApplicationComponent;

public class DaggerInjectionUtil {

    public static ApplicationComponent getApplicationComponent(Context context) {
        Context applicationContext = context.getApplicationContext();

        if (applicationContext instanceof DaggerApplication) {
            return ((DaggerApplication) applicationContext).getComponent();
        } else {
            throw new IllegalArgumentException("Could not get DaggerComponnent");
        }
    }
}
