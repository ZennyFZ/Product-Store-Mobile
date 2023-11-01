package com.prm392.assignment.api;

import com.prm392.assignment.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Endpoints {

    @GET("/products")
    Call<List<ProductModel>> getAllProducts();
}
