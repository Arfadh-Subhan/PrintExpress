package com.example.printxpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    ImageView ivBack;
    ListView lvProducts;

    DatabaseHelper dbHelper;
    ArrayList<Product> productList;
    ProductAdapter adapter;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ivBack = findViewById(R.id.ivBack);
        lvProducts = findViewById(R.id.lvProducts);

        userEmail = getIntent().getStringExtra("USER_EMAIL");

        dbHelper = new DatabaseHelper(this);

        productList = dbHelper.getAllProducts();

        adapter = new ProductAdapter(this, productList);
        lvProducts.setAdapter(adapter);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = productList.get(position);
                Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                intent.putExtra("PRODUCT_ID", product.getId());
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
    }
}