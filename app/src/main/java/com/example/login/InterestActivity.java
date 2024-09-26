package com.example.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.login.api.ApiService;
import com.example.login.model.KeywordDto;
import com.example.login.model.WishlistProductDto;
import com.example.login.network.RetrofitClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InterestActivity extends AppCompatActivity {

    private LinearLayout homeLayout;
    private LinearLayout interestLayout;
    private LinearLayout chatLayout;
    private LinearLayout communityLayout;
    private LinearLayout mypageLayout;
    private Button btnInterest;

    private List<KeywordDto> keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        // 사용자 키워드 조회 호출
        fetchUserKeywords();

        // 위시리스트 상품 조회 호출
        fetchWishlistProducts();

        // 브로드캐스트 리시버 등록
        LocalBroadcastManager.getInstance(this).registerReceiver(keywordReceiver, new IntentFilter("KEYWORD_ADDED"));



        btnInterest = findViewById(R.id.btn_interest_keyword);

        // 홈 레이아웃 클릭 이벤트 설정
        homeLayout = findViewById(R.id.home_layout);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InterestActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // 관심 레이아웃 클릭 이벤트 설정
        interestLayout = findViewById(R.id.interest_layout);
        interestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InterestActivity.this, InterestActivity.class);
                startActivity(intent);
            }
        });

        // 채팅 레이아웃 클릭 이벤트 설정
        chatLayout = findViewById(R.id.chat_layout);
        chatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InterestActivity.this,  ChatHome.class);
                startActivity(intent);
            }
        });

        // 커뮤니티 레이아웃 클릭 이벤트 설정
        communityLayout = findViewById(R.id.community_layout);
        communityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InterestActivity.this,  CommunityActivity.class);
                startActivity(intent);
            }
        });

        // 마이페이지 레이아웃 클릭 이벤트 설정
        mypageLayout = findViewById(R.id.mypage_layout);
        mypageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InterestActivity.this,  MyPage.class);
                startActivity(intent);
            }
        });

        // 업로드 버튼 클릭 리스너 설정
        btnInterest.setOnClickListener(view -> showPopup(view));
    }


    // 키워드~

    private void showPopup(View anchorView) {
        // 팝업 레이아웃을 인플레이트
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_keyword, null);

        // PopupWindow를 생성하고 설정
        final PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // 키워드 등록 버튼
        Button btnKeywordRegister = popupView.findViewById(R.id.btn_keyword);
        btnKeywordRegister.setOnClickListener(v -> {
            Intent intent = new Intent(InterestActivity.this, KeywordRegistrationActivity.class);
            startActivity(intent);
            popupWindow.dismiss(); // 팝업 닫기
        });

        popupWindow.showAsDropDown(anchorView, 0, -anchorView.getHeight()-190);
    }



    private BroadcastReceiver keywordReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 새로 등록한 키워드 수신
            String newKeyword = intent.getStringExtra("newKeyword");
            fetchUserKeywords(); // 키워드 목록 다시 가져와서 업데이트
        }
    };


    private void fetchUserKeywords() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<KeywordDto>> call = apiService.getUserKeywords();

        call.enqueue(new Callback<List<KeywordDto>>() {
            @Override
            public void onResponse(Call<List<KeywordDto>> call, Response<List<KeywordDto>> response) {
                if (response.isSuccessful()) {
                    List<KeywordDto> keywords = response.body();
                    //keywords = response.body(); // 클래스 변수에 할당
                    displayKeywords(keywords);
                } else {
                    Log.e("InterestActivity", "키워드 로딩 실패: " + response.code() + " - " + response.message());
                    Toast.makeText(InterestActivity.this, "키워드 로딩 실패",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<KeywordDto>> call, Throwable t) {
                Log.e("InterestActivity", "오류 발생: " + t.getMessage());
                Toast.makeText(InterestActivity.this, "키워드 로딩 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void displayKeywords(List<KeywordDto> keywords) {
        LinearLayout keywordsContainer = findViewById(R.id.ll_keywords_container);
        keywordsContainer.removeAllViews(); // 기존 키워드 클리어

        for (KeywordDto keyword : keywords) {
            TextView keywordView = new TextView(this);
            keywordView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            keywordView.setText("#" + keyword.getWord());
            keywordView.setTextSize(14);
            keywordView.setPadding(8, 8, 8, 8);
            keywordView.setTextColor(Color.BLACK);

            // 키워드 클릭 시 처리 (KeywordProductListActivity로 이동)
            keywordView.setOnClickListener(v -> {
                Intent intent = new Intent(InterestActivity.this, KeywordProductListActivity.class);
                intent.putExtra("keyword", keyword.getWord()); // 키워드 전달
                startActivity(intent);
            });

            keywordsContainer.addView(keywordView); // 키워드 추가
        }
    }


    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(keywordReceiver);
        super.onDestroy(); // 부모 클래스의 onDestroy 호출
    }


    // 위시리스트~

    private void fetchWishlistProducts() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<WishlistProductDto>> call = apiService.getWishlistProducts();

        call.enqueue(new Callback<List<WishlistProductDto>>() {
            @Override
            public void onResponse(Call<List<WishlistProductDto>> call, Response<List<WishlistProductDto>> response) {
                if (response.isSuccessful()) {
                    List<WishlistProductDto> wishlistProducts = response.body();

                    if (wishlistProducts != null) {
                        for (WishlistProductDto product : wishlistProducts) {
                            Log.d("InterestActivity", "fetch 시 wishlistId: " + product.getWishlistId());

                            Log.d("InterestActivity", "userId: " + product.getUserId());
                            Log.d("InterestActivity", "productId: " + product.getProductId());
                            Log.d("InterestActivity", "title: " + product.getTitle());
                            Log.d("InterestActivity", "content: " + product.getContent());
                            Log.d("InterestActivity", "category: " + product.getCategory());
                            Log.d("InterestActivity", "imageUrl: " + product.getImageUrl());
                            Log.d("InterestActivity", "price: " + product.getPrice());

                        }
                    } else {
                        Log.d("InterestActivity", "위시리스트 상품이 없습니다.");
                    }
                    displayWishlistProducts(wishlistProducts);
                } else {
                    Log.e("InterestActivity", "위시리스트 로딩 실패: " + response.code() + " - " + response.message());
                    Toast.makeText(InterestActivity.this, "위시리스트 로딩 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<WishlistProductDto>> call, Throwable t) {
                Log.e("InterestActivity", "오류 발생: " + t.getMessage());
                Toast.makeText(InterestActivity.this, "위시리스트 로딩 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayWishlistProducts(List<WishlistProductDto> wishlistProducts) {
        LinearLayout wishlistContainer = findViewById(R.id.wishlist_product_container);
        wishlistContainer.removeAllViews();

        if (wishlistProducts == null || wishlistProducts.isEmpty()) {
            // 위시리스트가 비어있을 경우
            TextView emptyMessage = new TextView(this);
            emptyMessage.setText("위시리스트에 상품이 없습니다.");
            emptyMessage.setTextSize(16);
            emptyMessage.setTextColor(Color.GRAY);
            wishlistContainer.addView(emptyMessage);
        } else {
            for (WishlistProductDto wishlistProduct : wishlistProducts) {
                View wishlistProductView = getLayoutInflater().inflate(R.layout.item_wishlist_product, wishlistContainer, false);

                // 상품 정보를 레이아웃에 설정
                ImageView wishlistProductImage = wishlistProductView.findViewById(R.id.wishlist_product_image);
                TextView wishlistProductTitle = wishlistProductView.findViewById(R.id.wishlist_product_title);
                TextView wishlistProductPrice = wishlistProductView.findViewById(R.id.wishlist_product_price);
                Button removeButton = wishlistProductView.findViewById(R.id.remove_button);

                wishlistProductTitle.setText(wishlistProduct.getTitle());
                wishlistProductPrice.setText(String.format("%,d원", (int) wishlistProduct.getPrice()));

                Log.d("WishlistProduct", "imageUrl: " + wishlistProduct.getImageUrl());

                // GCS imageUrl 사용하여 Glide로 이미지 로드
                Glide.with(this)
                        .load(wishlistProduct.getImageUrl())
                        .placeholder(R.drawable.placeholder) // 로딩 중에 보여줄 이미지 (선택)
                        .error(R.drawable.error_image) // 오류 발생 시 보여줄 이미지 (선택)
                        .into(wishlistProductImage);

                removeButton.setOnClickListener(v -> {
                    long wishlistId = wishlistProduct.getWishlistId(); // wishlistId 가져오기
                    Log.d("InterestActivity", "삭제 버튼 클릭 시 wishlistId: " + wishlistId);
                    removeWishlistItem(wishlistId, wishlistProductView);
                    //Toast.makeText(InterestActivity.this, "위시리스트에서 제거!", Toast.LENGTH_SHORT).show();
                });

                wishlistContainer.addView(wishlistProductView);
            }
        }
    }

    private void removeWishlistItem(long wishlistId, View wishlistProductView) {
        Log.d("InterestActivity", "wishlistId: " + wishlistId);
        Log.d("InterestActivity", "wishlistProductView: " + wishlistProductView);
        ApiService apiService = RetrofitClient.getApiService();
        Call<String> call = apiService.removeWishlistItem(wishlistId);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // 삭제 성공 시 해당 뷰 제거
                    Log.d("InterestActivity", "삭제 성공: " + response.message());
                    ((ViewGroup) wishlistProductView.getParent()).removeView(wishlistProductView);
                    Toast.makeText(InterestActivity.this, "위시리스트에서 삭제!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("InterestActivity", "위시리스트 상품 삭제 실패: " + response.code() + " - " + response.message());
                    // errorBody 확인
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("InterestActivity", "Error Body: " + errorBody);
                        } catch (IOException e) {
                            Log.e("InterestActivity", "Error reading error body: " + e.getMessage());
                        }
                    }

                    Toast.makeText(InterestActivity.this, "위시리스트 삭제 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("InterestActivity", "오류 발생: " + t.getMessage());
                Toast.makeText(InterestActivity.this, "삭제 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}