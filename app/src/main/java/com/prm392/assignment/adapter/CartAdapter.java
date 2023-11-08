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
import com.prm392.assignment.model.CartModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    List<CartModel> cartItems;

    public CartAdapter(Context context, List<CartModel> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_view, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        // Bind the cart item data to the views in the layout
        CartModel cartItem = cartItems.get(position);
        holder.productNameTextView.setText(cartItem.getProductName());
        Glide.with(context).load(cartItem.getProductImage()).into(holder.productImageView);
        holder.priceTextView.setText("$"+String.valueOf(cartItem.getPrice()));
        holder.quantityTextView.setText(String.valueOf(cartItem.getQuantity()));

        // Set the tag of the buttons to the product id
        holder.cartProductDeleteButton.setTag(cartItem.getProductName());
        holder.cartProductQuantityMinusButton.setTag(cartItem.getProductName());
        holder.cartProductQuantityPlusButton.setTag(cartItem.getProductName());
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView, priceTextView, quantityTextView;
        ImageView productImageView, cartProductDeleteButton;
        Button cartProductQuantityMinusButton, cartProductQuantityPlusButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.cartProductName);
            productImageView = itemView.findViewById(R.id.cartProductImage);
            priceTextView = itemView.findViewById(R.id.cartProductPrice);
            quantityTextView = itemView.findViewById(R.id.cartProductQuantity);

            cartProductQuantityMinusButton = itemView.findViewById(R.id.cartProductQuantityMinus);
            cartProductQuantityPlusButton = itemView.findViewById(R.id.cartProductQuantityPlus);
            cartProductDeleteButton = itemView.findViewById(R.id.cartProductDelete);
        }
    }
}
