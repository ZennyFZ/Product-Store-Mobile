package com.prm392.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.prm392.assignment.adapter.ProductAdapter;
import com.prm392.assignment.model.ProductModel;

import java.util.ArrayList;

public class ProductList extends AppCompatActivity {

    ArrayList<ProductModel> productList;
    ProductAdapter productAdapter;
    RecyclerView productListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        showAllProducts();
    }

    public void showAllProducts() {
        productListView = findViewById((R.id.productListView));
        productList = new ArrayList<>();
        productList.add(new ProductModel("Xiaomi Mi 10","12000000","Phone from Xiaomi brand", "https://cdn.tgdd.vn/Products/Images/42/307556/xiaomi-redmi-12-bac-thumb-600x600.jpg", "10"));
        productAdapter = new ProductAdapter(getApplicationContext(), productList);
        productListView.setAdapter(productAdapter);
    }
}