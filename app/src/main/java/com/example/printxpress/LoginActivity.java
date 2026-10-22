package com.example.printxpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button btnBack, btnLogin;
    EditText etLoginEmail, etLoginPassword;
    TextView tvRegisterLink;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnBack = findViewById(R.id.btnBack);
        btnLogin = findViewById(R.id.btnLogin);
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        tvRegisterLink = findViewById(R.id.tvRegisterLink);

        dbHelper = new DatabaseHelper(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailOrPhone = etLoginEmail.getText().toString().trim();
                String password = etLoginPassword.getText().toString().trim();

                if (emailOrPhone.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your email or phone", Toast.LENGTH_SHORT).show();
                    etLoginEmail.requestFocus();
                } else if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    etLoginPassword.requestFocus();
                } else {
                    boolean isValid = false;

                    if (emailOrPhone.contains("@")) {
                        isValid = dbHelper.checkUser(emailOrPhone, password);
                    } else {
                        String email = dbHelper.getEmailByPhone(emailOrPhone);
                        if (email != null && !email.isEmpty()) {
                            isValid = dbHelper.checkUser(email, password);
                        } else {
                            isValid = dbHelper.checkUserByPhone(emailOrPhone, password);
                        }
                    }

                    if (isValid) {
                        String userEmail = dbHelper.getEmailByPhone(emailOrPhone);
                        if (userEmail == null || userEmail.isEmpty()) {
                            userEmail = emailOrPhone;
                        }

                        SessionManager sessionManager = new SessionManager(LoginActivity.this);
                        sessionManager.setLogin(true, userEmail);

                        String userName = dbHelper.getUserNameByEmailOrPhone(emailOrPhone);
                        Toast.makeText(LoginActivity.this, "Welcome " + userName + "!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("USER_EMAIL", userEmail);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Email/Phone or Password", Toast.LENGTH_SHORT).show();
                        etLoginPassword.setText("");
                        etLoginPassword.requestFocus();
                    }
                }
            }
        });

        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}