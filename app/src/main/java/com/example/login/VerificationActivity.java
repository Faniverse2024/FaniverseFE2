package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class VerificationActivity extends AppCompatActivity {

    private EditText editTextVerificationCode;
    private Button buttonLogin;
    private Button buttonResend;
    private TextView textViewTimer;
    private TextView textViewPhoneNumberInfo; // 추가된 부분

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        initUI();

        // MainActivity로부터 전달된 전화번호 받기
        String phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
        textViewPhoneNumberInfo.setText(phoneNumber + "에 인증번호를 보냈어요!"); // 텍스트 설정

        // 로그인 버튼 클릭 이벤트 처리
        buttonLogin.setOnClickListener(v -> {
            String verificationCode = editTextVerificationCode.getText().toString().trim();
            if (!verificationCode.isEmpty()) {
                // 인증번호가 유효한지 확인하는 로직 추가
                if (isValidVerificationCode(verificationCode)) {
                    Toast.makeText(VerificationActivity.this, "인증 성공!", Toast.LENGTH_SHORT).show();
                    // 로그인 성공 후 다음 액티비티로 이동하는 로직 추가
                } else {
                    Toast.makeText(VerificationActivity.this, "인증번호가 유효하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(VerificationActivity.this, "인증번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        // 재전송 버튼 클릭 이벤트 처리
        buttonResend.setOnClickListener(v -> {
            // 인증번호 재전송 로직 추가
            resendVerificationCode(phoneNumber);
        });

        // 타이머 시작
        startTimer();
    }

    // UI 요소 초기화 메서드
    private void initUI() {
        editTextVerificationCode = findViewById(R.id.editTextVerificationCode);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonResend = findViewById(R.id.buttonResend);
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewPhoneNumberInfo = findViewById(R.id.editTextPhoneNumberInfo); // 초기화
    }

    // 타이머 시작 메서드
    private void startTimer() {
        new CountDownTimer(180000, 1000) {
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished % 60000) / 1000;
                textViewTimer.setText(String.format("%02d:%02d", minutes, seconds));
            }

            public void onFinish() {
                textViewTimer.setText("00:00");
                buttonResend.setEnabled(true);
                Toast.makeText(VerificationActivity.this, "인증번호 유효 시간이 만료되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    // 인증번호 유효성 검사 메서드
    private boolean isValidVerificationCode(String code) {
        // 실제 인증번호 유효성 검사 로직 추가
        return code.matches("\\d{6}");
    }

    // 인증번호 재전송 메서드
    private void resendVerificationCode(String phoneNumber) {
        // 실제 인증번호 재전송 로직 추가
        Toast.makeText(this, phoneNumber + "으로 인증번호가 재전송되었습니다.", Toast.LENGTH_SHORT).show();
        buttonResend.setEnabled(false);
        startTimer();
    }
}
