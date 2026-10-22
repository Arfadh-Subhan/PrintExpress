package com.example.printxpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GuidelinesActivity extends AppCompatActivity {

    ImageView ivBack;
    GridView gvGuidelines;

    ArrayList<GuidelineItem> guidelineList;
    GuidelineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelines);

        ivBack = findViewById(R.id.ivBack);
        gvGuidelines = findViewById(R.id.gvGuidelines);

        loadGuidelineItems();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gvGuidelines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GuidelineItem item = guidelineList.get(position);
                Intent intent = new Intent(getApplicationContext(), GuidelineDetailActivity.class);
                intent.putExtra("PRODUCT_NAME", item.getTitle());
                intent.putExtra("PRODUCT_IMAGE", item.getImageName());
                intent.putExtra("FILE_FORMAT", item.getFileFormat());
                intent.putExtra("COLOR_FORMAT", item.getColorFormat());
                intent.putExtra("BLEED", item.getBleed());
                intent.putExtra("RESOLUTION", item.getResolution());
                intent.putExtra("FONTS", item.getFonts());
                intent.putExtra("FILE_SIZE", item.getFileSize());
                startActivity(intent);
            }
        });
    }

    private void loadGuidelineItems() {
        guidelineList = new ArrayList<>();

        guidelineList.add(new GuidelineItem("Business Cards", "ic_business_card",
                "PDF, AI, JPEG, PNG", "CMYK", "3mm bleed on all sides",
                "300 DPI minimum", "Embed fonts or convert to outlines", "Max 10MB"));

        guidelineList.add(new GuidelineItem("Flyer (A4)", "ic_flyer",
                "PDF, JPEG, PNG", "CMYK", "3mm bleed on all sides",
                "300 DPI minimum", "Embed fonts", "Max 20MB"));

        guidelineList.add(new GuidelineItem("T-Shirt Printing", "ic_tshirt",
                "PNG with transparency", "RGB", "No bleed needed",
                "300 DPI minimum", "Not applicable", "Max 20MB"));

        guidelineList.add(new GuidelineItem("Poster (A3)", "ic_poster",
                "PDF, AI, JPEG", "CMYK", "3mm bleed on all sides",
                "300 DPI minimum", "Convert text to outlines", "Max 30MB"));

        guidelineList.add(new GuidelineItem("Sticker (100pcs)", "ic_sticker",
                "PNG with transparency", "CMYK", "2mm bleed on all sides",
                "300 DPI minimum", "Not applicable", "Max 15MB"));

        guidelineList.add(new GuidelineItem("Mug Printing", "ic_mug",
                "PNG, JPEG", "RGB", "No bleed needed",
                "300 DPI minimum", "Not applicable", "Max 15MB"));

        guidelineList.add(new GuidelineItem("Banner (3x2ft)", "ic_banner",
                "PDF, AI, EPS", "CMYK", "10mm bleed on all sides",
                "150 DPI minimum", "Convert text to outlines", "Max 40MB"));

        guidelineList.add(new GuidelineItem("Brochure", "ic_brochure",
                "PDF, AI, JPEG", "CMYK", "3mm bleed on all sides",
                "300 DPI minimum", "Embed fonts", "Max 25MB"));

        adapter = new GuidelineAdapter(this, guidelineList);
        gvGuidelines.setAdapter(adapter);
    }
}