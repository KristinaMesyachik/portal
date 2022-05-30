package com.example.demo.entity.enums;

public enum Type {
    SINGLE_LINE_TEXT("Single line text"),
    MULTILINE_TEXT("Multiline text"),
    RADIO_BUTTON("Radio button"),
    CHECKBOX("Checkbox"),
    COMBOBOX("Combobox"),
    DATE("Date");

    public final String value;

    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
