package com.example.xzxz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class reggg extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button registerButton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reggg);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("pays"); // Ссылка на таблицу pays
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.regButton);

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            registerUser(email, password); // Передаем email и password
        });
    }

    private void registerUser(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            // Отправка подтверждающего письма
                            user.sendEmailVerification()
                                    .addOnCompleteListener(sendTask -> {
                                        if (sendTask.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Пользователь зарегистрирован. Почта подтверждена", Toast.LENGTH_SHORT).show();
                                            // Переход на EmailVerificationActivity
                                            startActivity(new Intent(reggg.this, MainActivity.class));
                                            finish(); // Закрыть текущую активность
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Ошибка отправки подтверждающего письма.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        Exception exception = task.getException();
                        if (exception != null && exception.getMessage().contains("The email address is badly formatted")) {
                            Toast.makeText(getApplicationContext(), "Почта не существует", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Ошибка регистрации. Проверьте правильно ли вы ввели почту и пароль.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
