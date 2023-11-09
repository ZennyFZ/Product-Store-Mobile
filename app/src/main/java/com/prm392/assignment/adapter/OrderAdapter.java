package com.prm392.assignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prm392.assignment.OrderDetail;
import com.prm392.assignment.R;
import com.prm392.assignment.model.CartModel;
import com.prm392.assignment.model.OrderModel;
import com.prm392.assignment.util.DateConverter;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    Context context;
    List<OrderModel> orderItems;

    public OrderAdapter(Context context, List<OrderModel> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
    }
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_view, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        // Bind the order item data to the views in the layout
        OrderModel orderItem = orderItems.get(position);
        String convertedDate = DateConverter.convertDateFormat(orderItem.getOrderDate());
        holder.orderTitle.setText("Order ID: "+orderItem.getOrderId());
        holder.orderDetails.setText("Date: "+convertedDate+"\n"+"Customer: "+orderItem.getCustomerName()+"\n"+"Phone: "+orderItem.getPhoneNumber());
        holder.viewDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CartModel> products = orderItem.getProducts();
                Intent intent = new Intent(context, OrderDetail.class);
                intent.putExtra("ODproducts", (java.io.Serializable) products);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderTitle, orderDetails;
        Button viewDetailButton;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderTitle = itemView.findViewById(R.id.orderTitle);
            orderDetails = itemView.findViewById(R.id.orderDetails);
            viewDetailButton = itemView.findViewById(R.id.orderDetailsButton);
        }
    }

}
