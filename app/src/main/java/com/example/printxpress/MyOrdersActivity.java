package com.example.printxpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {

    ImageView ivBack;
    ListView lvOrders;
    TextView tvNoOrders;

    DatabaseHelper dbHelper;
    ArrayList<Order> orderList;
    OrderAdapter adapter;
    String userEmail;
    int userId;
    boolean fromTrack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        ivBack = findViewById(R.id.ivBack);
        lvOrders = findViewById(R.id.lvOrders);
        tvNoOrders = findViewById(R.id.tvNoOrders);

        userEmail = getIntent().getStringExtra("USER_EMAIL");

        fromTrack = getIntent().getBooleanExtra("FROM_TRACK", false);

        dbHelper = new DatabaseHelper(this);

        if (userEmail == null || userEmail.isEmpty()) {
            Toast.makeText(this, "Please login again", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        userId = dbHelper.getUserId(userEmail);

        if (userId == -1) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadOrders();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lvOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = orderList.get(position);

                if (fromTrack) {
                    Intent intent = new Intent(getApplicationContext(), TrackOrderActivity.class);
                    intent.putExtra("ORDER_ID", order.getId());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), OrderDetailActivity.class);
                    intent.putExtra("ORDER_ID", order.getId());
                    startActivity(intent);
                }
            }
        });
    }

    private void loadOrders() {
        orderList = dbHelper.getOrdersByUserId(userId);

        if (orderList == null || orderList.isEmpty()) {
            lvOrders.setVisibility(View.GONE);
            tvNoOrders.setVisibility(View.VISIBLE);
        } else {
            lvOrders.setVisibility(View.VISIBLE);
            tvNoOrders.setVisibility(View.GONE);
            adapter = new OrderAdapter(this, orderList);
            lvOrders.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOrders();
    }
}