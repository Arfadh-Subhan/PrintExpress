package com.example.printxpress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OfferAdapter extends ArrayAdapter<OfferItem> {

    private Context context;
    private ArrayList<OfferItem> offerList;

    public OfferAdapter(Context context, ArrayList<OfferItem> offerList) {
        super(context, R.layout.item_offer, offerList);
        this.context = context;
        this.offerList = offerList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_offer, parent, false);

        OfferItem item = offerList.get(position);

        TextView tvOfferTitle = rowView.findViewById(R.id.tvOfferTitle);
        TextView tvOfferDiscount = rowView.findViewById(R.id.tvOfferDiscount);
        TextView tvOfferDescription = rowView.findViewById(R.id.tvOfferDescription);
        TextView tvOfferValid = rowView.findViewById(R.id.tvOfferValid);

        tvOfferTitle.setText(item.getTitle());
        tvOfferDiscount.setText(item.getDiscount());
        tvOfferDescription.setText(item.getDescription());
        tvOfferValid.setText("Valid: " + item.getValidUntil());

        return rowView;
    }
}