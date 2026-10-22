package com.example.printxpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    Button btnBack, btnRegister;
    EditText etRegName, etRegEmail, etRegPassword, etRegConfirmPassword, etRegPhone;
    TextView tvLoginLink;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnBack = findViewById(R.id.btnBack);
        btnRegister = findViewById(R.id.btnRegister);
        etRegName = findViewById(R.id.etRegName);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        etRegConfirmPassword = findViewById(R.id.etRegConfirmPassword);
        etRegPhone = findViewById(R.id.etRegPhone);
        tvLoginLink = findViewById(R.id.tvLoginLink);

        dbHelper = new DatabaseHelper(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etRegName.getText().toString().trim();
                String email = etRegEmail.getText().toString().trim();
                String phone = etRegPhone.getText().toString().trim();
                String password = etRegPassword.getText().toString().trim();
                String confirmPassword = etRegConfirmPassword.getText().toString().trim();

                if(name.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    etRegName.requestFocus();
                }
                else if(email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    etRegEmail.requestFocus();
                }
                else if(!isValidEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    etRegEmail.requestFocus();
                }
                else if(phone.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    etRegPhone.requestFocus();
                }
                else if(!isValidSriLankanPhone(phone)) {
                    Toast.makeText(RegisterActivity.this, "Please enter a valid Sri Lankan number (+94 or 0) with 10 digits", Toast.LENGTH_LONG).show();
                    etRegPhone.requestFocus();
                }
                else if(password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                    etRegPassword.requestFocus();
                }
                else if(confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please confirm your password", Toast.LENGTH_SHORT).show();
                    etRegConfirmPassword.requestFocus();
                }
                else if(!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    etRegPassword.setText("");
                    etRegConfirmPassword.setText("");
                    etRegPassword.requestFocus();
                }
                else if(password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    etRegPassword.requestFocus();
                }
                else {
                    if(dbHelper.checkEmailExists(email)) {
                        Toast.makeText(RegisterActivity.this, "Email already registered. Please Login.", Toast.LENGTH_SHORT).show();
                        etRegEmail.setText("");
                        etRegEmail.requestFocus();
                    } else {
                        boolean isInserted = dbHelper.insertUser(name, email, password, phone, "");
                        if(isInserted) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });

        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailPattern, email);
    }

    private boolean isValidSriLankanPhone(String phone) {
        String cleaned = phone.replaceAll("[^0-9+]", "");
        if (cleaned.matches("^0[0-9]{9}$")) {
            return true;
        } else if (cleaned.matches("^\\+94[0-9]{9}$")) {
            return true;
        } else if (cleaned.matches("^94[0-9]{9}$")) {
            return true;
        }
        return false;
    }
}