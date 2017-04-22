package com.example.karim.etbara3;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Karim on 7/3/2016.
 */
public class Fragment5 extends Fragment {
    TextView textView, textView2;
    Button button;
    View rootView;
    public Fragment5() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment5, container, false);
        MainActivity.flag = false;
        textView = (TextView) rootView.findViewById(R.id.textView);
        textView2 = (TextView) rootView.findViewById(R.id.textView2);
        button = (Button) rootView.findViewById(R.id.button_admin);
        if (MainActivity.admin.equals("1"))
        {
            textView.setText("Hello Admin \n\n Statistics: \n");
            button.setVisibility(View.VISIBLE);
        }
        else
            button.setVisibility(View.GONE);


        textView2.setText(MainActivity.data);


        return rootView;
    }
}