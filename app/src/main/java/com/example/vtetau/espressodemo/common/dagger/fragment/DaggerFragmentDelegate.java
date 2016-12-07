package com.example.vtetau.espressodemo.common.dagger.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.vtetau.espressodemo.common.dagger.DaggerCallback;
import com.example.vtetau.espressodemo.common.dagger.activity.NonConfigurationObjectProvider;
import com.example.vtetau.espressodemo.dagger.components.DaggerComponent;


/**
 * The @{link DaggerActivityFragment} handles retaining Dagger components across configuration changes
 * <p>
 * A fragment implementing the delegate must have a parent activity that is a @{link {@link NonConfigurationObjectProvider}
 * <p>
 * The delegate requires the following methods to proxied from the activity:
 * * onAttach(Context context)
 *
 * @param <C> The Dagger component type that will be retained
 */
public final class DaggerFragmentDelegate<C extends DaggerComponent> {

    private final String componentKey;

    @NonNull
    private final Fragment fragment;

    @NonNull
    private final DaggerCallback<C> callback;

    private C component;

    /**
     * @param id       A unique id to identify the Component
     * @param fragment the fragment that will delegate to this delegate
     * @param callback a callback responsible for creating the Dagger Component
     */
    public DaggerFragmentDelegate(@NonNull String id, @NonNull Fragment fragment, @NonNull DaggerCallback<C> callback) {
        this.componentKey = "fragment_component_" + id;
        this.fragment = fragment;
        this.callback = callback;
    }

    public void onAttach(@NonNull Context context) {
        if (!(context instanceof NonConfigurationObjectProvider)) {
            throw new IllegalArgumentException("Activity is not of type " + NonConfigurationObjectProvider.class.getSimpleName());
        }

        NonConfigurationObjectProvider daggerActivity = (NonConfigurationObjectProvider) context;

        Object retainedComponent = daggerActivity.getNonConfigurationObject(this.componentKey);

        if (retainedComponent instanceof DaggerComponent) {
//            Timber.d("Using retained fragment component");
            component = (C) retainedComponent;
        } else {
//            Timber.d("Creating new fragment component");
            component = callback.createComponent();

            NonConfigurationObjectProvider cache = (NonConfigurationObjectProvider) fragment.getActivity();
            cache.addNonConfigurationObject(this.componentKey, component);
        }

        if (this.callback != null) {
            this.callback.inject(getComponent());
        }
    }

    @Nullable
    public C getComponent() {
        return component;
    }

}
