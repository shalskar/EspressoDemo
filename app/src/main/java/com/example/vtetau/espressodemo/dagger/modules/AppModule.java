package com.example.vtetau.espressodemo.dagger.modules;


import android.content.Context;
import android.support.annotation.NonNull;

import com.example.vtetau.espressodemo.dagger.qualifiers.ForApplication;
import com.example.vtetau.espressodemo.dagger.scopes.ApplicationScope;
import com.example.vtetau.espressodemo.presenter.VehicleDetailsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private static final String TAG = AppModule.class.getSimpleName();
    private final Context context;

    public AppModule(@NonNull Context context) {
        this.context = context;
    }

    @ApplicationScope
    @Provides
    @NonNull
    static VehicleDetailsPresenter provideVehicleDetailsPresenter() {
        return new VehicleDetailsPresenter();
    }

    @ApplicationScope
    @Provides
    @ForApplication
    @NonNull
    Context provideApplicationContext() {
        return this.context;
    }
}
