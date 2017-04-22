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
public class MyRequestEventAdapter extends ArrayAdapter<CMyRequestEvent> {
    public MyRequestEventAdapter(Context context, ArrayList<CMyRequestEvent> myrequestevent) {
        super(context, 0, myrequestevent);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        CMyRequestEvent myrequestevent = getItem (position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.myrequestevent_item, parent, false);
        }

        TextView textview = (TextView) convertView.findViewById(R.id.textView);
        TextView textview2 = (TextView) convertView.findViewById(R.id.textView2);
        TextView textview3 = (TextView) convertView.findViewById(R.id.textView3);
        TextView textview4 = (TextView) convertView.findViewById(R.id.textView4);
        TextView textview5 = (TextView) convertView.findViewById(R.id.textView5);
        textview.setText(myrequestevent.org_id);
        textview2.setText("Event: " + myrequestevent.event_id);
        textview3.setText("Date: " + myrequestevent.date);
        textview4.setText("Description: " + myrequestevent.description);

        if (MainActivity.admin.equals("1"))
        {
            textview5.setVisibility(View.VISIBLE);
            textview5.setText(myrequestevent.profile_id);
        }
        else
            textview5.setVisibility(View.GONE);

        return convertView;
    }
}