package com.prm392.assignment.model;

import com.google.gson.annotations.SerializedName;
import com.prm392.assignment.Cart;

import java.util.List;

public class OrderModel {
    @SerializedName("customerID")
    private String customerID;
    @SerializedName("customerName")
    private String customerName;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("address")
    private String address;
    @SerializedName("products")
    private List<CartModel> products;
    @SerializedName("orderDate")
    private String orderDate;
    @SerializedName("paymentMethod")
    private String paymentMethod;

    public OrderModel() {
    }

    public OrderModel(String customerID, String customerName, String phoneNumber, String address, List<CartModel> products, String orderDate, String paymentMethod) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.products = products;
        this.orderDate = orderDate;
        this.paymentMethod = paymentMethod;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public List<CartModel> getProducts() {
        return products;
    }

    public void setProducts(List<CartModel> products) {
        this.products = products;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "customerID='" + customerID + '\'' +
                ", customerName='" + customerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", products=" + products.toString() +
                ", orderDate='" + orderDate + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
