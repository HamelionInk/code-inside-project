package com.nikitin.codeinsideproject.util;

public enum RoleEnum {
    USER("USER"),
    ADMIN("ADMIN");

    private final String enumName;

    RoleEnum(String enumName) {
        this.enumName = enumName;
    }

    public String getEnumName() {
        return enumName;
    }
}
