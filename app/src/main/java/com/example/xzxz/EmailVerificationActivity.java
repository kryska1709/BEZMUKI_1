package com.example.xzxz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerificationActivity extends AppCompatActivity {

    private Button verifyButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        mAuth = FirebaseAuth.getInstance();
        verifyButton = findViewById(R.id.verifyButton);

        verifyButton.setOnClickListener(v -> verifyEmail());
    }

    private void verifyEmail() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.reload().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (user.isEmailVerified()) {
                        Toast.makeText(getApplicationContext(), "Email подтвержден. Переход на MainActivity.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EmailVerificationActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Email не подтвержден. Пожалуйста, проверьте свою почту.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Ошибка проверки email.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
