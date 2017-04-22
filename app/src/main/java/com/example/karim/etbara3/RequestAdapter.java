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
public class RequestAdapter extends ArrayAdapter<Request> {
    public RequestAdapter(Context context, ArrayList<Request> request) {
        super(context, 0, request);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        Request request = getItem (position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.request_item, parent, false);
        }

        TextView textview = (TextView) convertView.findViewById(R.id.textView);
        textview.setText(request.name);
        return convertView;

    }
}