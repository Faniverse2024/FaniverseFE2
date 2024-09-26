package com.example.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.api.ApiService;
import com.example.login.model.KeywordProductDto;
import com.example.login.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.bumptech.glide.Glide;


public class KeywordProductListActivity extends AppCompatActivity {
    private LinearLayout productsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_product_list);

        // productContainer 초기화
        productsContainer = findViewById(R.id.products_container);

       // Intent에서 전달된 키워드 받기
        String keyword = getIntent().getStringExtra("keyword");

        if (keyword != null) {
            // 해당 키워드로 상품 목록을 불러옴
            fetchProductsByKeyword(keyword);
        }
    }

    private void fetchProductsByKeyword(String keyword) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<KeywordProductDto>> call = apiService.getProductsByKeyword(keyword);

        call.enqueue(new Callback<List<KeywordProductDto>>() {
            @Override
            public void onResponse(Call<List<KeywordProductDto>> call, Response<List<KeywordProductDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<KeywordProductDto> products = response.body();
                    displayProducts(products);
                } else {
                    displayProducts(null); // 상품이 없을 때 null 처리
                }
            }

            @Override
            public void onFailure(Call<List<KeywordProductDto>> call, Throwable t) {
                Log.e("ProductListActivity", "상품 목록 로딩 오류 발생: " + t.getMessage());
                Toast.makeText(KeywordProductListActivity.this, "상품 목록 로딩 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayProducts(List<KeywordProductDto> products) {
        if (productsContainer != null) {
            productsContainer.removeAllViews(); // 기존 뷰 제거

            // 뒤로가기 버튼
            Button backButton = new Button(this);
            backButton.setText("뒤로가기");
            backButton.setOnClickListener(v -> finish()); // 버튼 클릭 시 현재 액티비티 종료
            productsContainer.addView(backButton); // 뒤로가기 버튼을 레이아웃에 추가

            if (products == null || products.isEmpty()) {
                // 상품이 없을 때
                TextView noProductsTextView = new TextView(this);
                noProductsTextView.setText("상품이 없습니다.");
                productsContainer.addView(noProductsTextView); // 메시지를 레이아웃에 추가
            } else {
                // 상품이 있을 때
                for (KeywordProductDto product : products) {
                    LinearLayout productLayout = new LinearLayout(this);
                    productLayout.setOrientation(LinearLayout.VERTICAL);
                    productLayout.setPadding(16, 16, 16, 16);

                    // title
                    TextView productTitle = new TextView(this);
                    productTitle.setText(product.getTitle());
                    productTitle.setTextSize(16);

                    // title 클릭 시 상품 상세 페이지로 이동 (DetailPage_buyer)
                    productTitle.setOnClickListener(v -> {
                        // 상품의 id를 상세 페이지로 전달
                        Intent intent = new Intent(KeywordProductListActivity.this, DetailPage_buyer.class);
                        intent.putExtra("productId", product.getProductId()); // productId 전달
                        startActivity(intent);
                    });

                    productLayout.addView(productTitle);

                    // category
                    TextView productCategory = new TextView(this);
                    productCategory.setText(product.getCategory());
                    productCategory.setTextSize(14);
                    productCategory.setTextColor(Color.GRAY);
                    productLayout.addView(productCategory);

                    // image
                    ImageView productImage = new ImageView(this);
                    Glide.with(this)
                            .load(product.getImageUrl())
                            .into(productImage);
                    productLayout.addView(productImage);

                    // price
                    TextView productPrice = new TextView(this);
                    productPrice.setText(String.format("%,.0f 원", product.getPrice()));
                    productLayout.addView(productPrice);

                    productsContainer.addView(productLayout);
                }
            }

        } else {
            Log.e("KeywordProductListActivity", "productContainer is null");
        }

    }
}

