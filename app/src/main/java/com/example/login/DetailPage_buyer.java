package com.example.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.login.api.ApiService;
import com.example.login.model.ProductDetailsResponse;

import retrofit2.Call;
import com.example.login.api.ApiService;
import com.example.login.model.ProductDetailsResponse;
import com.example.login.network.RetrofitClient;

import java.io.IOException;

import retrofit2.Callback;
import retrofit2.Response;

public class DetailPage_buyer extends AppCompatActivity {

    private TextView productName, sellerName, productDescription, productPrice;
    private ImageView productImage, sellerAvatar;
    private Button Chatroom;
    private Long productId;
    private Long sellerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page_buyer);

        // UI 요소 초기화
        productName = findViewById(R.id.product_name);
        sellerName = findViewById(R.id.seller_name);
        productDescription = findViewById(R.id.product_description);
        productImage = findViewById(R.id.product_image);
        sellerAvatar = findViewById(R.id.seller_avatar);
        productPrice = findViewById(R.id.product_price);
        Chatroom = findViewById(R.id.btn_chat);


         /* HomeActivity 에서 DetailPage_buyer 호출 구현 이후 적용
        // Intent로 productId 받기
        Intent intent = getIntent();

        if (intent != null) {
            Long productId = intent.getLongExtra("productId", -1); // productId만 받음
            if (productId != -1) {
                // API 호출로 데이터 로드
                loadProductDetails(productId);
            } else {
                Toast.makeText(this, "상품 ID가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }*/


        //임시로 지정한 productId
        productId = 1L;
        loadProductDetails(productId);



        /************* 채팅 담당 파트 *********/
        // "채팅하기" 버튼 클릭 리스너 설정
        Chatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ChatRoomActivity로 이동하는 Intent 생성
                Intent chatIntent = new Intent(DetailPage_buyer.this, ChatRoomActivity.class);

                // 필요에 따라 추가 데이터를 전달
                chatIntent.putExtra("CHAT_ROOM_ID", "default_room_id"); // 예시로 고정된 값 사용
                chatIntent.putExtra("CHAT_ROOM_NAME", "뉴진스사랑"); // 예시로 고정된 값 사용

                startActivity(chatIntent);
            }
        });
    }

    private void loadProductDetails(Long productId) {

        ApiService apiService = RetrofitClient.getApiService();

        // API 호출
        apiService.getProductDetail(productId).enqueue(new Callback<ProductDetailsResponse>() {
            @Override
            public void onResponse(Call<ProductDetailsResponse> call, Response<ProductDetailsResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ProductDetailsResponse productDetail = response.body();

                    // 데이터를 UI에 설정
                    productName.setText(productDetail.getTitle());
                    sellerName.setText(productDetail.getUserName());
                    productPrice.setText(String.format("%,.0f원", productDetail.getPrice())); // 가격 포맷팅
                    productDescription.setText(productDetail.getContent());

                    // 이미지 설정
                    if (productDetail.getImageUrl() != null) {
                        Glide.with(DetailPage_buyer.this)
                                .load(productDetail.getImageUrl())
                                .into(productImage);
                    } else {
                        Toast.makeText(DetailPage_buyer.this, "데이터를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductDetailsResponse> call, Throwable t) {
                Log.e("API Call", "Network error: " + t.getMessage());
                Toast.makeText(DetailPage_buyer.this, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}