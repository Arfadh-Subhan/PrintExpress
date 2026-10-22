package com.example.printxpress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;
    private ArrayList<Product> productList;

    public ProductAdapter(Context context, ArrayList<Product> productList) {
        super(context, R.layout.item_product, productList);
        this.context = context;
        this.productList = productList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_product, parent, false);

        Product product = productList.get(position);

        ImageView ivProductImage = rowView.findViewById(R.id.ivProductImage);
        TextView tvProductName = rowView.findViewById(R.id.tvProductName);
        TextView tvProductCategory = rowView.findViewById(R.id.tvProductCategory);
        TextView tvProductPrice = rowView.findViewById(R.id.tvProductPrice);

        tvProductName.setText(product.getName());
        tvProductCategory.setText(product.getCategory());
        tvProductPrice.setText(product.getPrice());

        String imageName = product.getImagePath();
        int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        if (imageResId != 0) {
            ivProductImage.setImageResource(imageResId);
        } else {
            ivProductImage.setImageResource(R.drawable.ic_products);
        }

        return rowView;
    }
}