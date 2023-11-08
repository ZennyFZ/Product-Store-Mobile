package com.prm392.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.prm392.assignment.adapter.CartAdapter;
import com.prm392.assignment.util.CartDatabaseHelper;

public class Cart extends AppCompatActivity {
    CartAdapter cartAdapter;
    RecyclerView cartListView;
    TextView totalTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        showCart();
        calculateTotal();
    }

    public void showCart() {
        CartDatabaseHelper cartDatabaseHelper = new CartDatabaseHelper(this);
        if(cartDatabaseHelper.getCartProducts().size() > 0) {
            findViewById(R.id.cartScrollView).setVisibility(View.VISIBLE);
            findViewById(R.id.cartEmpty).setVisibility(View.GONE);
        }
        cartListView = findViewById(R.id.cartRecyclerView);
        cartAdapter = new CartAdapter(this, new CartDatabaseHelper(this).getCartProducts());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Cart.this);
        cartListView.setLayoutManager(layoutManager);
        cartListView.setAdapter(cartAdapter);
    }

    public void removeAllProductsFromCart(View view) {
        CartDatabaseHelper cartDatabaseHelper = new CartDatabaseHelper(this);
        cartDatabaseHelper.removeAllProducts();
        calculateTotal();
        showCart();
    }

    public void goBack(View view) {
        Intent productListIntent = new Intent(this, ProductList.class);
        startActivity(productListIntent);
    }

    public void goToBilling(View view) {
        Intent billingIntent = new Intent(this, Billing.class);
        startActivity(billingIntent);
    }

    public void calculateTotal() {
        CartDatabaseHelper cartDatabaseHelper = new CartDatabaseHelper(this);
        totalTextView = findViewById(R.id.cartProductTotalPrice);
        totalTextView.setText("Total Price: $"+String.valueOf(cartDatabaseHelper.calculateTotal()));
    }

    public void removeProductFromCart(View view) {
        CartDatabaseHelper cartDatabaseHelper = new CartDatabaseHelper(this);
        cartDatabaseHelper.removeProduct(view.getTag().toString());
        calculateTotal();
        showCart();
    }

    public void increaseQuantity(View view) {
        CartDatabaseHelper cartDatabaseHelper = new CartDatabaseHelper(this);
        cartDatabaseHelper.increaseQuantity(view.getTag().toString());
        calculateTotal();
        showCart();
    }

    public void decreaseQuantity(View view) {
        CartDatabaseHelper cartDatabaseHelper = new CartDatabaseHelper(this);
        cartDatabaseHelper.decreaseQuantity(view.getTag().toString());
        calculateTotal();
        showCart();
    }
}