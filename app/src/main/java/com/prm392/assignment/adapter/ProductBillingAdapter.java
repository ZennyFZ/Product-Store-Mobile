package com.prm392.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prm392.assignment.R;
import com.prm392.assignment.model.CartModel;

import java.util.List;

public class ProductBillingAdapter extends RecyclerView.Adapter<ProductBillingAdapter.CartViewHolder> {
    Context context;
    List<CartModel> cartItems;

    public ProductBillingAdapter(Context context, List<CartModel> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ProductBillingAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_billing_view, parent, false);
        return new ProductBillingAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductBillingAdapter.CartViewHolder holder, int position) {
        CartModel cartItem = cartItems.get(position);
        holder.productNameTextView.setText(cartItem.getProductName());
        Glide.with(context).load(cartItem.getProductImage()).into(holder.productImageView);
        holder.priceTextView.setText("$"+String.valueOf(cartItem.getPrice()));
        holder.quantityTextView.setText("x"+String.valueOf(cartItem.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView, priceTextView, quantityTextView;
        ImageView productImageView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.cartProductName);
            productImageView = itemView.findViewById(R.id.cartProductImage);
            priceTextView = itemView.findViewById(R.id.cartProductPrice);
            quantityTextView = itemView.findViewById(R.id.cartProductQuantity);
        }
    }
}
