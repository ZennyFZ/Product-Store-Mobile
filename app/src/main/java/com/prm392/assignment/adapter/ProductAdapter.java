package com.prm392.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prm392.assignment.R;
import com.prm392.assignment.model.ProductModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    Context context;
    List<ProductModel> productList;
    public ProductAdapter(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
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
        holder.productPrice.setText("$"+product.getProductPrice());
        Glide.with(context).load(product.getProductImage()).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice;
        Button productDetailButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
              productImage = itemView.findViewById(R.id.productImage);
              productName = itemView.findViewById(R.id.productName);
              productPrice = itemView.findViewById(R.id.productPrice);
              productDetailButton = itemView.findViewById(R.id.productDetailButton);
        }
    }
}
