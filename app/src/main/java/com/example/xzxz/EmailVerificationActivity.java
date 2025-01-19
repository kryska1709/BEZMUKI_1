package com.example.xzxz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerificationActivity extends AppCompatActivity {

    private Button resendButton;
    private Button verifyButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        mAuth = FirebaseAuth.getInstance();
        verifyButton = findViewById(R.id.verifyButton);

        resendButton.setOnClickListener(v -> {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                user.sendEmailVerification()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Подтверждающее письмо отправлено повторно.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Ошибка отправки подтверждающего письма.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        verifyButton.setOnClickListener(v -> {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                user.reload().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.reload();
                        if (user.isEmailVerified()) {
                            // Переход на MainActivity
                            startActivity(new Intent(EmailVerificationActivity.this, MainActivity.class));
                            finish(); // Закрыть текущую активность
                        } else {
                            Toast.makeText(getApplicationContext(), "Почта еще не подтверждена.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Ошибка проверки подтверждения почты.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            // Переход на MainActivity
            startActivity(new Intent(EmailVerificationActivity.this, MainActivity.class));
            finish(); // Закрыть текущую активность
        }
    }
}
