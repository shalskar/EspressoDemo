package com.example.vtetau.espressodemo.component;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.vtetau.espressodemo.R;
import com.example.vtetau.espressodemo.common.Util.StringUtil;
import com.example.vtetau.espressodemo.model.Attribute;
import com.example.vtetau.espressodemo.model.ValidationState;

import java.lang.annotation.Retention;

import static com.example.vtetau.espressodemo.component.InputViewHolder.*;
import static java.lang.annotation.RetentionPolicy.SOURCE;

public class VehicleDetailsAdapter extends CustomRecyclerAdapter<FieldViewHolder> implements FieldViewHolderListener {

    @Retention(SOURCE)
    @IntDef({ViewType.DETAIL, ViewType.INPUT, ViewType.SWITCH})
    public @interface ViewType {
        int DETAIL = 0;
        int INPUT = 1;
        int SWITCH = 2;
    }

    @NonNull
    private final AdapterListener adapterListener;

    public VehicleDetailsAdapter(@NonNull AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    @Override
    @Nullable
    public FieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @ViewType final int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FieldViewHolder viewHolder = null;
        switch (viewType) {
            case ViewType.DETAIL:
                viewHolder = new DetailViewHolder(inflater.inflate(R.layout.sell_detail_doubleline_cell, parent, false));
                break;
            case ViewType.SWITCH:
                viewHolder = new SwitchViewHolder(inflater.inflate(R.layout.sell_switch_cell, parent, false), this);
                break;
            case ViewType.INPUT:
                viewHolder = new InputViewHolder(inflater.inflate(R.layout.sell_input_cell, parent, false), this);
                break;
        }
        viewHolder.setError(false, null);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FieldViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        @ViewType
        int viewType = getItemViewType(position);
        Attribute attribute = this.adapterListener.getAttribute(position);
        String value = this.adapterListener.getAttributeDisplayValue(position);

        ValidationState validationState = this.adapterListener.getValidationState(position);

        switch (viewType) {
            case ViewType.DETAIL:
                bindDetailViewHolder((DetailViewHolder) holder, attribute, value);
                break;
            case ViewType.INPUT:
                bindInputViewHolder((InputViewHolder) holder, attribute, value);
                break;
            case ViewType.SWITCH:
                bindSwitchViewHolder((SwitchViewHolder) holder, attribute, value);
                break;
        }

        if (validationState != null) {
            holder.setError(!validationState.isValid(), validationState.getErrorMessage());
        } else {
            holder.setError(false, null);
        }
    }

    private void bindDetailViewHolder(@NonNull DetailViewHolder detailViewHolder, @NonNull Attribute attribute,
                                      @Nullable String attributeValue) {
        detailViewHolder.setPrimaryText(attribute.getDisplayName());
        if (StringUtil.emptyString(attributeValue)) {
            if (attribute.getRequiredForSell()) {
                detailViewHolder.setSecondaryText(R.string.required);
            } else {
                detailViewHolder.setSecondaryText(R.string.none_value);
            }
        } else {
            detailViewHolder.setSecondaryText(attributeValue);
        }

        detailViewHolder.setIconVisibility(false);
    }

    private void bindInputViewHolder(@NonNull InputViewHolder inputViewHolder, @NonNull Attribute attribute,
                                     @Nullable String attributeValue) {
        inputViewHolder.setIconVisibility(false);

        if (attribute.getType() == Attribute.TYPE_DECIMAL) {
            inputViewHolder.setInputType(InputType.DECIMAL);
        } else if (attribute.getType() == Attribute.TYPE_INTEGER) {
            inputViewHolder.setInputType(InputType.INTEGER);
        } else {
            inputViewHolder.setInputType(InputType.STRING);
        }

        if (attribute.getMaxStringLength() != null) {
            inputViewHolder.setMaxInputLength(attribute.getMaxStringLength());
        } else if (attribute.getUpperRange() != null) {
            inputViewHolder.setMaxInputLength(attribute.getUpperRange().length());
        } else {
            inputViewHolder.setMaxInputLength(DEFAULT_INPUT_LENGTH);
        }

        inputViewHolder.setText(attributeValue);
        inputViewHolder.setRequired(attribute.getRequiredForSell());
        inputViewHolder.setHint(attribute.getDisplayName());
    }

    private void bindSwitchViewHolder(@NonNull SwitchViewHolder switchViewHolder, @NonNull Attribute attribute,
                                      @Nullable String attributeValue) {
        switchViewHolder.setPrimaryText(attribute.getDisplayName());
        if (attributeValue != null) {
            switchViewHolder.setChecked(attributeValue);
        }
        switchViewHolder.setIconVisibility(false);
    }

    @Override
    @ViewType
    public int getItemViewType(int position) {
        Attribute attribute = this.adapterListener.getAttribute(position);
        if (attribute.getType() == Attribute.TYPE_BOOLEAN) {
            return ViewType.SWITCH;
        } else if (attribute.getOptions() != null && !attribute.getOptions().isEmpty()) {
            return ViewType.DETAIL;
        } else {
            return ViewType.INPUT;
        }
    }

    @Override
    public int getItemCount() {
        return this.adapterListener.getAttributesCount();
    }

    @Override
    public void onValueChanged(@NonNull FieldViewHolder viewHolder, @NonNull String value) {
        this.adapterListener.onValueChanged(viewHolder, value);
    }

    @Override
    public void onViewFocusChanged(@NonNull FieldViewHolder viewHolder, boolean focused) {
        this.adapterListener.onViewFocusChanged(viewHolder, focused);
    }

    public interface AdapterListener {

        int getAttributesCount();

        @NonNull
        String getCategory();

        @NonNull
        Attribute getAttribute(int position);

        void onValueChanged(@NonNull FieldViewHolder viewHolder, @NonNull String value);

        void onViewFocusChanged(@NonNull FieldViewHolder viewHolder, boolean focused);

        @Nullable
        ValidationState getValidationState(int position);

        @Nullable
        String getAttributeDisplayValue(int position);
    }
}

