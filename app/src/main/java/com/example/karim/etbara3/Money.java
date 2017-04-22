package com.example.karim.etbara3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by Karim on 7/3/2016.
 */
public class Money extends AppCompatActivity {


    TextView SelectedCause, SelectedDate;
    EditText ediText;
    private int year;
    private int month;
    private int day;
    int mYear, mMonth, mDay;
    String date= "", currdate = "", amount= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money);

        Bundle extras = getIntent().getExtras();
        SelectedCause = (TextView) findViewById(R.id.textView7);
        SelectedDate = (TextView) findViewById(R.id.textView11);
        SelectedCause.setText("Cause: " + extras.getString("SelectedCause"));
        ediText = (EditText) findViewById(R.id.editText);
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        date =  String.valueOf(mYear) + "-" + String.valueOf(mMonth+1) + "-" + String.valueOf(mDay);
        currdate = date;
        SelectedDate.setText("Date: " + date);
    }

    public void request (View view)
    {
        amount = ediText.getText().toString();
        if (amount.equals(""))
            Toast.makeText(getApplicationContext(), "Fill the required fields!", Toast.LENGTH_SHORT).show();
        else
        {
            JSONObject obj = new JSONObject();
            try {
                obj.put("org_id", MainActivity.organization);
                obj.put("cause_id", Causes.cause[MainActivity.CausePosition].cause_id);
                obj.put("profile_id", MainActivity.profile_id);
                obj.put("amount", amount);
                obj.put("date", date);
                obj.put("currdate", currdate);
            }
            catch (JSONException e) {
                System.out.println(e.getMessage());
            }
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://46.101.108.112/etbara3/requestmoney.php";
            queue.add(new JsonObjectRequest(Request.Method.POST, url, obj,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            int status = (int) response.get("Status");
                            if (status == 1) {
                                Toast.makeText(getApplicationContext(), "Request Saved!", Toast.LENGTH_SHORT).show();
                                final Intent intent = new Intent(Money.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void date (View v)
    {
        DatePickerDialog dialog = new DatePickerDialog(Money.this, datePickerListener, mYear, mMonth, mDay);
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