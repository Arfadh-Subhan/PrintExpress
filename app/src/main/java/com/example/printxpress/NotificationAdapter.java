package com.example.printxpress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationAdapter extends ArrayAdapter<NotificationItem> {

    private Context context;
    private ArrayList<NotificationItem> notificationList;

    public NotificationAdapter(Context context, ArrayList<NotificationItem> notificationList) {
        super(context, R.layout.item_notification, notificationList);
        this.context = context;
        this.notificationList = notificationList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_notification, parent, false);

        NotificationItem item = notificationList.get(position);

        TextView tvTitle = rowView.findViewById(R.id.tvNotificationTitle);
        TextView tvMessage = rowView.findViewById(R.id.tvNotificationMessage);
        TextView tvDate = rowView.findViewById(R.id.tvNotificationDate);
        View viewUnreadDot = rowView.findViewById(R.id.viewUnreadDot);

        tvTitle.setText(item.getTitle());
        tvMessage.setText(item.getMessage());
        tvDate.setText(item.getDate());

        if (item.isRead()) {
            viewUnreadDot.setVisibility(View.GONE);
        } else {
            viewUnreadDot.setVisibility(View.VISIBLE);
        }

        return rowView;
    }
}