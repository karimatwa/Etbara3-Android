package com.example.karim.etbara3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Karim on 7/12/2016.
 */
public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(Context context, ArrayList<Event> event) {
        super(context, 0, event);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        Event event = getItem (position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);
        }

        TextView textview = (TextView) convertView.findViewById(R.id.textView);
        TextView textview2 = (TextView) convertView.findViewById(R.id.textView2);
        TextView textview3 = (TextView) convertView.findViewById(R.id.textView3);
        textview.setText(event.name);
        textview2.setText("Date: " +event.date);
        textview3.setText("Description: " +event.description);
        return convertView;
    }
}