package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AuctionPage_buyer extends AppCompatActivity {

    private Button btnAuctionAtten;
    private static final int AUCTION_ATTEND_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auction_page_buyer);

        // btn_auctionatten 버튼을 레이아웃에서 찾기
        btnAuctionAtten = findViewById(R.id.btn_auctionatten);

        // 윈도우 인셋 적용
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }

    // btn_auctionatten 버튼이 클릭되었을 때 호출되는 메서드
    public void onAttendAuctionClick(View view) {
        Intent intent = new Intent(AuctionPage_buyer.this, AuctionAttend.class);
        startActivityForResult(intent, AUCTION_ATTEND_REQUEST_CODE);
    }

    // AuctionAttend 액티비티에서 돌아올 때 결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUCTION_ATTEND_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            boolean auctionJoined = data.getBooleanExtra("AUCTION_JOINED", false);

            if (auctionJoined) {
                // 버튼 텍스트 변경 및 비활성화
                btnAuctionAtten.setText("경매 참여 완료");
                btnAuctionAtten.setEnabled(false);
                btnAuctionAtten.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            }
        }
    }
}

