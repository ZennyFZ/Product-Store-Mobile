package com.prm392.assignment.model;

import com.google.gson.annotations.SerializedName;

public class ProductModel {
    @SerializedName("name")
    private String productName;
    @SerializedName("price")
    private String productPrice;
    @SerializedName("description")
    private String productDescription;
    @SerializedName("image")
    private String productImage;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("status")
    private String status;
    @SerializedName("capacity")
    private String capacity;
    @SerializedName("brand")
    private String brand;
    @SerializedName("category")
    private String category;

    public ProductModel() {
    }

    public ProductModel(String productName, String productPrice, String productDescription, String productImage, String quantity, String status, String capacity, String brand, String category) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.quantity = quantity;
        this.status = status;
        this.capacity = capacity;
        this.brand = brand;
        this.category = category;
    }

    public ProductModel(int productId, String productName, double price, int quantity) {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productImage='" + productImage + '\'' +
                ", quantity='" + quantity + '\'' +
                ", status='" + status + '\'' +
                ", capacity='" + capacity + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
