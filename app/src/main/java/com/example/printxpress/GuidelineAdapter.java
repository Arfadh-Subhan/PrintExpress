package com.example.printxpress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GuidelineAdapter extends ArrayAdapter<GuidelineItem> {

    private Context context;
    private ArrayList<GuidelineItem> guidelineList;

    public GuidelineAdapter(Context context, ArrayList<GuidelineItem> guidelineList) {
        super(context, R.layout.item_guideline, guidelineList);
        this.context = context;
        this.guidelineList = guidelineList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_guideline, parent, false);

        GuidelineItem item = guidelineList.get(position);

        ImageView ivImage = rowView.findViewById(R.id.ivGuidelineImage);
        TextView tvTitle = rowView.findViewById(R.id.tvGuidelineTitle);

        String imageName = item.getImageName();
        int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        if (imageResId != 0) {
            ivImage.setImageResource(imageResId);
        } else {
            ivImage.setImageResource(R.drawable.ic_products);
        }

        tvTitle.setText(item.getTitle());

        return rowView;
    }
}