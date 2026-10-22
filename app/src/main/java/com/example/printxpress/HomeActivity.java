package com.example.printxpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class HomeActivity extends AppCompatActivity {

    TextView tvWelcome;
    DrawerLayout drawerLayout;
    LinearLayout drawerMenu;

    LinearLayout llBrowseProducts, llMyOrders, llTrackOrder;
    LinearLayout llViewGallery, llGuidelines, llFAQ;
    LinearLayout llViewOffers, llCustomerSupport;

    LinearLayout llSidebarProfile, llSidebarSupport, llSidebarAbout, llSidebarLogout;
    TextView tvSidebarName, tvSidebarEmail;

    Button btnViewAllSamples, btnLogout;
    ImageView ivMenu, ivSearch;

    DatabaseHelper dbHelper;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvWelcome = findViewById(R.id.tvWelcome);
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerMenu = findViewById(R.id.drawerMenu);
        ivMenu = findViewById(R.id.ivMenu);
        ivSearch = findViewById(R.id.ivSearch);

        llBrowseProducts = findViewById(R.id.llBrowseProducts);
        llMyOrders = findViewById(R.id.llMyOrders);
        llTrackOrder = findViewById(R.id.llTrackOrder);
        llViewGallery = findViewById(R.id.llViewGallery);
        llGuidelines = findViewById(R.id.llGuidelines);
        llFAQ = findViewById(R.id.llFAQ);
        llViewOffers = findViewById(R.id.llViewOffers);
        llCustomerSupport = findViewById(R.id.llCustomerSupport);

        llSidebarProfile = findViewById(R.id.llSidebarProfile);
        llSidebarSupport = findViewById(R.id.llSidebarSupport);
        llSidebarAbout = findViewById(R.id.llSidebarAbout);
        llSidebarLogout = findViewById(R.id.llSidebarLogout);
        tvSidebarName = findViewById(R.id.tvSidebarName);
        tvSidebarEmail = findViewById(R.id.tvSidebarEmail);

        btnViewAllSamples = findViewById(R.id.btnViewAllSamples);
        btnLogout = findViewById(R.id.btnLogout);

        userEmail = getIntent().getStringExtra("USER_EMAIL");

        dbHelper = new DatabaseHelper(this);

        if(userEmail != null) {
            String userName = dbHelper.getUserName(userEmail);
            if(!userName.isEmpty()) {
                tvWelcome.setText("Hello! " + userName);
                tvSidebarName.setText(userName);
                tvSidebarEmail.setText(userEmail);
            }
        }

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NotificationsActivity.class);
                startActivity(intent);
            }
        });

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawerMenu);
            }
        });
        // App Functions
        // 1. Browse Products
        llBrowseProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });

        // 2. My Orders
        llMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyOrdersActivity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });

        // 3. Track Order
        llTrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyOrdersActivity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                intent.putExtra("FROM_TRACK", true);
                startActivity(intent);
            }
        });

        // 4. View Gallery
        llViewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                intent.putExtra("FROM_HOME", true);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });

        // 5. Guidelines
        llGuidelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GuidelinesActivity.class);
                startActivity(intent);
            }
        });

        // 6. FAQ
        llFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FAQActivity.class);
                startActivity(intent);
            }
        });

        // 7. View Offers
        llViewOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OffersActivity.class);
                startActivity(intent);
            }
        });

        // 8. Customer Support
        llCustomerSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SupportActivity.class);
                startActivity(intent);
            }
        });


        // Sidebar - Profile
        llSidebarProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(drawerMenu);
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });

        // Sidebar - Support
        llSidebarSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(drawerMenu);
                Intent intent = new Intent(getApplicationContext(), SupportActivity.class);
                startActivity(intent);
            }
        });

        // Sidebar - About
        llSidebarAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(drawerMenu);
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
            }
        });

        // Sidebar - Logout
        llSidebarLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(drawerMenu);
                SessionManager sessionManager = new SessionManager(HomeActivity.this);
                sessionManager.logout();
                Toast.makeText(HomeActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        // View All Sample Designs
        btnViewAllSamples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });

        // Logout Button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager sessionManager = new SessionManager(HomeActivity.this);
                sessionManager.logout();
                Toast.makeText(HomeActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}