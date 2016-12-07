package com.example.vtetau.espressodemo.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * @author scook
 */
public class Attribute {

    public static final int TYPE_NONE = 0;
    public static final int TYPE_BOOLEAN= 1;
    public static final int TYPE_INTEGER = 2;
    public static final int TYPE_DECIMAL = 3;
    public static final int TYPE_STRING = 4;
    public static final int TYPE_DATE = 5;

    private long id;

    private String value;

    private String name;

    private String displayName;

    private int type;

    private String upperRange;

    private String lowerRange;

    private Integer maxStringLength;

    private boolean requiredForSell;

    private String groupName;

    private List<AttributeOption> options;

    private List<AttributeUnit> units;

    private AttributeUnit selectedUnit;

	private String generatedValue;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUpperRange() {
        return this.upperRange;
    }

    public void setUpperRange(String upperRange) {
        this.upperRange = upperRange;
    }

    public String getLowerRange() {
        return this.lowerRange;
    }

    public void setLowerRange(String lowerRange) {
        this.lowerRange = lowerRange;
    }

    @Nullable
    public Integer getMaxStringLength() {
        return maxStringLength;
    }

    public void setMaxStringLength(@Nullable Integer maxStringLength) {
        this.maxStringLength = maxStringLength;
    }

    public boolean getRequiredForSell() {
        return this.requiredForSell;
    }

    public void setRequiredForSell(boolean requiredForSell) {
        this.requiredForSell = requiredForSell;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Collection<AttributeOption> getOptions() {
        return this.options;
    }

    public void setOptions(List<AttributeOption> options) {
        this.options = options;
    }

    public Collection<AttributeUnit> getUnits() {
        return this.units;
    }

    public void setUnits(List<AttributeUnit> units) {
        this.units = units;
    }

    public AttributeUnit getSelectedUnit() {
        return this.selectedUnit;
    }

    public void setSelectedUnit(AttributeUnit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

	public String getGeneratedValue() {
		return generatedValue;
	}

	public void setGeneratedValue(String generatedValue) {
		this.generatedValue = generatedValue;
	}

	@Override
    public String toString() {
        return "Attribute: " + displayName;
    }

    @NonNull
    public CharSequence[] getOptionsAsCharSequence(@Nullable String defaultOption) {
        CharSequence[] attributeOptions = new CharSequence[defaultOption != null ? this.options.size() + 1 : this.options.size()];
        int index = 0;
        // If not required for sell, add extra "Default" option as first option
        if (defaultOption != null) {
            attributeOptions[index] = defaultOption;
            index++;
        }

        for (AttributeOption attributeOption : this.options) {
            attributeOptions[index] = attributeOption.getDisplay();
            index++;
        }
        return attributeOptions;
    }

    public boolean hasRange(){
        //todo
//        return !StringUtil.emptyString(this.lowerRange) && !StringUtil.emptyString(this.upperRange);
        return false;
    }

}
