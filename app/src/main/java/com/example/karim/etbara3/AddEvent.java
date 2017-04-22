package com.example.karim.etbara3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by Karim on 7/17/2016.
 */
public class AddEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String org_id;
    EditText etname, etdescription;
    private int year;
    private int month;
    private int day;
    int mYear, mMonth, mDay;
    TextView SelectedDate;
    String date = "", currdate = "", description="", name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addevent);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        etname = (EditText) findViewById(R.id.name);
        etdescription = (EditText) findViewById(R.id.editText);
        SelectedDate = (TextView) findViewById(R.id.textView11);

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        date = String.valueOf(mYear) + "-" + String.valueOf(mMonth+1) + "-" + String.valueOf(mDay);
        currdate = date;
        SelectedDate.setText("Date: " + date);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.organization_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void add (View view)
    {
        name = etname.getText().toString();
        description = etdescription.getText().toString();

        if (description.equals("") || name.equals(""))
            Toast.makeText(getApplicationContext(), "Fill the required fields!", Toast.LENGTH_SHORT).show();
        else
        {
            JSONObject obj = new JSONObject();
            try {
                obj.put("org_id", org_id);
                obj.put("name",  name);
                obj.put("description", description);
                obj.put("date", date);
                obj.put("currdate", currdate);
            }
            catch (JSONException e) {
                System.out.println(e.getMessage());
            }
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://46.101.108.112/etbara3/addevent.php";
            queue.add(new JsonObjectRequest(com.android.volley.Request.Method.POST, url, obj,
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject response) {
                            try {
                                int status = (int) response.get("Status");
                                if (status == 1) {
                                    Toast.makeText(getApplicationContext(), "Event Added!", Toast.LENGTH_SHORT).show();
                                    final Intent intent = new Intent(AddEvent.this, Admin.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                                }
                                else if (status == 2)
                                {
                                    Toast.makeText(getApplicationContext(), "Invalid Date!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }));
        }
    }

    public void date (View v)
    {
        DatePickerDialog dialog = new DatePickerDialog(AddEvent.this, datePickerListener, mYear, mMonth, mDay);
        dialog.show();
    }
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth+1;
            day = selectedDay;
            date = year + "-" + month + "-" + day;
            SelectedDate.setText("Date: " + date);
        }
    };
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        org_id = String.valueOf(pos+1);
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_1:
            {
                final Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return true;
            }
            case R.id.menu_item_2:
            {
                final Intent intent = new Intent(this, RequestsList.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return true;
            }
            case R.id.menu_item_3:
            {
                final Intent intent = new Intent(this, Profile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return true;
            }
            case R.id.menu_item_4:
            {
                SharedPreferences preferences = getSharedPreferences("PREFERENCE", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                final Intent intent = new Intent(this, SignIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return true;
            }
            case R.id.menu_item_5:
            {
                final Intent intent = new Intent(this, About.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}