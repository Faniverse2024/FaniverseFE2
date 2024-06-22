package com.example.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class VerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        TextView textViewInstructions = findViewById(R.id.textViewInstructions);
        EditText editTextVerificationCode = findViewById(R.id.editTextVerificationCode);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Retrieve the phone number passed from MainActivity
        String phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
        textViewInstructions.setText(phoneNumber + "에 인증번호를 보냈어요!");

        buttonLogin.setOnClickListener(v -> {
            String verificationCode = editTextVerificationCode.getText().toString().trim();
            if (!verificationCode.isEmpty()) {
                // Handle the login process
            }
        });
    }
}
