package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class KeywordRegistrationActivity extends AppCompatActivity {

    private EditText etKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_register);

        // UI 요소 초기화
        etKeyword = findViewById(R.id.et_keyword);
        Button btnRegister = findViewById(R.id.btn_register);

        // 등록 버튼 클릭 리스너 설정
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = etKeyword.getText().toString().trim();
                if (!keyword.isEmpty()) {
                    // 키워드를 InterestActivity로 전달
                    Intent intent = new Intent();
                    intent.putExtra("keyword", keyword);
                    setResult(RESULT_OK, intent);
                    finish(); // 현재 액티비티 종료하고 이전 액티비티로 돌아가기
                } else {
                    Toast.makeText(KeywordRegistrationActivity.this, "Please enter a keyword", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
