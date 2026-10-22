package com.example.printxpress;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TrackOrderActivity extends AppCompatActivity {

    ImageView ivBack;
    TextView tvOrderId, tvProductName, tvQuantity, tvTotalPrice, tvDelivery;
    TextView tvStatusMessage, tvStep1, tvStep2, tvStep3, tvStep4, tvStep5;
    TextView tvStep1Status, tvStep2Status, tvStep3Status, tvStep4Status, tvStep5Status;
    ImageView ivStep1, ivStep2, ivStep3, ivStep4, ivStep5;
    View viewLine1, viewLine2, viewLine3, viewLine4;
    Button btnCancelOrder;

    DatabaseHelper dbHelper;
    int orderId;
    Order order;
    Handler handler;
    Runnable statusUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        ivBack = findViewById(R.id.ivBack);
        tvOrderId = findViewById(R.id.tvOrderId);
        tvProductName = findViewById(R.id.tvProductName);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvDelivery = findViewById(R.id.tvDelivery);
        tvStatusMessage = findViewById(R.id.tvStatusMessage);
        btnCancelOrder = findViewById(R.id.btnCancelOrder);

        ivStep1 = findViewById(R.id.ivStep1);
        ivStep2 = findViewById(R.id.ivStep2);
        ivStep3 = findViewById(R.id.ivStep3);
        ivStep4 = findViewById(R.id.ivStep4);
        ivStep5 = findViewById(R.id.ivStep5);

        tvStep1 = findViewById(R.id.tvStep1);
        tvStep2 = findViewById(R.id.tvStep2);
        tvStep3 = findViewById(R.id.tvStep3);
        tvStep4 = findViewById(R.id.tvStep4);
        tvStep5 = findViewById(R.id.tvStep5);

        tvStep1Status = findViewById(R.id.tvStep1Status);
        tvStep2Status = findViewById(R.id.tvStep2Status);
        tvStep3Status = findViewById(R.id.tvStep3Status);
        tvStep4Status = findViewById(R.id.tvStep4Status);
        tvStep5Status = findViewById(R.id.tvStep5Status);

        viewLine1 = findViewById(R.id.viewLine1);
        viewLine2 = findViewById(R.id.viewLine2);
        viewLine3 = findViewById(R.id.viewLine3);
        viewLine4 = findViewById(R.id.viewLine4);

        orderId = getIntent().getIntExtra("ORDER_ID", -1);

        dbHelper = new DatabaseHelper(this);

        if (orderId == -1) {
            Toast.makeText(this, "Invalid order", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadOrder();

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

        startStatusTimer();
    }

    private void loadOrder() {
        order = dbHelper.getOrderById(orderId);

        if (order == null) {
            Toast.makeText(this, "Order not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvOrderId.setText("#" + order.getId());
        tvProductName.setText(order.getProductName());
        tvQuantity.setText(String.valueOf(order.getQuantity()));
        tvTotalPrice.setText(order.getTotalPrice());
        tvDelivery.setText(order.getDeliveryOption());

        updateStatusDisplay();
    }

    private void updateStatusDisplay() {
        String status = order.getStatus();

        resetSteps();

        String message = getStatusMessage(status);
        tvStatusMessage.setText(message);

        switch (status) {
            case "Pending":
                setStepActive(1);
                btnCancelOrder.setVisibility(View.VISIBLE);
                break;
            case "Processing":
                setStepCompleted(1);
                setStepActive(2);
                btnCancelOrder.setVisibility(View.GONE);
                break;
            case "Printing":
                setStepCompleted(1);
                setStepCompleted(2);
                setStepActive(3);
                btnCancelOrder.setVisibility(View.GONE);
                break;
            case "Ready":
                setStepCompleted(1);
                setStepCompleted(2);
                setStepCompleted(3);
                setStepActive(4);
                btnCancelOrder.setVisibility(View.GONE);
                break;
            case "Completed":
                setStepCompleted(1);
                setStepCompleted(2);
                setStepCompleted(3);
                setStepCompleted(4);
                setStepActive(5);
                btnCancelOrder.setVisibility(View.GONE);
                break;
            case "Cancelled":
                tvStatusMessage.setText("This order has been cancelled.");
                btnCancelOrder.setVisibility(View.GONE);
                break;
        }
    }

    private String getStatusMessage(String status) {
        switch (status) {
            case "Pending":
                return "Your order has been placed! We will confirm soon.";
            case "Processing":
                return "Your order is being prepared for printing.";
            case "Printing":
                return "Your order is currently being printed.";
            case "Ready":
                return "Your order is ready for pickup/delivery!";
            case "Completed":
                return "Your order has been completed! Thank you for choosing PrintXpress.";
            case "Cancelled":
                return "This order has been cancelled.";
            default:
                return "Status: " + status;
        }
    }

    private void resetSteps() {
        int grayColor = 0xFF888888;
        int grayBg = 0xFFE0E0E0;

        setStepColor(ivStep1, grayColor);
        setStepColor(ivStep2, grayColor);
        setStepColor(ivStep3, grayColor);
        setStepColor(ivStep4, grayColor);
        setStepColor(ivStep5, grayColor);

        tvStep1.setTextColor(grayColor);
        tvStep2.setTextColor(grayColor);
        tvStep3.setTextColor(grayColor);
        tvStep4.setTextColor(grayColor);
        tvStep5.setTextColor(grayColor);

        viewLine1.setBackgroundColor(grayBg);
        viewLine2.setBackgroundColor(grayBg);
        viewLine3.setBackgroundColor(grayBg);
        viewLine4.setBackgroundColor(grayBg);

        tvStep1Status.setVisibility(View.GONE);
        tvStep2Status.setVisibility(View.GONE);
        tvStep3Status.setVisibility(View.GONE);
        tvStep4Status.setVisibility(View.GONE);
        tvStep5Status.setVisibility(View.GONE);
    }

    private void setStepActive(int step) {
        int color = 0xFFFF9800;
        int greenColor = 0xFF4CAF50;

        switch (step) {
            case 1:
                setStepColor(ivStep1, color);
                tvStep1.setTextColor(color);
                tvStep1Status.setText("Active");
                tvStep1Status.setVisibility(View.VISIBLE);
                break;
            case 2:
                setStepColor(ivStep2, color);
                tvStep2.setTextColor(color);
                tvStep2Status.setText("Active");
                tvStep2Status.setVisibility(View.VISIBLE);
                break;
            case 3:
                setStepColor(ivStep3, color);
                tvStep3.setTextColor(color);
                tvStep3Status.setText("Active");
                tvStep3Status.setVisibility(View.VISIBLE);
                break;
            case 4:
                setStepColor(ivStep4, color);
                tvStep4.setTextColor(color);
                tvStep4Status.setText("Active");
                tvStep4Status.setVisibility(View.VISIBLE);
                break;
            case 5:
                setStepColor(ivStep5, greenColor);
                tvStep5.setTextColor(greenColor);
                tvStep5Status.setText("Completed");
                tvStep5Status.setVisibility(View.VISIBLE);
                tvStep5Status.setTextColor(greenColor);
                break;
        }
    }

    private void setStepCompleted(int step) {
        int color = 0xFF4CAF50;

        switch (step) {
            case 1:
                setStepColor(ivStep1, color);
                tvStep1.setTextColor(color);
                tvStep1Status.setText("Done");
                tvStep1Status.setVisibility(View.VISIBLE);
                tvStep1Status.setTextColor(color);
                viewLine1.setBackgroundColor(color);
                break;
            case 2:
                setStepColor(ivStep2, color);
                tvStep2.setTextColor(color);
                tvStep2Status.setText("Done");
                tvStep2Status.setVisibility(View.VISIBLE);
                tvStep2Status.setTextColor(color);
                viewLine2.setBackgroundColor(color);
                break;
            case 3:
                setStepColor(ivStep3, color);
                tvStep3.setTextColor(color);
                tvStep3Status.setText("Done");
                tvStep3Status.setVisibility(View.VISIBLE);
                tvStep3Status.setTextColor(color);
                viewLine3.setBackgroundColor(color);
                break;
            case 4:
                setStepColor(ivStep4, color);
                tvStep4.setTextColor(color);
                tvStep4Status.setText("Done");
                tvStep4Status.setVisibility(View.VISIBLE);
                tvStep4Status.setTextColor(color);
                viewLine4.setBackgroundColor(color);
                break;
        }
    }

    private void setStepColor(ImageView imageView, int color) {
        imageView.setColorFilter(color);
    }

    private void startStatusTimer() {
        handler = new Handler();
        statusUpdater = new Runnable() {
            @Override
            public void run() {
                updateOrderStatus();
                handler.postDelayed(this, 300000); // 5 minutes
            }
        };
        handler.post(statusUpdater);
    }

    private void updateOrderStatus() {
        String currentStatus = order.getStatus();

        if (currentStatus.equals("Cancelled") || currentStatus.equals("Completed")) {
            return;
        }

        String nextStatus = getNextStatus(currentStatus);
        if (nextStatus != null) {
            boolean updated = dbHelper.updateOrderStatus(orderId, nextStatus);
            if (updated) {
                order.setStatus(nextStatus);
                updateStatusDisplay();
                Toast.makeText(this, "Status updated to: " + nextStatus, Toast.LENGTH_SHORT).show();

                if (nextStatus.equals("Completed")) {
                    handler.removeCallbacks(statusUpdater);
                }
            }
        }
    }

    private String getNextStatus(String currentStatus) {
        switch (currentStatus) {
            case "Pending":
                return "Processing";
            case "Processing":
                return "Printing";
            case "Printing":
                return "Ready";
            case "Ready":
                return "Completed";
            default:
                return null;
        }
    }

    private void cancelOrder() {
        boolean updated = dbHelper.updateOrderStatus(orderId, "Cancelled");
        if (updated) {
            order.setStatus("Cancelled");
            updateStatusDisplay();
            Toast.makeText(this, "Order cancelled successfully!", Toast.LENGTH_SHORT).show();
            handler.removeCallbacks(statusUpdater);
            btnCancelOrder.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Failed to cancel order", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && statusUpdater != null) {
            handler.removeCallbacks(statusUpdater);
        }
    }
}