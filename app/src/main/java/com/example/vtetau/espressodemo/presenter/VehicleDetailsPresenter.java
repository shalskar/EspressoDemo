package com.example.vtetau.espressodemo.presenter;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import com.example.vtetau.espressodemo.common.Util.StringUtil;
import com.example.vtetau.espressodemo.common.mvp.MVPPresenter;
import com.example.vtetau.espressodemo.dagger.scopes.ApplicationScope;
import com.example.vtetau.espressodemo.model.Attribute;
import com.example.vtetau.espressodemo.model.AttributeCreator;
import com.example.vtetau.espressodemo.model.AttributeOption;
import com.example.vtetau.espressodemo.model.ValidationState;
import com.example.vtetau.espressodemo.view.VehicleDetailsElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@ApplicationScope
public class VehicleDetailsPresenter extends MVPPresenter<VehicleDetailsElement> {

    private static final String VEHICLE_DETAILS_GROUP_NAME = "Vehicle Details";

    @NonNull
    private List<Attribute> attributes = new ArrayList<>();

    @NonNull
    private final HashMap<String, String> attributeValues = new HashMap<>();

    @NonNull
    private final HashMap<String, ValidationState> attributeValidationStates = new HashMap<>();

    @NonNull
    private String dontKnowOption;

    @NonNull
    private String lessThanError;

    @NonNull
    private String greaterThanError;

    @NonNull
    private String emptyError;


    @Override
    public void bindView(@NonNull VehicleDetailsElement view) {
        super.bindView(view);

        if (this.attributes.isEmpty()) {
            this.attributes = AttributeCreator.createAttributes();
        }
    }

    public void onItemClicked(int position) {
        Attribute attribute = this.attributes.get(position);
        if (attribute.getOptions() != null && !attribute.getOptions().isEmpty()) {
            int selectedOption = getSelectedOption(attribute);
            getView().showOptionDialog(position, attribute.getDisplayName(),
                    attribute.getOptionsAsCharSequence(!attribute.getRequiredForSell() ? this.dontKnowOption : null),
                    selectedOption);
        }
    }

    public void onSaveClicked() {
        Pair<Boolean, Integer> validation = validateFields();
        if (validation.first) { // Validation successful
            for (Attribute attribute : this.attributes) {
                attribute.setValue(this.attributeValues.get(attribute.getName()));
            }
            getView().showInputValid();
        } else { // Validation unsuccessful, scroll to invalid view
            getView().scrollToViewPosition(validation.second);
        }
    }

    public void onResetClicked() {
        this.attributeValues.clear();
        for (Attribute attribute : this.attributes) {
            attribute.setValue(null);
        }
        getView().reset();
    }

    public void onValueChanged(int position, @NonNull String value) {
        if (position != -1) {
            Attribute attribute = this.attributes.get(position);
            this.attributeValues.put(attribute.getName(), value);
            getView().showErrorForView(position, false, null);
        }
    }

    public void onViewFocusChanged(int position, boolean focused) {
        if (!focused && position != -1) {
            Attribute attribute = this.attributes.get(position);
            ValidationState validationState = validateField(attribute, this.attributeValues.get(attribute.getName()));
            this.attributeValidationStates.put(attribute.getName(), validationState);
            getView().showErrorForView(position, !validationState.isValid(), validationState.getErrorMessage());
        }
    }

    public void onAttributeOptionsSelected(int position, int selectedOption) {
        Attribute attribute = this.attributes.get(position);
        ArrayList<AttributeOption> attributeOptions = new ArrayList<>(this.attributes.get(position).getOptions());
        String optionsString = null;

        if (!attribute.getRequiredForSell()) {
            selectedOption--;
        }

        if (selectedOption >= 0) {
            optionsString = attributeOptions.get(selectedOption).getValue();
        }
        this.attributeValues.put(attribute.getName(), optionsString);
        this.attributeValidationStates.put(attribute.getName(), new ValidationState(true, null));
        getView().showErrorForView(position, false, null);
    }

