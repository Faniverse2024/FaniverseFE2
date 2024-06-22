package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        Button buttonNext = findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(v -> {
            String phoneNumber = editTextPhoneNumber.getText().toString().trim();
            if (!phoneNumber.isEmpty()) {
                // Pass the phone number to the VerificationActivity
                Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
                intent.putExtra("PHONE_NUMBER", phoneNumber);
                startActivity(intent);
            }
        });
    }
}
