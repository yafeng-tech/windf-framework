package com.windf.core.entity;

public enum OrderSortType {
    ASC("asc"),
    DESC("desc");

    private final String direction;

    OrderSortType(String direction) {
        this.direction = direction;
    }
}
