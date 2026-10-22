package com.example.printxpress;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GuidelineDetailActivity extends AppCompatActivity {

    ImageView ivBack, ivProductImage;
    TextView tvProductName, tvFileFormat, tvColorFormat, tvBleed;
    TextView tvResolution, tvFonts, tvFileSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideline_detail);

        ivBack = findViewById(R.id.ivBack);
        ivProductImage = findViewById(R.id.ivProductImage);
        tvProductName = findViewById(R.id.tvProductName);
        tvFileFormat = findViewById(R.id.tvFileFormat);
        tvColorFormat = findViewById(R.id.tvColorFormat);
        tvBleed = findViewById(R.id.tvBleed);
        tvResolution = findViewById(R.id.tvResolution);
        tvFonts = findViewById(R.id.tvFonts);
        tvFileSize = findViewById(R.id.tvFileSize);

        String productName = getIntent().getStringExtra("PRODUCT_NAME");
        String productImage = getIntent().getStringExtra("PRODUCT_IMAGE");
        String fileFormat = getIntent().getStringExtra("FILE_FORMAT");
        String colorFormat = getIntent().getStringExtra("COLOR_FORMAT");
        String bleed = getIntent().getStringExtra("BLEED");
        String resolution = getIntent().getStringExtra("RESOLUTION");
        String fonts = getIntent().getStringExtra("FONTS");
        String fileSize = getIntent().getStringExtra("FILE_SIZE");

        tvProductName.setText(productName);

        int imageResId = getResources().getIdentifier(productImage, "drawable", getPackageName());
        if (imageResId != 0) {
            ivProductImage.setImageResource(imageResId);
        } else {
            ivProductImage.setImageResource(R.drawable.ic_products);
        }

        tvFileFormat.setText(fileFormat);
        tvColorFormat.setText(colorFormat);
        tvBleed.setText(bleed);
        tvResolution.setText(resolution);
        tvFonts.setText(fonts);
        tvFileSize.setText(fileSize);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}