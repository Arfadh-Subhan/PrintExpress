package com.example.printxpress;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class OrderDetailActivity extends AppCompatActivity {

    ImageView ivBack, ivDesignImage;
    TextView tvOrderId, tvProductName, tvQuantity, tvTotalPrice;
    TextView tvDelivery, tvAddress, tvAddressLabel;
    TextView tvPaymentMethod, tvOrderStatus, tvOrderDate, tvNotes;
    Button btnCancelOrder;

    DatabaseHelper dbHelper;
    int orderId;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        ivBack = findViewById(R.id.ivBack);
        ivDesignImage = findViewById(R.id.ivDesignImage);
        tvOrderId = findViewById(R.id.tvOrderId);
        tvProductName = findViewById(R.id.tvProductName);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvDelivery = findViewById(R.id.tvDelivery);
        tvAddress = findViewById(R.id.tvAddress);
        tvAddressLabel = findViewById(R.id.tvAddressLabel);
        tvPaymentMethod = findViewById(R.id.tvPaymentMethod);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvNotes = findViewById(R.id.tvNotes);
        btnCancelOrder = findViewById(R.id.btnCancelOrder);

        orderId = getIntent().getIntExtra("ORDER_ID", -1);

        dbHelper = new DatabaseHelper(this);

        if (orderId != -1) {
            order = dbHelper.getOrderById(orderId);
            if (order != null) {
                displayOrderDetails(order);
            } else {
                Toast.makeText(this, "Order not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Invalid order", Toast.LENGTH_SHORT).show();
            finish();
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder();
            }
        });
    }

    private void displayOrderDetails(Order order) {
        tvOrderId.setText("#" + order.getId());
        tvProductName.setText(order.getProductName());
        tvQuantity.setText(String.valueOf(order.getQuantity()));
        tvTotalPrice.setText(order.getTotalPrice());
        tvDelivery.setText(order.getDeliveryOption());
        tvPaymentMethod.setText(order.getPaymentMethod());
        tvOrderStatus.setText(order.getStatus());
        tvOrderDate.setText(order.getOrderDate());

        String notes = order.getNotes();
        if (notes != null && !notes.isEmpty()) {
            tvNotes.setText("Notes: " + notes);
            tvNotes.setVisibility(View.VISIBLE);
        } else {
            tvNotes.setVisibility(View.GONE);
        }

        if (order.getDeliveryOption().equals("Delivery")) {
            tvAddressLabel.setVisibility(View.VISIBLE);
            tvAddress.setVisibility(View.VISIBLE);
            tvAddress.setText(order.getAddress());
        } else {
            tvAddressLabel.setVisibility(View.GONE);
            tvAddress.setVisibility(View.GONE);
        }

        String status = order.getStatus();
        if (status.equals("Pending")) {
            tvOrderStatus.setTextColor(0xFFFF9800);
        } else if (status.equals("Processing")) {
            tvOrderStatus.setTextColor(0xFF2196F3);
        } else if (status.equals("Printing")) {
            tvOrderStatus.setTextColor(0xFF9C27B0);
        } else if (status.equals("Ready")) {
            tvOrderStatus.setTextColor(0xFF4CAF50);
        } else if (status.equals("Completed")) {
            tvOrderStatus.setTextColor(0xFF4CAF50);
        } else if (status.equals("Cancelled")) {
            tvOrderStatus.setTextColor(0xFFF44336);
        }

        if (status.equals("Pending")) {
            btnCancelOrder.setVisibility(View.VISIBLE);
        } else {
            btnCancelOrder.setVisibility(View.GONE);
        }

        String designPath = order.getFilePath();
        if (designPath != null && !designPath.isEmpty()) {
            try {
                File imageFile = new File(designPath);
                if (imageFile.exists()) {
                    ivDesignImage.setImageURI(Uri.fromFile(imageFile));
                    ivDesignImage.setVisibility(View.VISIBLE);
                } else {
                    ivDesignImage.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                ivDesignImage.setVisibility(View.GONE);
            }
        } else {
            ivDesignImage.setVisibility(View.GONE);
        }
    }

    private void cancelOrder() {
        boolean updated = dbHelper.updateOrderStatus(orderId, "Cancelled");
        if (updated) {
            order.setStatus("Cancelled");
            displayOrderDetails(order);
            Toast.makeText(this, "Order cancelled successfully!", Toast.LENGTH_SHORT).show();
            btnCancelOrder.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Failed to cancel order", Toast.LENGTH_SHORT).show();
        }
    }
}