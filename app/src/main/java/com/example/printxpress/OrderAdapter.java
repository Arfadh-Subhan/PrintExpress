package com.example.printxpress;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Order> {

    private Context context;
    private ArrayList<Order> orderList;

    public OrderAdapter(Context context, ArrayList<Order> orderList) {
        super(context, R.layout.item_order, orderList);
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_order, parent, false);

        Order order = orderList.get(position);

        ImageView ivOrderIcon = rowView.findViewById(R.id.ivOrderIcon);
        TextView tvOrderProduct = rowView.findViewById(R.id.tvOrderProduct);
        TextView tvOrderQtyPrice = rowView.findViewById(R.id.tvOrderQtyPrice);
        TextView tvOrderDate = rowView.findViewById(R.id.tvOrderDate);
        TextView tvPaymentMethod = rowView.findViewById(R.id.tvPaymentMethod);
        TextView tvOrderStatus = rowView.findViewById(R.id.tvOrderStatus);

        String imageName = order.getProductImage();
        int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        if (imageResId != 0) {
            ivOrderIcon.setImageResource(imageResId);
        } else {
            ivOrderIcon.setImageResource(R.drawable.ic_orders);
        }

        tvOrderProduct.setText(order.getProductName());
        tvOrderQtyPrice.setText("Qty: " + order.getQuantity() + " | " + order.getTotalPrice());
        tvOrderDate.setText("Date: " + order.getOrderDate());

        String payment = order.getPaymentMethod();
        if (payment == null || payment.isEmpty()) {
            payment = "Cash on Delivery";
        }
        tvPaymentMethod.setText("Payment: " + payment);

        String status = order.getStatus();
        tvOrderStatus.setText(status);
        tvOrderStatus.setTextColor(0xFF000000);
        tvOrderStatus.setTypeface(null, Typeface.BOLD);

        if (status.equals("Pending")) {
            tvOrderStatus.setBackgroundColor(0xFFFFF3E0);
        } else if (status.equals("Processing")) {
            tvOrderStatus.setBackgroundColor(0xFFE3F2FD);
        } else if (status.equals("Printing")) {
            tvOrderStatus.setBackgroundColor(0xFFF3E5F5);
        } else if (status.equals("Ready")) {
            tvOrderStatus.setBackgroundColor(0xFFE8F5E9);
        } else if (status.equals("Completed")) {
            tvOrderStatus.setBackgroundColor(0xFFE8F5E9);
        } else if (status.equals("Cancelled")) {
            tvOrderStatus.setBackgroundColor(0xFFFFEBEE);
        }

        return rowView;
    }
}