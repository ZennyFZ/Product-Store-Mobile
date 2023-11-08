package com.prm392.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prm392.assignment.adapter.CartAdapter;
import com.prm392.assignment.adapter.ProductBillingAdapter;
import com.prm392.assignment.api.Endpoints;
import com.prm392.assignment.api.RetrofitClientInstance;
import com.prm392.assignment.model.CartModel;
import com.prm392.assignment.model.OrderModel;
import com.prm392.assignment.util.CartDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Billing extends AppCompatActivity {
    RecyclerView productListView;
    ProductBillingAdapter productBillingAdapter;
    FirebaseAuth auth;
    EditText customerName,customerPhoneNumber,customerAddress;
    TextView billingTotalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        customerName = findViewById(R.id.customerName);
        customerPhoneNumber = findViewById(R.id.customerPhoneNumber);
        customerAddress = findViewById(R.id.customerAddress);
        showCart();
        calculateTotal();
    }

    public void showCart() {
        productListView = findViewById(R.id.productBillingRecyclerView);
        productBillingAdapter = new ProductBillingAdapter(this, new CartDatabaseHelper(this).getCartProducts());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Billing.this);
        productListView.setLayoutManager(layoutManager);
        productListView.setAdapter(productBillingAdapter);
    }

    public void calculateTotal() {
        CartDatabaseHelper cartDatabaseHelper = new CartDatabaseHelper(this);
        billingTotalPrice = findViewById(R.id.billingTotalPrice);
        billingTotalPrice.setText("Total Price: $"+String.valueOf(cartDatabaseHelper.calculateTotal()));
    }

    public void backToCart(View view) {
        Intent cartintent = new Intent(this, Cart.class);
        startActivity(cartintent);
    }

    public void checkout(View view) {
        //Get Customer Details
        if(customerName.getText().toString().isEmpty() || customerPhoneNumber.getText().toString().isEmpty() || customerAddress.getText().toString().isEmpty()){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        String customerID = auth.getInstance().getCurrentUser().getUid();
        String customerName = this.customerName.getText().toString();
        String phoneNumber = this.customerPhoneNumber.getText().toString();
        String address = this.customerAddress.getText().toString();

        //Get Products from Cart
        CartDatabaseHelper cartDatabaseHelper = new CartDatabaseHelper(this);
        List<CartModel> products = cartDatabaseHelper.getCartProducts();

        //Get Payment Method from Radio Group
        RadioGroup radioGroup = findViewById(R.id.paymentMethodRadioGroup);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        if(radioButtonID == -1){
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);
        String paymentMethod = radioButton.getText().toString();

        //Get Order Date
        String orderDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        //Create Order
        Endpoints service = RetrofitClientInstance.getRetrofitInstance().create(Endpoints.class);
        OrderModel orderModel = new OrderModel(customerID, customerName, phoneNumber, address, products, orderDate, paymentMethod);
        Call<OrderModel> orderData = service.createOrder(orderModel);

        //Log all the data
        Log.d("Customer ID", customerID);
        Log.d("Customer Name", customerName);
        Log.d("Customer Phone Number", phoneNumber);
        Log.d("Customer Address", address);
        Log.d("Payment Method", paymentMethod);
        Log.d("Order Date", orderDate);
        Log.d("Products", products.toString());
        orderData.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                Toast.makeText(Billing.this, "Order Placed", Toast.LENGTH_SHORT).show();
                cartDatabaseHelper.removeAllProducts();
                Intent productListIntent = new Intent(Billing.this, ProductList.class);
                startActivity(productListIntent);
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                Toast.makeText(Billing.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}