package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextPhoneNumber;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        buttonNext.setOnClickListener(v -> {
            String phoneNumber = editTextPhoneNumber.getText().toString().trim();
            if (!phoneNumber.isEmpty()) {
                if (isValidPhoneNumber(phoneNumber)) {
                    // 전화번호를 VerificationActivity로 전달
                    Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
                    intent.putExtra("PHONE_NUMBER", phoneNumber);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "유효한 전화번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "전화번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // UI 요소 초기화 메서드
    private void initUI() {
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonNext = findViewById(R.id.buttonNext);
    }

    // 전화번호 유효성 검사 메서드
    private boolean isValidPhoneNumber(String phoneNumber) {
        // 간단한 전화번호 형식 검사 (예: 10~11자리 숫자)
        return phoneNumber.matches("\\d{10,11}");
    }
}