    @Override
    public void onDestroy() {
    }

    @NonNull
    private Pair<Boolean, Integer> validateFields() {
        boolean validated = true;
        int index = 0;
        int firstErrorIndex = Integer.MAX_VALUE;

        for (Attribute attribute : this.attributes) {
            ValidationState validationState = validateField(attribute, this.attributeValues.get(attribute.getName()));
            this.attributeValidationStates.put(attribute.getName(), validationState);
            getView().showErrorForView(index, !validationState.isValid(), validationState.getErrorMessage());

            if (!validationState.isValid()) {
                validated = false;
                firstErrorIndex = Math.min(firstErrorIndex, index);
            }

            index++;
        }
        return new Pair<>(validated, firstErrorIndex);
    }

    @NonNull
    private ValidationState validateField(@NonNull Attribute attribute, @NonNull String attributeValue) {
        ValidationState validationState = new ValidationState(false, null);
        if (!StringUtil.emptyString(attributeValue)) {
            if (attribute.hasRange()) {
                float lowerRange = Float.parseFloat(attribute.getLowerRange());
                float upperRange = Float.parseFloat(attribute.getUpperRange());
                float value = Float.parseFloat(attributeValue);
                if (value < lowerRange) {
                    validationState.setErrorMessage(String.format(this.lessThanError, attribute.getDisplayName(), attribute.getLowerRange()));
                } else if (value > upperRange) {
                    validationState.setErrorMessage(String.format(this.greaterThanError, attribute.getDisplayName(), attribute.getUpperRange()));
                } else {
                    validationState.setValid(true);
                }
            } else {
                validationState.setValid(true);
            }
        } else if (attribute.getRequiredForSell()) {
            validationState.setErrorMessage(String.format(this.emptyError, attribute.getDisplayName()));
        } else {
            validationState.setValid(true);
        }
        return validationState;
    }

    @NonNull
    public String getCategory() {
//        return this.categoryManager.getCategoryByMcat(this.listingTemplateManager.getListingTemplate().getCategory()).getName();
        // todo
        return "";
    }

    private int getSelectedOption(@NonNull Attribute attribute) {
        String attributeValue = this.attributeValues.get(attribute.getName());

        if (!StringUtil.emptyString(attributeValue)) {
            String option = this.attributeValues.get(attribute.getName());

            int index = 0;
            for (AttributeOption attributeOption : attribute.getOptions()) {
                if (option.equals(attributeOption.getValue())) {
                    return attribute.getRequiredForSell() ? index : index + 1;
                }
                index++;
            }
        }
        // If required for sell, the default option is nothing, otherwise it is the first option ("Don't know")
        return attribute.getRequiredForSell() ? -1 : 0;
    }

    @Nullable
    public ValidationState getValidationState(int position) {
        return this.attributeValidationStates.get(this.attributes.get(position).getName());
    }

    @Nullable
    public String getAttributeDisplayValue(int position) {
        Attribute attribute = this.attributes.get(position);
        String attributeValue = this.attributeValues.get(attribute.getName());
        if (attribute.getOptions() != null && !attribute.getOptions().isEmpty()) {
            for (AttributeOption attributeOption : attribute.getOptions()) {
                if (attributeOption.getValue().equals(attributeValue)) {
                    return attributeOption.getDisplay();
                }
            }
        } else {
            return attributeValue;
        }

        return null;
    }

    public int getAttributesCount() {
        return this.attributes.size();
    }

    @NonNull
    public Attribute getAttribute(int position) {
        return this.attributes.get(position);
    }

    public void setDontKnowOption(@NonNull String dontKnowOption) {
        this.dontKnowOption = dontKnowOption;
    }

    public void setLessThanError(@NonNull String lessThanError) {
        this.lessThanError = lessThanError;
    }

    public void setGreaterThanError(@NonNull String greaterThanError) {
        this.greaterThanError = greaterThanError;
    }

    public void setEmptyError(@NonNull String emptyError) {
        this.emptyError = emptyError;
    }

}
