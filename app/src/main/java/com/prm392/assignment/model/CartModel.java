package com.prm392.assignment.model;

import java.io.Serializable;

public class CartModel implements Serializable {
    private String productName;
    private String productImage;
    private double price;
    private int quantity;

    public CartModel() {
    }

    public CartModel(String productName, String productImage, double price, int quantity) {
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    @Override
    public String toString() {
        return "CartModel{" +
                "productName='" + productName + '\'' +
                ", productImage='" + productImage + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
