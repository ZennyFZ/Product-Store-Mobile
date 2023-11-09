package com.prm392.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.prm392.assignment.adapter.OrderDetailAdapter;
import com.prm392.assignment.model.CartModel;

import java.util.List;

public class OrderDetail extends AppCompatActivity {
    TextView subtotalTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        showOrderDetail();
        calculateSubtotal();
    }

    public void goToOrderList(View view) {
        Intent intent = new Intent(this, OrderList.class);
        startActivity(intent);
    }

    public void showOrderDetail() {
        Intent intent = getIntent();
        List<CartModel> products = (List<CartModel>) intent.getSerializableExtra("ODproducts");
        Log.d("ODproducts", products.toString());
        RecyclerView recyclerView = findViewById(R.id.order_detail_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new OrderDetailAdapter(this, products));
    }

    public void calculateSubtotal() {
        Intent intent = getIntent();
        List<CartModel> products = (List<CartModel>) intent.getSerializableExtra("ODproducts");
        double subtotal = 0;
        for (CartModel product : products) {
            subtotal += product.getPrice() * product.getQuantity();
        }
        subtotalTextView = findViewById(R.id.order_detail_total);
        subtotalTextView.setText("Subtotal: $" + String.valueOf(subtotal));
    }
}