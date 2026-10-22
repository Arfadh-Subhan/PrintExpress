package com.example.printxpress;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

    ImageView ivBack;
    ListView lvNotifications;

    ArrayList<NotificationItem> notificationList;
    NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ivBack = findViewById(R.id.ivBack);
        lvNotifications = findViewById(R.id.lvNotifications);

        loadNotifications();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadNotifications() {
        notificationList = new ArrayList<>();

        notificationList.add(new NotificationItem(
                "Order Placed!",
                "Your order #12345 has been placed successfully.",
                "Today, 10:30 AM",
                false
        ));

        notificationList.add(new NotificationItem(
                "Order Processing",
                "Your order #12345 is being processed.",
                "Today, 11:00 AM",
                false
        ));

        notificationList.add(new NotificationItem(
                "Order Ready!",
                "Your order #12344 is ready for pickup.",
                "Yesterday",
                true
        ));

        notificationList.add(new NotificationItem(
                "Seasonal Sale!",
                "Special discounts on all products. Up to 30% off!",
                "2 days ago",
                true
        ));

        notificationList.add(new NotificationItem(
                "Order Completed",
                "Your order #12343 has been completed. Thank you!",
                "3 days ago",
                true
        ));

        notificationList.add(new NotificationItem(
                "Bulk Discount Available",
                "Order 50+ items and get 15% off on your next order.",
                "5 days ago",
                true
        ));

        notificationList.add(new NotificationItem(
                "Welcome Offer!",
                "Get 20% cash back on your first order. Use code: NEWBIE26 ",
                "7 days ago",
                true
        ));

        adapter = new NotificationAdapter(this, notificationList);
        lvNotifications.setAdapter(adapter);
    }
}