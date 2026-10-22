package com.example.printxpress;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PlaceOrderActivity extends AppCompatActivity {

    ImageView ivBack;
    TextView tvProductName, tvPrice, tvTotalPrice, tvAddressLabel;
    EditText etQuantity, etAddress, etNotes;
    RadioGroup rgDelivery, rgPayment;
    RadioButton rbPickup, rbDelivery, rbCash, rbReceipt;
    Button btnUploadDesign, btnUploadReceipt, btnPlaceOrder, btnCancel;
    ImageView ivDesignPreview, ivReceiptPreview;

    DatabaseHelper dbHelper;
    int productId;
    String productName, price;
    int userId;
    String userEmail;

    Uri designImageUri;
    Uri receiptImageUri;
    String savedImagePath = "";

    private final ActivityResultLauncher<Intent> designGalleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    designImageUri = result.getData().getData();
                    ivDesignPreview.setImageURI(designImageUri);
                    ivDesignPreview.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Design uploaded!", Toast.LENGTH_SHORT).show();
                }
            });

    private final ActivityResultLauncher<Intent> receiptGalleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    receiptImageUri = result.getData().getData();
                    ivReceiptPreview.setImageURI(receiptImageUri);
                    ivReceiptPreview.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Receipt uploaded!", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        ivBack = findViewById(R.id.ivBack);
        tvProductName = findViewById(R.id.tvProductName);
        tvPrice = findViewById(R.id.tvPrice);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvAddressLabel = findViewById(R.id.tvAddressLabel);
        etQuantity = findViewById(R.id.etQuantity);
        etAddress = findViewById(R.id.etAddress);
        etNotes = findViewById(R.id.etNotes);
        rgDelivery = findViewById(R.id.rgDelivery);
        rgPayment = findViewById(R.id.rgPayment);
        rbPickup = findViewById(R.id.rbPickup);
        rbDelivery = findViewById(R.id.rbDelivery);
        rbCash = findViewById(R.id.rbCash);
        rbReceipt = findViewById(R.id.rbReceipt);
        btnUploadDesign = findViewById(R.id.btnUploadDesign);
        btnUploadReceipt = findViewById(R.id.btnUploadReceipt);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnCancel = findViewById(R.id.btnCancel);
        ivDesignPreview = findViewById(R.id.ivDesignPreview);
        ivReceiptPreview = findViewById(R.id.ivReceiptPreview);

        productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        productName = getIntent().getStringExtra("PRODUCT_NAME");
        price = getIntent().getStringExtra("PRODUCT_PRICE");
        userEmail = getIntent().getStringExtra("USER_EMAIL");

        dbHelper = new DatabaseHelper(this);

        if (userEmail != null && !userEmail.isEmpty()) {
            userId = dbHelper.getUserId(userEmail);
        } else {
            Toast.makeText(this, "Please login again", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvProductName.setText(productName);
        tvPrice.setText(price);

        etQuantity.setText("1");
        calculateTotal();

        String userAddress = dbHelper.getUserAddress(userEmail);
        if (userAddress != null && !userAddress.isEmpty()) {
            etAddress.setText(userAddress);
        }

        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateTotal();
            }
        });

        rgDelivery.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbDelivery) {
                    tvAddressLabel.setVisibility(View.VISIBLE);
                    etAddress.setVisibility(View.VISIBLE);
                } else {
                    tvAddressLabel.setVisibility(View.GONE);
                    etAddress.setVisibility(View.GONE);
                }
            }
        });

        rgPayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbReceipt) {
                    btnUploadReceipt.setVisibility(View.VISIBLE);
                } else {
                    btnUploadReceipt.setVisibility(View.GONE);
                    ivReceiptPreview.setVisibility(View.GONE);
                    receiptImageUri = null;
                }
            }
        });

        btnUploadDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                designGalleryLauncher.launch(intent);
            }
        });

        btnUploadReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                receiptGalleryLauncher.launch(intent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void calculateTotal() {
        String quantityStr = etQuantity.getText().toString().trim();

        if (quantityStr.isEmpty()) {
            tvTotalPrice.setText(price);
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            String priceStr = price.replace("LKR ", "").trim();
            int unitPrice = Integer.parseInt(priceStr);
            int total = quantity * unitPrice;
            tvTotalPrice.setText("LKR " + total);
        } catch (NumberFormatException e) {
            tvTotalPrice.setText(price);
        }
    }

    private String saveImageToAppStorage(Uri imageUri) {
        try {
            File folder = new File(getExternalFilesDir(null), "designs");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String fileName = "design_" + System.currentTimeMillis() + ".jpg";
            File destinationFile = new File(folder, fileName);

            ContentResolver resolver = getContentResolver();
            InputStream inputStream = resolver.openInputStream(imageUri);
            FileOutputStream outputStream = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            return destinationFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void placeOrder() {
        String quantityStr = etQuantity.getText().toString().trim();

        if (quantityStr.isEmpty()) {
            Toast.makeText(this, "Please enter quantity", Toast.LENGTH_SHORT).show();
            etQuantity.requestFocus();
            return;
        }

        int quantity = Integer.parseInt(quantityStr);
        if (quantity < 1) {
            Toast.makeText(this, "Quantity must be at least 1", Toast.LENGTH_SHORT).show();
            etQuantity.requestFocus();
            return;
        }

        if (designImageUri == null) {
            Toast.makeText(this, "Please upload a design to print", Toast.LENGTH_SHORT).show();
            return;
        }

        String deliveryOption = rbPickup.isChecked() ? "Pickup" : "Delivery";
        String address = "";

        if (deliveryOption.equals("Delivery")) {
            address = etAddress.getText().toString().trim();
            if (address.isEmpty()) {
                Toast.makeText(this, "Please enter delivery address", Toast.LENGTH_SHORT).show();
                etAddress.requestFocus();
                return;
            }
        }

        String paymentMethod = rbCash.isChecked() ? "Cash on Delivery" : "Upload Receipt";
        if (paymentMethod.equals("Upload Receipt") && receiptImageUri == null) {
            Toast.makeText(this, "Please upload payment receipt", Toast.LENGTH_SHORT).show();
            return;
        }

        String notes = etNotes.getText().toString().trim();
        String totalPrice = tvTotalPrice.getText().toString();

        String designPath = saveImageToAppStorage(designImageUri);
        if (designPath == null) {
            Toast.makeText(this, "Failed to save design. Please try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = dbHelper.insertOrder(userId, productId, productName, quantity,
                totalPrice, deliveryOption, address, designPath, paymentMethod, notes);

        if (isInserted) {
            Toast.makeText(this, "Order Placed Successfully!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Order Failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}