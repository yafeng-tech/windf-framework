package com.windf.core.entity;

import java.io.Serializable;

/**
 * id,name
 * 用于只需要值和名字的场景
 */
public class NamedBean implements Serializable {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
