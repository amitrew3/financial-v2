package com.rew3.common.utils;

import java.util.HashMap;

public class TypeAndValue {
    public TypeAndValue(String type, String value) {
        this.type = type;
        this.value = value;
    }

    String type;
    String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}