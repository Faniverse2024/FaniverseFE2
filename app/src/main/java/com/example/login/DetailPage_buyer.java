package com.example.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailPage_buyer extends AppCompatActivity {

    private TextView productName, sellerName, productDescription, productPrice;
    private ImageView productImage, sellerAvatar;

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

        // Intent로부터 데이터 수신
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String category = intent.getStringExtra("category");
            String price = intent.getStringExtra("price");
            String description = intent.getStringExtra("description");
            String imageUriString = intent.getStringExtra("imageUri");
            String seller = intent.getStringExtra("sellerName");

            // 데이터를 UI에 설정
            productName.setText(title != null ? title : "Unknown");
            sellerName.setText(seller != null ? seller : "Unknown Seller");
            productPrice.setText(price != null ? price : "No Price");
            productDescription.setText(description != null ? description : "No Description");

            // 이미지 URI가 있는 경우 ImageView에 설정
            if (imageUriString != null && productImage != null) {
                Uri imageUri = Uri.parse(imageUriString);
                productImage.setImageURI(imageUri);
            } else if (productImage != null) {
                // 기본 이미지 또는 아무것도 설정하지 않을 수 있습니다.
                productImage.setImageResource(R.drawable.aespa_icon); // placeholder_image는 기본 이미지입니다.
            }
        }
    }
}
