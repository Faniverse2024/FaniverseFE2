package com.example.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailPage_buyer1 extends AppCompatActivity {

    private TextView productName, sellerName, productDescription, productPrice;
    private ImageView productImage, sellerAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page_buyer1);

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
            String price = intent.getStringExtra("price");
            String description = intent.getStringExtra("description");
            String imageUriString = intent.getStringExtra("imageUri");
            String seller = intent.getStringExtra("sellerName");


            // 데이터를 UI에 설정 (빈 문자열이나 null일 경우 디폴트 값 사용)
            productName.setText((title != null && !title.trim().isEmpty()) ? title : "에스파 아마겟돈 앨범 새상품");
            sellerName.setText((seller != null && !seller.trim().isEmpty()) ? seller : "칼윈러버");
            productPrice.setText((price != null && !price.trim().isEmpty()) ? price : "23000");
            productDescription.setText((description != null && !description.trim().isEmpty()) ? description : "에스파 아마겟돈 앨범 새상품이고 포카 뭔지 몰라요.. 안 팔리면 개인소장 할 거예요 네고문의 사절");

            // 이미지 URI가 있는 경우 ImageView에 설정
            if (imageUriString != null && !imageUriString.trim().isEmpty() && productImage != null) {
                Uri imageUri = Uri.parse(imageUriString);
                productImage.setImageURI(imageUri);
            }
        }
    }
}
