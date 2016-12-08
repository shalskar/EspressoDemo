package com.example.vtetau.espressodemo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vtetau.espressodemo.common.dagger.activity.DaggerActivity;
import com.example.vtetau.espressodemo.component.CustomRecyclerAdapter;
import com.example.vtetau.espressodemo.component.FieldViewHolder;
import com.example.vtetau.espressodemo.component.SwitchViewHolder;
import com.example.vtetau.espressodemo.component.VehicleDetailsAdapter;
import com.example.vtetau.espressodemo.dagger.components.ApplicationComponent;
import com.example.vtetau.espressodemo.dagger.util.DaggerInjectionUtil;
import com.example.vtetau.espressodemo.fragment.AttributeOptionDialogFragment;
import com.example.vtetau.espressodemo.model.Attribute;
import com.example.vtetau.espressodemo.model.ValidationState;
import com.example.vtetau.espressodemo.presenter.VehicleDetailsPresenter;
import com.example.vtetau.espressodemo.util.KeyboardUtil;
import com.example.vtetau.espressodemo.view.VehicleDetailsElement;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;


public class MainActivity extends DaggerActivity<ApplicationComponent> implements VehicleDetailsElement, VehicleDetailsAdapter.AdapterListener, CustomRecyclerAdapter.OnItemClickListener<FieldViewHolder>,
        AttributeOptionDialogFragment.AttributeOptionDialogListener {

    private static final String TAG_DIALOG_ATTRIBUTE_OPTION = "tag_dialog_attribute_option";

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindString(R.string.dont_know_option)
    String dontKnowOption;

    @BindString(R.string.field_empty_error)
    String emptyError;

    @BindString(R.string.greater_than_error)
    String greaterThanError;

    @BindString(R.string.less_than_error)
    String lessThanError;

    @NonNull
    private VehicleDetailsAdapter adapter;

    @Inject
    VehicleDetailsPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.presenter.bindView(this);
        resolvePresenterResources();
        initialiseRecyclerView();
    }

    private void initialiseRecyclerView() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new VehicleDetailsAdapter(this);
        this.adapter.setOnItemClickListener(this);
        this.recyclerView.setAdapter(this.adapter);
    }

    private void resolvePresenterResources() {
        this.presenter.setDontKnowOption(this.dontKnowOption);
        this.presenter.setEmptyError(this.emptyError);
        this.presenter.setGreaterThanError(this.greaterThanError);
        this.presenter.setLessThanError(this.lessThanError);
    }

    @Override
    public void onItemClick(@NonNull FieldViewHolder viewHolder, int position) {
        if (viewHolder instanceof SwitchViewHolder) {
            SwitchViewHolder switchViewHolder = (SwitchViewHolder) viewHolder;
            switchViewHolder.setChecked(!switchViewHolder.isChecked());
        }
        this.presenter.onItemClicked(position);
    }

    @Override
    public void showOptionDialog(int position, @NonNull String title, @NonNull CharSequence[] options, int selectedOption) {
        KeyboardUtil.hideKeyboard(this);
        this.recyclerView.clearFocus();
        AttributeOptionDialogFragment.newInstance(position, title, options, selectedOption)
                .show(getSupportFragmentManager(), TAG_DIALOG_ATTRIBUTE_OPTION);
    }

    @Override
    public void showErrorForView(int position, boolean enabled, @Nullable String errorMessage) {
        RecyclerView.ViewHolder viewHolder = this.recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {
            ((FieldViewHolder) viewHolder).setError(enabled, errorMessage);
        } else if(!this.recyclerView.isComputingLayout()) {
            this.adapter.notifyItemChanged(position);
        }
    }

    @Override
    public void showInputValid() {
        Snackbar.make(this.recyclerView, "Input valid!", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void scrollToViewPosition(int position) {
        this.recyclerView.smoothScrollToPosition(position);
    }

    @Override
    public int getAttributesCount() {
        return this.presenter.getAttributesCount();
    }

    @NonNull
    @Override
    public String getCategory() {
        return this.presenter.getCategory();
    }

    @NonNull
    @Override
    public Attribute getAttribute(int position) {
        return this.presenter.getAttribute(position);
    }

    @Override
    public void onPositiveClicked(int position, int selectedOption) {
        this.presenter.onAttributeOptionsSelected(position, selectedOption);
        this.adapter.notifyItemChanged(position);
    }

    @Override
    public void onValueChanged(@NonNull FieldViewHolder viewHolder, @NonNull String value) {
        this.presenter.onValueChanged(viewHolder.getAdapterPosition(), value);
    }

    @Override
    public void onViewFocusChanged(@NonNull FieldViewHolder viewHolder, boolean focused) {
        this.presenter.onViewFocusChanged(viewHolder.getAdapterPosition(), focused);
    }

    @Override
    public void reset() {
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return super.onOptionsItemSelected(item);
            case R.id.menu_save:
                this.presenter.onSaveClicked();
                break;
            case R.id.menu_reset:
                this.presenter.onResetClicked();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_vehicle_details, menu);
        return result;
    }

    @Nullable
    @Override
    public ValidationState getValidationState(int position) {
        return this.presenter.getValidationState(position);
    }

    @Nullable
    @Override
    public String getAttributeDisplayValue(int position) {
        return this.presenter.getAttributeDisplayValue(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.presenter.unbindView();
        this.presenter.onDestroy();
    }

    @Nullable
    @Override
    public ApplicationComponent createComponent() {
        return DaggerInjectionUtil.getApplicationComponent(getApplicationContext());
    }

    @Override
    public void inject(@Nullable ApplicationComponent component) {
        if (component != null) {
            component.inject(this);
        }
    }
}
