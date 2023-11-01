package com.prm392.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.prm392.assignment.adapter.ProductAdapter;
import com.prm392.assignment.api.Endpoints;
import com.prm392.assignment.api.RetrofitClientInstance;
import com.prm392.assignment.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductList extends AppCompatActivity {
    ProductAdapter productAdapter;
    RecyclerView productListView;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        getAllProduct();
    }

    public void showLoading() {
        progressDialog = new ProgressDialog(ProductList.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

    public void getAllProduct() {
        showLoading();
        Endpoints service = RetrofitClientInstance.getRetrofitInstance().create(Endpoints.class);
        Call<List<ProductModel>> productData = service.getAllProducts();
        productData.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                progressDialog.dismiss();
                showAllProducts(response.body());
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ProductList.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showAllProducts(List<ProductModel> productList) {
        productListView = findViewById((R.id.productListView));
        productAdapter = new ProductAdapter(this, productList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductList.this);
        productListView.setLayoutManager(layoutManager);
        productListView.setAdapter(productAdapter);
    }
}