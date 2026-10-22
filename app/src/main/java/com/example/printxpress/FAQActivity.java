package com.example.printxpress;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FAQActivity extends AppCompatActivity {

    ImageView ivBack;
    ListView lvFAQ;

    ArrayList<FAQItem> faqList;
    FAQAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        ivBack = findViewById(R.id.ivBack);
        lvFAQ = findViewById(R.id.lvFAQ);

        loadFAQItems();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadFAQItems() {
        faqList = new ArrayList<>();

        faqList.add(new FAQItem(
                "What products can I print?",
                "Business cards, flyers, posters, t-shirts, stickers, mugs, banners, brochures, and more."
        ));

        faqList.add(new FAQItem(
                "How do I place an order?",
                "Browse products, select a product, upload your design, fill in details, choose delivery, and make payment."
        ));

        faqList.add(new FAQItem(
                "What file formats are accepted?",
                "PDF, AI, PSD, JPEG, PNG. Check product guidelines for specific requirements."
        ));

        faqList.add(new FAQItem(
                "What is the minimum order quantity?",
                "Most products have a minimum quantity of 1. Bulk orders may have discounts."
        ));

        faqList.add(new FAQItem(
                "How long does printing take?",
                "Standard orders take 2-3 business days. Rush orders available for an additional fee."
        ));

        faqList.add(new FAQItem(
                "How can I pickup my order?",
                "Once the process is completed, we will mail or contact you over phone."
        ));

        faqList.add(new FAQItem(
                "What payment methods are accepted?",
                "Cash on delivery, bank transfer and upload receipts."
        ));

        faqList.add(new FAQItem(
                "Can I cancel my order?",
                "Yes, you can cancel within 24 hours of placing the order or before printing starts."
        ));

        faqList.add(new FAQItem(
                "What if I'm not satisfied?",
                "Contact our support team within 7 days and we'll resolve the issue."
        ));

        faqList.add(new FAQItem(
                "How do I contact support?",
                "Visit the Support page or email support@printxpress.com"
        ));

        adapter = new FAQAdapter(this, faqList);
        lvFAQ.setAdapter(adapter);
    }
}