package com.kapici.kapici.Models;

import java.util.List;

public class Users {
    private String name;
    private String surname;
    private String birthday;
    private String phoneNumber;
    private String address;
    private boolean isAdmin;
    private List<String> shoppingCart;
    private List<String> cartQuantities;
    private long cartTotal;
    private List<String> orderList;
    public Users() {
    }

    public Users(String name, String surname, String birthday, String phoneNumber, String address, List<String> shoppingCart, List<String> cartQuantities,long cartTotal,List<String> orderList) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isAdmin = false;
        this.shoppingCart = shoppingCart;
        this.cartQuantities = cartQuantities;
        this.cartTotal= cartTotal;
        this.orderList=orderList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<String> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<String> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<String> getCartQuantities() {
        return cartQuantities;
    }

    public void setCartQuantities(List<String> cartQuantities) {
        this.cartQuantities = cartQuantities;
    }

    public long getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(long cartTotal) {
        this.cartTotal = cartTotal;
    }

    public List<String> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<String> orderList) {
        this.orderList = orderList;
    }
}
