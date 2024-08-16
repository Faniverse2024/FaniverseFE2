package com.example.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getItemViewType(int position) {
        // Product의 type 필드를 사용하여 뷰 유형을 반환
        Product product = productList.get(position);
        int type = product.getType();  // Product 클래스에 정의된 getType() 메서드를 사용

        if (type == 1) {
            return 1; // 첫 번째 유형
        } else if (type == 2) {
            return 2; // 두 번째 유형
        } else {
            return 3; // 세 번째 유형 또는 기본 유형
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == 1) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.newjeans_doll, parent, false);
        } else if (viewType == 2) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.newjeans_minji, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.newjeans_bag, parent, false);
        }
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, productPrice;

        public ProductViewHolder(View view) {
            super(view);
            productName = view.findViewById(R.id.product_name);
            productPrice = view.findViewById(R.id.product_price);
        }
    }
}
