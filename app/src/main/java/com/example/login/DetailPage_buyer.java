package com.example.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DetailPage_buyer extends AppCompatActivity {

    private TextView productName, sellerName, productDescription, productPrice;
    private ImageView productImage, sellerAvatar;
    private Button Chatroom;

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
            productName.setText(title != null ? title : "뉴진스 가방(끈 조절 가능)");
            sellerName.setText(seller != null ? seller : "뉴진스사랑");
            productPrice.setText(price != null ? price : "39,000원");
            productDescription.setText(description != null ? description : "뉴진스 데뷔앨범 원형가방 버전입니다. 안에 CD는 없고 가방 단독으로 판매합니다. 급전 필요해서 너무 아끼는 건데 내놔요 ㅜ");

            // 이미지 URI가 있는 경우 ImageView에 설정
            if (imageUriString != null && productImage != null) {
                Uri imageUri = Uri.parse(imageUriString);
                productImage.setImageURI(imageUri);
            }
        }

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
}
