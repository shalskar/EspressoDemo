package com.example.vtetau.espressodemo.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * @author scook
 */
public class AttributeUnit {

    private static final HashMap<String, String[]> attributeUnits = new HashMap<>();

    static {
        String[] cpuSpeed = new String[]{"MHz", "GHz"};
        attributeUnits.put("CPUSpeed", cpuSpeed);

        String[] hardDrive = new String[]{"MB", "GB", "TB"};
        attributeUnits.put("HardDrive", hardDrive);

        String[] memory = new String[]{"MB", "GB"};
        attributeUnits.put("Memory", memory);

        String[] monitorSize = new String[]{"inches"};
        attributeUnits.put("MonitorSize", monitorSize);

        String[] megapixels = new String[]{"MP"};
        attributeUnits.put("Megapixels", megapixels);

        String[] opticalZoom = new String[]{"x"};
        attributeUnits.put("OpticalZoom", opticalZoom);
    }

    private long id;

    private Attribute attribute;

    private String unit;

    public static List<String> getUnitsForAttributeName(String name) {
        String[] units = attributeUnits.get(name);

        if (units == null) {
            return null;
        }

        return Arrays.asList(units);
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Attribute getAttribute() {
        return this.attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
