package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class AuctionAttend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_attend);
    }

    // 버튼 클릭 시 호출되는 메서드
    public void onJoinAuctionClick(View view) {
        // 경매 참여 완료 신호를 보내고 현재 액티비티를 종료
        Intent intent = new Intent();
        intent.putExtra("AUCTION_JOINED", true);
        setResult(RESULT_OK, intent);
        finish(); // 현재 액티비티를 종료하고 이전 액티비티로 돌아감
    }
}

