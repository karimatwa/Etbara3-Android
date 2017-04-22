package com.example.karim.etbara3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Karim on 7/12/2016.
 */
public class Volunteer extends AppCompatActivity {
    public static ArrayList<Event> arrayOfEvent = new ArrayList<>();

    public static Event[] event;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer);
        arrayOfEvent.clear();
        getevents();

    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void getevents() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    _getevents();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void _getevents() throws JSONException {
        JSONArray array = new JSONArray();
        try {
            JSONObject obj = new JSONObject();
            obj.put("org", MainActivity.organization);
            array.put(obj);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://46.101.108.112/etbara3/getevents.php";
        queue.add(new JsonArrayRequest(Request.Method.POST, url, array,
                new Response.Listener<JSONArray>() {
                    public void onResponse(JSONArray response) {
                        try {
                            Gson gson = new Gson();
                            Type t = new TypeToken<Event[]>() {
                            }.getType();
                            event = gson.fromJson(String.valueOf(response), t);
                            for (int i = 0; i < event.length; i++) {
                                arrayOfEvent.add(event[i]);
                            }
                            listView = (ListView) findViewById(R.id.list);
                            EventAdapter adapter = new EventAdapter(Volunteer.this, arrayOfEvent);
                            listView.setAdapter(adapter);
                            listView.requestFocus();
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    JSONObject obj = new JSONObject();
                                    try {
                                        obj.put("profile_id", MainActivity.profile_id);
                                        obj.put("org_id", MainActivity.organization);
                                        obj.put("event_id", event[position].event_id);
                                    } catch (JSONException e) {
                                        System.out.println(e.getMessage());
                                    }

                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                    String url = "http://46.101.108.112/etbara3/volunteer.php";
                                    queue.add(new JsonObjectRequest(Request.Method.POST, url, obj,
                                            new Response.Listener<JSONObject>() {
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        int status = (int) response.get("Status");
                                                        if (status == 1) {
                                                            Toast.makeText(getApplicationContext(), "Registered!", Toast.LENGTH_SHORT).show();
                                                            final Intent intent = new Intent(Volunteer.this, MainActivity.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);
                                                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                                        } else if (status == 2)
                                                            Toast.makeText(getApplicationContext(), "Already Registered!", Toast.LENGTH_SHORT).show();
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
                            });
                        } catch (Exception e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));
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