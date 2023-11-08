package com.prm392.assignment.api;

import com.prm392.assignment.model.CartModel;
import com.prm392.assignment.model.OrderModel;
import com.prm392.assignment.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Endpoints {

    @GET("/products")
    Call<List<ProductModel>> getAllProducts();

    @GET("/products/search/{keyword}")
    Call<List<ProductModel>> searchProduct(@Path("keyword") String keyword);

    @POST("/orders")
    Call<OrderModel> createOrder(@Body OrderModel orderModel);

    @GET("/orders/filter/{date}")
    Call<List<OrderModel>> filterOrderByDate(@Path("date") String date);

    @GET("/orders/{customerID}")
    Call<List<OrderModel>> getCustomerOrders(@Path("customerID") String customerID);
}
