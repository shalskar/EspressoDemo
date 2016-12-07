package com.example.vtetau.espressodemo.common.dagger.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.example.vtetau.espressodemo.common.dagger.DaggerCallback;
import com.example.vtetau.espressodemo.dagger.components.DaggerComponent;


/**
 * The @{link DaggerActivityDelegate} handles retaining Dagger components across configuration changes
 * <p>
 * The delegate requires the following methods to proxied from the activity:
 * * onRetainCustomNonConfigurationInstance()
 *
 * @param <C> The Dagger component type that will be retained
 */
public final class DaggerActivityDelegate<C extends DaggerComponent> implements NonConfigurationObjectProvider {

    private final String componentKey;

    private final ArrayMap<String, Object> nonConfigurationMap;

    /**
     * @param id       A unique id to identify the Component
     * @param activity the activity that will delegate to this delegate
     * @param callback a callback responsible for creating the Dagger Component
     */
    public DaggerActivityDelegate(@NonNull String id, @NonNull AppCompatActivity activity, @NonNull DaggerCallback<C> callback) {
        this.componentKey = "activity_component_" + id;

        Object lastNonConfig = activity.getLastCustomNonConfigurationInstance();
        if (lastNonConfig instanceof ArrayMap) {
            this.nonConfigurationMap = (ArrayMap<String, Object>) lastNonConfig;
        } else {
            this.nonConfigurationMap = new ArrayMap<>();
        }

        Object retainedComponent = getNonConfigurationObject(componentKey);
        C component;

        if (retainedComponent instanceof DaggerComponent) {
//            Timber.d("Using retained activity component");
            component = (C) retainedComponent;
        } else {
//            Timber.d("Creating new activity component");
            component = callback.createComponent();
            addNonConfigurationObject(componentKey, component);
        }

        callback.inject(component);
    }

    @Override
    @Nullable
    public Object getNonConfigurationObject(@NonNull String key) {
//        Timber.d("Getting Non Configuration object: %s", key);
        if (this.nonConfigurationMap.containsKey(key)) {
            return this.nonConfigurationMap.get(key);
        }
        return null;
    }

    @Override
    @Nullable
    public Object removeNonConfigurationObject(@NonNull String key) {
//        Timber.d("Removing Non Configuration object: %s", key);
        Object object = getNonConfigurationObject(key);

        if (this.nonConfigurationMap.containsKey(key)) {
            return this.nonConfigurationMap.remove(key);
        }
        return object;
    }

    @Override
    public void addNonConfigurationObject(@NonNull String key, @NonNull Object value) {
//        Timber.d("Adding Non Configuration object: %s", key);
        this.nonConfigurationMap.put(key, value);
    }

    @NonNull
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return this.nonConfigurationMap;
    }

    @Nullable
    public DaggerComponent getComponent() {
        return (DaggerComponent) this.nonConfigurationMap.get(componentKey);
    }

}
