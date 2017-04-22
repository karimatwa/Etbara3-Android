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
public class CauseAdapter extends ArrayAdapter<Cause> {
    public CauseAdapter(Context context, ArrayList<Cause> cause) {
        super(context, 0, cause);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        Cause cause = getItem (position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cause_item, parent, false);
        }

        TextView textview = (TextView) convertView.findViewById(R.id.textView);
        textview.setText(cause.name);
        return convertView;

    }
}
