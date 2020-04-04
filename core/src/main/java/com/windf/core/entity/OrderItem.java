package com.windf.core.entity;

public class OrderItem {
    private String dataIndex;
    private OrderSortType sort;

    public OrderItem() {
    }

    public OrderItem(String dataIndex, OrderSortType sort) {
        this.dataIndex = dataIndex;
        this.sort = sort;
    }

    public String getDataIndex() {
        return this.dataIndex;
    }

    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
    }

    public OrderSortType getSort() {
        return sort;
    }

    public void setSort(OrderSortType sort) {
        this.sort = sort;
    }
}
