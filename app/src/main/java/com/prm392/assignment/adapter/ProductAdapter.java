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
import com.prm392.assignment.R;
import com.prm392.assignment.model.ProductModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    Context context;
    ArrayList<ProductModel> productList;
    public ProductAdapter(Context context, ArrayList<ProductModel> productList) {
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

        //Tên sản phẩm
        holder.productName.setText(product.getProductName());

        //Tiền Việt
        Locale locale = new Locale("vn", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        holder.productPrice.setText(currencyFormatter.format(product.getProductPrice()));

        //Hình ảnh
        String productImage = product.getProductImage();
        int productImageResourceId = context.getResources().getIdentifier(productImage, "drawable", context.getPackageName());
        holder.productImage.setImageResource(productImageResourceId);
    }

    @Override
    public int getItemCount() {
        return 0;
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
