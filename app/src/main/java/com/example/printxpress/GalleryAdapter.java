package com.example.printxpress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GalleryAdapter extends ArrayAdapter<GalleryItem> {

    private Context context;
    private ArrayList<GalleryItem> galleryList;

    public GalleryAdapter(Context context, ArrayList<GalleryItem> galleryList) {
        super(context, R.layout.item_gallery, galleryList);
        this.context = context;
        this.galleryList = galleryList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_gallery, parent, false);

        GalleryItem item = galleryList.get(position);

        ImageView ivImage = rowView.findViewById(R.id.ivGalleryImage);
        TextView tvTitle = rowView.findViewById(R.id.tvGalleryTitle);
        TextView tvDescription = rowView.findViewById(R.id.tvGalleryDescription);

        String imageName = item.getImageName();
        int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        if (imageResId != 0) {
            ivImage.setImageResource(imageResId);
        } else {
            ivImage.setImageResource(R.drawable.ic_sample1);
        }

        tvTitle.setText(item.getTitle());
        tvDescription.setText(item.getDescription());

        return rowView;
    }
}