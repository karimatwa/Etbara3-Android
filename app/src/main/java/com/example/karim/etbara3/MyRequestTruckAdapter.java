package com.example.karim.etbara3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Karim on 7/17/2016.
 */
public class MyRequestTruckAdapter extends ArrayAdapter<CMyRequestTruck> {
    public MyRequestTruckAdapter(Context context, ArrayList<CMyRequestTruck> myrequesttruck) {
        super(context, 0, myrequesttruck);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        CMyRequestTruck myrequesttruck = getItem (position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.myrequesttruck_item, parent, false);
        }

        TextView textview = (TextView) convertView.findViewById(R.id.textView);
        TextView textview2 = (TextView) convertView.findViewById(R.id.textView2);
        TextView textview3 = (TextView) convertView.findViewById(R.id.textView3);
        TextView textview4 = (TextView) convertView.findViewById(R.id.textView4);
        textview.setText(myrequesttruck.org_id);
        textview2.setText("Date: " + myrequesttruck.date);
        textview3.setText("Description: " + myrequesttruck.description);

        if (MainActivity.admin.equals("1"))
        {
            textview4.setVisibility(View.VISIBLE);
            textview4.setText(myrequesttruck.profile_id);
        }
        else
            textview4.setVisibility(View.GONE);
        return convertView;
    }
}