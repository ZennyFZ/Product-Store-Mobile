package com.prm392.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.prm392.assignment.adapter.ProductAdapter;
import com.prm392.assignment.api.Endpoints;
import com.prm392.assignment.api.RetrofitClientInstance;
import com.prm392.assignment.model.ProductModel;
import com.prm392.assignment.util.CartDatabaseHelper;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    FirebaseAuth auth;
    TextView customerName;
    ProductAdapter productAdapter;
    RecyclerView productListView;
    ProgressDialog progressDialog;
    NavigationView navigationView;
    boolean isNavigationViewOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        showDisplayName();
        showCartQuantity();
        getAllProduct();
    }

    public void showDisplayName() {
        View headerView = navigationView.getHeaderView(0);
        customerName = headerView.findViewById(R.id.welcome_text);
        customerName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        customerName.setText("Hi, "+auth.getInstance().getCurrentUser().getDisplayName());
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

    public void searchProduct(View view) {
        EditText searchInput = findViewById(R.id.search);
        String keyword = searchInput.getText().toString();
        if (keyword.isEmpty()) {
            getAllProduct();
        } else {
            showLoading();
            Endpoints service = RetrofitClientInstance.getRetrofitInstance().create(Endpoints.class);
            Call<List<ProductModel>> productData = service.searchProduct(keyword);
            productData.enqueue(new Callback<List<ProductModel>>() {
                @Override
                public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                    Log.d("Search", response.body().toString());
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
    }


    public void showAllProducts(List<ProductModel> productList) {
        productListView = findViewById((R.id.productListView));
        productAdapter = new ProductAdapter(this, productList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductList.this);
        productListView.setLayoutManager(layoutManager);
        productListView.setAdapter(productAdapter);
    }

    public void goToProductDetail(View view) {
        Intent productDetailIntent = new Intent(this, ProductDetail.class);
        startActivity(productDetailIntent);
    }

    public void goToContactUs() {
        Intent contactUsIntent = new Intent(this, ContactUs.class);
        startActivity(contactUsIntent);
    }

    public void goToOrderList() {
        Intent orderListIntent = new Intent(this, OrderList.class);
        startActivity(orderListIntent);
    }

    public void goToProfile(View view) {
        Intent profileIntent = new Intent(this, Profile.class);
        startActivity(profileIntent);
    }

    public void openCartView(View view) {
        Intent cartIntent = new Intent(this, Cart.class);
        startActivity(cartIntent);
    }

    public void showCartQuantity() {
        CartDatabaseHelper cartDbHelper = new CartDatabaseHelper(this);
        int cartQuantity = cartDbHelper.getCartCount();
        TextView cartCount = findViewById(R.id.cartCount);
        cartCount.setText(String.valueOf(cartQuantity));
    }

    /////////////////////////// Navigation Drawer ///////////////////////////
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            closeNavigationView();
        } else if (item.getItemId() == R.id.nav_order) {
            goToOrderList();
        } else if (item.getItemId() == R.id.nav_about_us) {
            goToContactUs();
        } else if (item.getItemId() == R.id.nav_logout) {
            auth.getInstance().signOut();
            Intent loginIntent = new Intent(this, Login.class);
            startActivity(loginIntent);
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isNavigationViewOpen && !isInsideNavigationView(event)) {
                closeNavigationView();
                return true;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public boolean isInsideNavigationView(MotionEvent event) {
        Rect rect = new Rect();
        navigationView.getGlobalVisibleRect(rect);
        return rect.contains((int) event.getRawX(), (int) event.getRawY());
    }

    public void openNavigationView(View view) {
        navigationView.setVisibility(View.VISIBLE);
        isNavigationViewOpen = true;
    }

    public void closeNavigationView() {
        navigationView.setVisibility(View.INVISIBLE);
        isNavigationViewOpen = false;
    }
}