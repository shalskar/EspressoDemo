package com.example.vtetau.espressodemo.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class AttributeCreator {

    @NonNull
    public static List<Attribute> createAttributes() {
        List<Attribute> attributes = new ArrayList<>();

        List<AttributeOption> attributeOptions = new ArrayList<>();
        Attribute modelAttribute = createAttribute("Model", "Model",  true, Attribute.TYPE_STRING, null, null);
        attributeOptions.add(createAttributeOption(modelAttribute, "3", "3"));
        attributeOptions.add(createAttributeOption(modelAttribute, "6", "6"));
        attributeOptions.add(createAttributeOption(modelAttribute, "RX7", "rx7"));
        attributeOptions.add(createAttributeOption(modelAttribute, "RX8", "rx8"));
        modelAttribute.setOptions(attributeOptions);
        attributes.add(modelAttribute);

        attributes.add(createAttribute("4WD", "4wd",  false, Attribute.TYPE_BOOLEAN, null, null));
        attributes.add(createAttribute("Kilometres", "Kilometres",  true, Attribute.TYPE_DECIMAL, "0", "500000"));
        attributes.add(createAttribute("Engine size", "EngineSize",  true, Attribute.TYPE_DECIMAL, "0", "20000"));
        attributes.add(createAttribute("Number plate", "NumberPlate",  false, Attribute.TYPE_STRING, null, null));
        return attributes;
    }

    @NonNull
    public static Attribute createAttribute(@NonNull String displayName, @NonNull String name, boolean requiredForSell, int type,
                                            @Nullable String lowerRange, @Nullable String upperRange) {
        Attribute attribute = new Attribute();
        attribute.setDisplayName(displayName);
        attribute.setName(name);
        attribute.setRequiredForSell(requiredForSell);
        attribute.setType(type);
        attribute.setLowerRange(lowerRange);
        attribute.setUpperRange(upperRange);
        return attribute;
    }

    @NonNull
    public static AttributeOption createAttributeOption(@NonNull Attribute attribute, @NonNull String display, @NonNull String value) {
        AttributeOption attributeOption = new AttributeOption();
        attributeOption.setAttribute(attribute);
        attributeOption.setDisplay(display);
        attributeOption.setValue(value);
        return  attributeOption;
    }
}
