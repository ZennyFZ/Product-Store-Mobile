package com.prm392.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.prm392.assignment.util.CartDatabaseHelper;

public class ProductDetail extends AppCompatActivity {

    ImageView productImage;
    TextView productName, productPrice, productDescription, productCategory, productBrand, productQuantity, productCapacity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productDescription = findViewById(R.id.product_description);
        productCategory = findViewById(R.id.product_category);
        productBrand = findViewById(R.id.product_brand);
        productQuantity = findViewById(R.id.quantity);
        productCapacity = findViewById(R.id.capacity);
        showProductDetail(productImage);
    }

    public void showProductDetail(View view) {
        Intent productDetailIntent = getIntent();
        Bundle productDetailBundle = productDetailIntent.getBundleExtra("productDetail");
        String productName = productDetailBundle.getString("productName");
        String productPrice = productDetailBundle.getString("productPrice");
        String productImage = productDetailBundle.getString("productImage");
        String productDescription = productDetailBundle.getString("productDescription");
        String productCategory = productDetailBundle.getString("productCategory");
        String productBrand = productDetailBundle.getString("productBrand");
        String productQuantity = productDetailBundle.getString("quantity");
        String productCapacity = productDetailBundle.getString("capacity");

        Glide.with(this).load(productImage).into(this.productImage);
        this.productName.setText(productName);
        this.productPrice.setText("$"+productPrice);
        this.productDescription.setText(productDescription);
        this.productCategory.setText("Category: "+productCategory+" ");
        this.productBrand.setText("Brand: "+productBrand+" ");
        this.productQuantity.setText("Quantity: "+productQuantity+" ");
        this.productCapacity.setText("Capacity: "+productCapacity+"ml ");
    }

    public void backToProductList(View view) {
        Intent backToProductListIntent = new Intent(this, ProductList.class);
        startActivity(backToProductListIntent);
    }

    public void addToCart(View view) {
        Intent productDetailIntent = getIntent();
        Bundle productDetailBundle = productDetailIntent.getBundleExtra("productDetail");
        String productName = productDetailBundle.getString("productName");
        String productPrice = productDetailBundle.getString("productPrice");
        String productImage = productDetailBundle.getString("productImage");

        CartDatabaseHelper cart = new CartDatabaseHelper(this);
        cart.addProductToCart(productName, productImage, Double.parseDouble(productPrice), 1);
        Toast.makeText(this, "Added "+productName+" to cart!", Toast.LENGTH_SHORT).show();
    }
}