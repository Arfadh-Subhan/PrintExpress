package com.example.printxpress;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class OffersActivity extends AppCompatActivity {

    ImageView ivBack;
    ListView lvOffers;

    ArrayList<OfferItem> offerList;
    OfferAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        ivBack = findViewById(R.id.ivBack);
        lvOffers = findViewById(R.id.lvOffers);

        loadOfferItems();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadOfferItems() {
        offerList = new ArrayList<>();

        offerList.add(new OfferItem(
                "Pickup Cashback",
                "Get 10% cashback when you choose pickup",
                "10% Cashback",
                "Ongoing"
        ));

        offerList.add(new OfferItem(
                "Bulk Order Discount",
                "Order 50+ items and get 15% off",
                "15% OFF",
                "Ongoing"
        ));

        offerList.add(new OfferItem(
                "Super Bulk Discount",
                "Order 100+ items and get 25% off",
                "25% OFF",
                "Ongoing"
        ));

        offerList.add(new OfferItem(
                "Seasonal Sale",
                "Special discounts on all products",
                "Up to 30% OFF",
                "August 2026"
        ));

        offerList.add(new OfferItem(
                "Weekend Special",
                "Extra 10% off on orders placed on weekends",
                "10% OFF",
                "Every Weekend"
        ));

        offerList.add(new OfferItem(
                "Happy Hour Deal",
                "5% off on orders placed between 10am-12pm",
                "5% OFF",
                "Daily"
        ));

        adapter = new OfferAdapter(this, offerList);
        lvOffers.setAdapter(adapter);
    }
}