package com.example.printxpress;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivBack;
    TextView tvEdit, tvEmail, tvPasswordArrow;
    EditText etName, etPhone, etAddress, etOldPassword, etNewPassword, etConfirmPassword;
    Button btnSave;
    LinearLayout llChangePassword, llPasswordFields, llPasswordHeader;

    DatabaseHelper dbHelper;
    String userEmail;
    boolean isEditing = false;
    boolean isPasswordExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivBack = findViewById(R.id.ivBack);
        tvEdit = findViewById(R.id.tvEdit);
        tvEmail = findViewById(R.id.tvEmail);
        tvPasswordArrow = findViewById(R.id.tvPasswordArrow);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSave = findViewById(R.id.btnSave);
        llChangePassword = findViewById(R.id.llChangePassword);
        llPasswordFields = findViewById(R.id.llPasswordFields);
        llPasswordHeader = findViewById(R.id.llPasswordHeader);

        userEmail = getIntent().getStringExtra("USER_EMAIL");

        dbHelper = new DatabaseHelper(this);

        loadUserData();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEditMode();
            }
        });

        llPasswordHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditing) {
                    togglePasswordFields();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });
    }

    private void loadUserData() {
        if (userEmail != null) {
            String userName = dbHelper.getUserName(userEmail);
            String userPhone = dbHelper.getUserPhone(userEmail);
            String userAddress = dbHelper.getUserAddress(userEmail);

            etName.setText(userName);
            tvEmail.setText(userEmail);

            if (userPhone != null && !userPhone.isEmpty()) {
                etPhone.setText(userPhone);
            } else {
                etPhone.setText("");
                etPhone.setHint("Enter phone number");
            }

            if (userAddress != null && !userAddress.isEmpty()) {
                etAddress.setText(userAddress);
            } else {
                etAddress.setText("");
                etAddress.setHint("Enter your delivery address");
            }
        }
    }

    private void toggleEditMode() {
        if (isEditing) {
            // Cancel edit mode
            isEditing = false;
            tvEdit.setText("Edit");
            etName.setEnabled(false);
            etPhone.setEnabled(false);
            etAddress.setEnabled(false);
            btnSave.setVisibility(View.GONE);

            llChangePassword.setVisibility(View.GONE);

            collapsePasswordFields();
            loadUserData();
        } else {
            isEditing = true;
            tvEdit.setText("Cancel");
            etName.setEnabled(true);
            etPhone.setEnabled(true);
            etAddress.setEnabled(true);
            btnSave.setVisibility(View.VISIBLE);

            llChangePassword.setVisibility(View.VISIBLE);

            if (isPasswordExpanded) {
                etOldPassword.setEnabled(true);
                etNewPassword.setEnabled(true);
                etConfirmPassword.setEnabled(true);
            }
        }
    }

    private void togglePasswordFields() {
        if (isPasswordExpanded) {
            collapsePasswordFields();
        } else {
            expandPasswordFields();
        }
    }

    private void expandPasswordFields() {
        isPasswordExpanded = true;
        llPasswordFields.setVisibility(View.VISIBLE);
        tvPasswordArrow.setText("▼");
        if (isEditing) {
            etOldPassword.setEnabled(true);
            etNewPassword.setEnabled(true);
            etConfirmPassword.setEnabled(true);
        }
    }

    private void collapsePasswordFields() {
        isPasswordExpanded = false;
        llPasswordFields.setVisibility(View.GONE);
        tvPasswordArrow.setText("▶");
        etOldPassword.setText("");
        etNewPassword.setText("");
        etConfirmPassword.setText("");
        etOldPassword.setEnabled(false);
        etNewPassword.setEnabled(false);
        etConfirmPassword.setEnabled(false);
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

    private void saveUserData() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!phone.isEmpty() && !isValidSriLankanPhone(phone)) {
            Toast.makeText(this, "Please enter a valid Sri Lankan number (+94 or 0) with 10 digits",
                    Toast.LENGTH_LONG).show();
            etPhone.requestFocus();
            return;
        }

        String oldPassword = etOldPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Phone", phone);
        values.put("Address", address);

        if (!oldPassword.isEmpty() || !newPassword.isEmpty() || !confirmPassword.isEmpty()) {
            String currentPassword = dbHelper.getUserPassword(userEmail);
            if (!oldPassword.equals(currentPassword)) {
                Toast.makeText(this, "Incorrect old password", Toast.LENGTH_SHORT).show();
                etOldPassword.requestFocus();
                return;
            }
            if (newPassword.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                etNewPassword.requestFocus();
                return;
            }
            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                etConfirmPassword.requestFocus();
                return;
            }
            values.put("Password", newPassword);
        }

        int result = database.update("UsersTable", values,
                "Email = ?", new String[]{userEmail});

        if (result > 0) {
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            toggleEditMode();
            loadUserData();
            collapsePasswordFields();
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }
}