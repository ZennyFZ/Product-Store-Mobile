package com.prm392.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.prm392.assignment.adapter.OrderAdapter;
import com.prm392.assignment.api.Endpoints;
import com.prm392.assignment.api.RetrofitClientInstance;
import com.prm392.assignment.model.CartModel;
import com.prm392.assignment.model.OrderModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderList extends AppCompatActivity {
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    List<OrderModel> orderItems;
    OrderAdapter orderAdapter;
    RecyclerView orderListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        showAllOrders();
    }

    public void showLoading() {
        progressDialog = new ProgressDialog(OrderList.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

    public void goToProductList(View view) {
        Intent intent = new Intent(OrderList.this, ProductList.class);
        startActivity(intent);
    }

    public void showAllOrders() {
        showLoading();
        String userId = auth.getInstance().getCurrentUser().getUid();
        Endpoints service = RetrofitClientInstance.getRetrofitInstance().create(Endpoints.class);
        Call<List<OrderModel>> orderList = service.getCustomerOrders(userId);
        orderList.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                Log.d("OrderList", "onResponse: "+response.body());
                progressDialog.dismiss();
                if (response.body() != null) {
                    orderItems = response.body();
                    orderListView = findViewById(R.id.order_list_recyclerview);
                    orderAdapter = new OrderAdapter(OrderList.this, orderItems);
                    orderListView.setAdapter(orderAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OrderList.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}