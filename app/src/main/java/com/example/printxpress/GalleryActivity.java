package com.example.printxpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    ImageView ivBack;
    GridView gvGallery;

    ArrayList<GalleryItem> galleryList;
    GalleryAdapter adapter;
    boolean fromHome = false;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ivBack = findViewById(R.id.ivBack);
        gvGallery = findViewById(R.id.gvGallery);

        fromHome = getIntent().getBooleanExtra("FROM_HOME", false);
        userEmail = getIntent().getStringExtra("USER_EMAIL");

        loadGalleryItems();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gvGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GalleryItem item = galleryList.get(position);

                if (fromHome) {
                    // From Home → Open Product Detail with Order
                    Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                    intent.putExtra("PRODUCT_ID", item.getProductId());
                    intent.putExtra("FROM_GALLERY", false);
                    intent.putExtra("USER_EMAIL", userEmail);
                    startActivity(intent);
                } else {
                    // From "View All Sample Designs" → Open Sample Detail (no order)
                    Intent intent = new Intent(getApplicationContext(), SampleDetailActivity.class);
                    intent.putExtra("SAMPLE_TITLE", item.getTitle());
                    intent.putExtra("SAMPLE_DESCRIPTION", item.getDescription());
                    intent.putExtra("SAMPLE_IMAGE_NAME", item.getImageName());
                    startActivity(intent);
                }
            }
        });
    }

    private void loadGalleryItems() {
        galleryList = new ArrayList<>();

        galleryList.add(new GalleryItem(1, "Business Card Sample", "Modern business card design", "ic_sample1", 1));
        galleryList.add(new GalleryItem(2, "T-Shirt Design", "Custom t-shirt printing design", "ic_sample2", 3));
        galleryList.add(new GalleryItem(3, "Poster Design", "High quality poster design", "ic_sample3", 4));
        galleryList.add(new GalleryItem(4, "Flyer Design", "Creative flyer design", "ic_sample4", 2));
        galleryList.add(new GalleryItem(5, "Sticker Design", "Custom sticker design", "ic_sample5", 5));
        galleryList.add(new GalleryItem(6, "Banner Design", "Outdoor banner design", "ic_sample6", 7));
        galleryList.add(new GalleryItem(7, "Mug Design", "Custom mug printing design", "ic_sample7", 6));
        galleryList.add(new GalleryItem(8, "Brochure Design", "Professional brochure design", "ic_sample8", 8));

        adapter = new GalleryAdapter(this, galleryList);
        gvGallery.setAdapter(adapter);
    }
}