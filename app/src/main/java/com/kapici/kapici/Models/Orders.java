package com.kapici.kapici.Models;

import java.util.ArrayList;
import java.util.List;

public class Orders {
    private List<String> items;
    private List<String> quantity;
    private long totalPrice;
    private String paymentMethod;
    private String orderOwner;

    public Orders(List<String> items, List<String> quantity, long totalPrice, String paymentMethod,String orderOwner) {
        this.items = items;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.orderOwner = orderOwner;
    }

    public Orders() {
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public List<String> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<String> quantity) {
        this.quantity = quantity;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrderOwner() {
        return orderOwner;
    }

    public void setOrderOwner(String orderOwner) {
        this.orderOwner = orderOwner;
    }
}
