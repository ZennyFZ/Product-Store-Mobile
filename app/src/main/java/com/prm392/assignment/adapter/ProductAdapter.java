package com.prm392.assignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prm392.assignment.ProductDetail;
import com.prm392.assignment.R;
import com.prm392.assignment.model.ProductModel;
import com.prm392.assignment.util.CartDatabaseHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    List<ProductModel> productList;
    CartDatabaseHelper cartDbHelper;

    public ProductAdapter(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
        cartDbHelper = new CartDatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // gán view
        View view = LayoutInflater.from(context).inflate(R.layout.product_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Gán dữ liêu
        ProductModel product = productList.get(position);

        holder.productName.setText(product.getProductName());
        holder.productPrice.setText("$" + product.getProductPrice());
        Glide.with(context).load(product.getProductImage()).into(holder.productImage);
        holder.productDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductModel selectedProduct = productList.get(position);
                Bundle productDetailBundle = new Bundle();
                productDetailBundle.putString("productName", selectedProduct.getProductName());
                productDetailBundle.putString("productPrice", selectedProduct.getProductPrice());
                productDetailBundle.putString("productImage", selectedProduct.getProductImage());
                productDetailBundle.putString("productDescription", selectedProduct.getProductDescription());
                productDetailBundle.putString("productCategory", selectedProduct.getCategory());
                productDetailBundle.putString("productBrand", selectedProduct.getBrand());
                productDetailBundle.putString("quantity", selectedProduct.getQuantity());
                productDetailBundle.putString("capacity", selectedProduct.getCapacity());
                Intent productDetailIntent = new Intent(context, ProductDetail.class);
                productDetailIntent.putExtra("productDetail", productDetailBundle);
                context.startActivity(productDetailIntent);
            }
        });

        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductModel selectedProduct = productList.get(position);
                cartDbHelper.addProductToCart(selectedProduct.getProductName(), Double.parseDouble(selectedProduct.getProductPrice()), 1);
                Toast.makeText(v.getContext(), "Added "+selectedProduct.getProductName()+" to cart!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice;
        Button productDetailButton, addToCartButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDetailButton = itemView.findViewById(R.id.productDetailButton);
            addToCartButton = itemView.findViewById(R.id.productAddToCartButton);
        }
    }
}
