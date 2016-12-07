package com.example.vtetau.espressodemo.common.dagger.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.vtetau.espressodemo.common.dagger.DaggerCallback;
import com.example.vtetau.espressodemo.dagger.components.DaggerComponent;


/**
 * An AppCompat activity that implements the @{link DaggerActivityDelegate}
 *
 * @param <C> The dagger component type this activity will hold
 */
public abstract class DaggerActivity<C extends DaggerComponent> extends AppCompatActivity implements NonConfigurationObjectProvider, DaggerCallback<C> {

    private DaggerActivityDelegate<C> daggerDelegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        this.daggerDelegate = new DaggerActivityDelegate<>(this.getClass().getSimpleName(), this, this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return this.daggerDelegate.onRetainCustomNonConfigurationInstance();
    }

    @Override
    @Nullable
    public Object getNonConfigurationObject(@NonNull String key) {
        return this.daggerDelegate.getNonConfigurationObject(key);
    }

    @Override
    @Nullable
    public Object removeNonConfigurationObject(@NonNull String key) {
        return daggerDelegate.removeNonConfigurationObject(key);
    }

    @Override
    public void addNonConfigurationObject(@NonNull String key, @NonNull Object value) {
        this.daggerDelegate.addNonConfigurationObject(key, value);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
