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

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    Context context;
    List<CartModel> cartItems;

    public OrderDetailAdapter(Context context, List<CartModel> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orderp_view, parent, false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.OrderDetailViewHolder holder, int position) {
        // Bind the cart item data to the views in the layout
        CartModel cartItem = cartItems.get(position);
        holder.OD_productNameTextView.setText(cartItem.getProductName());
        Glide.with(context).load(cartItem.getProductImage()).into(holder.OD_productImageView);
        holder.OD_productQuantityPrice.setText("Quantity: " + String.valueOf(cartItem.getQuantity()) + "\n" + "Price: $" + String.valueOf(cartItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        ImageView OD_productImageView;
        TextView OD_productNameTextView, OD_productQuantityPrice;

        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            OD_productImageView = itemView.findViewById(R.id.orderp_productImage);
            OD_productNameTextView = itemView.findViewById(R.id.orderp_productName);
            OD_productQuantityPrice = itemView.findViewById(R.id.orderp_productQuantityPrice);
        }
    }
}
