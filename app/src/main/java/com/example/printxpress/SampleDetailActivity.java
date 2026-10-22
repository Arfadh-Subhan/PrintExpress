package com.example.printxpress;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SampleDetailActivity extends AppCompatActivity {

    ImageView ivBack;
    ImageView imgSample;
    TextView txtSampleTitle;
    TextView txtSampleDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_detail);

        ivBack = findViewById(R.id.ivBack);
        imgSample = findViewById(R.id.imgSample);
        txtSampleTitle = findViewById(R.id.txtSampleTitle);
        txtSampleDesc = findViewById(R.id.txtSampleDesc);

        String title = getIntent().getStringExtra("SAMPLE_TITLE");
        String description = getIntent().getStringExtra("SAMPLE_DESCRIPTION");
        String imageName = getIntent().getStringExtra("SAMPLE_IMAGE_NAME");

        txtSampleTitle.setText(title);
        txtSampleDesc.setText(description);

        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if (imageResId != 0) {
            imgSample.setImageResource(imageResId);
        } else {
            imgSample.setImageResource(R.drawable.ic_sample1);
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}