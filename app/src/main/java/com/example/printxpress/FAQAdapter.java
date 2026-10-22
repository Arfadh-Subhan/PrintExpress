package com.example.printxpress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FAQAdapter extends ArrayAdapter<FAQItem> {

    private Context context;
    private ArrayList<FAQItem> faqList;

    public FAQAdapter(Context context, ArrayList<FAQItem> faqList) {
        super(context, R.layout.item_faq, faqList);
        this.context = context;
        this.faqList = faqList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_faq, parent, false);

        FAQItem item = faqList.get(position);

        LinearLayout llQuestion = rowView.findViewById(R.id.llQuestion);
        TextView tvQuestion = rowView.findViewById(R.id.tvQuestion);
        TextView tvAnswer = rowView.findViewById(R.id.tvAnswer);
        TextView tvArrow = rowView.findViewById(R.id.tvArrow);

        tvQuestion.setText(item.getQuestion());
        tvAnswer.setText(item.getAnswer());

        if (item.isExpanded()) {
            tvAnswer.setVisibility(View.VISIBLE);
            tvArrow.setText("▼");
        } else {
            tvAnswer.setVisibility(View.GONE);
            tvArrow.setText("▶");
        }

        llQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setExpanded(!item.isExpanded());
                notifyDataSetChanged();
            }
        });

        return rowView;
    }
}