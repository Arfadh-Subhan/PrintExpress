package com.example.printxpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView ivBack, ivProductImage;
    TextView tvProductName, tvProductCategory, tvProductPrice, tvProductDescription;
    Button btnOrderNow;

    DatabaseHelper dbHelper;
    int productId;
    boolean fromGallery;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ivBack = findViewById(R.id.ivBack);
        ivProductImage = findViewById(R.id.ivProductImage);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductCategory = findViewById(R.id.tvProductCategory);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductDescription = findViewById(R.id.tvProductDescription);
        btnOrderNow = findViewById(R.id.btnOrderNow);

        productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        fromGallery = getIntent().getBooleanExtra("FROM_GALLERY", false);
        userEmail = getIntent().getStringExtra("USER_EMAIL");

        dbHelper = new DatabaseHelper(this);

        if (fromGallery) {
            btnOrderNow.setVisibility(View.GONE);
        }

        if (productId != -1) {
            Product product = dbHelper.getProductById(productId);
            if (product != null) {
                displayProductDetails(product);
            } else {
                Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Invalid product", Toast.LENGTH_SHORT).show();
            finish();
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnOrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlaceOrderActivity.class);
                intent.putExtra("PRODUCT_ID", productId);
                intent.putExtra("PRODUCT_NAME", tvProductName.getText().toString());
                intent.putExtra("PRODUCT_PRICE", tvProductPrice.getText().toString());
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
    }

    private void displayProductDetails(Product product) {
        tvProductName.setText(product.getName());
        tvProductCategory.setText(product.getCategory());
        tvProductPrice.setText(product.getPrice());
        tvProductDescription.setText(product.getDescription());

        String imageName = product.getImagePath();
        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if (imageResId != 0) {
            ivProductImage.setImageResource(imageResId);
        } else {
            ivProductImage.setImageResource(R.drawable.ic_products);
        }
    }
}