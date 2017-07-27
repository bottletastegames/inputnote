package com.axtha.inputnote;

/**
 * Created by user on 2017-07-24.
 */

public enum FieldType {
    INTEGER(1), REAL_NUM(2), TEXT(3), DATE_TIME(4);
    private final int value;

    FieldType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
