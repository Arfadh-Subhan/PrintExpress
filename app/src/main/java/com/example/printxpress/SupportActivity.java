package com.example.printxpress;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SupportActivity extends AppCompatActivity {

    ImageView ivBack;
    TextView tvEmailValue, tvPhoneValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        ivBack = findViewById(R.id.ivBack);
        tvEmailValue = findViewById(R.id.tvEmailValue);
        tvPhoneValue = findViewById(R.id.tvPhoneValue);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvEmailValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@printxpress.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PrintXpress Support Request");
                startActivity(emailIntent);
            }
        });

        tvPhoneValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                phoneIntent.setData(Uri.parse("tel:+94781218767"));
                startActivity(phoneIntent);
            }
        });
    }
}