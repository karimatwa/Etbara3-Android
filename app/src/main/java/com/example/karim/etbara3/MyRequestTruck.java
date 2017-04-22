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
 * Created by Karim on 7/17/2016.
 */
public class MyRequestTruck extends AppCompatActivity {
    public static ArrayList<CMyRequestTruck> arrayOfTruckRequest = new ArrayList<>();
    public ListView listView;
    public static CMyRequestTruck[] myrequesttruck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myrequesttruck);
        trucklist();
    }


    public void trucklist ()
    {
        arrayOfTruckRequest.clear();
        JSONArray array = new JSONArray();
        try {
            JSONObject obj = new JSONObject();
            obj.put("profile_id", MainActivity.profile_id);
            array.put(obj);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url;

        if (MainActivity.admin.equals("1"))
            url = "http://46.101.108.112/etbara3/agetmytruckrequest.php";
        else
            url = "http://46.101.108.112/etbara3/getmytruckrequest.php";
        queue.add(new JsonArrayRequest(com.android.volley.Request.Method.POST, url, array,
                new Response.Listener<JSONArray>() {
                    public void onResponse(JSONArray response) {
                        try {
                            Gson gson = new Gson();
                            Type t = new TypeToken<CMyRequestTruck[]>() {
                            }.getType();
                            myrequesttruck = gson.fromJson(String.valueOf(response), t);
                            for (int i = 0; i < myrequesttruck.length; i++) {
                                arrayOfTruckRequest.add(myrequesttruck[i]);
                            }
                            listView = (ListView) findViewById(R.id.list);
                            MyRequestTruckAdapter adapter = new MyRequestTruckAdapter(MyRequestTruck.this, arrayOfTruckRequest);
                            listView.setAdapter(adapter);
                            if (MainActivity.admin.equals("1"))
                            {
                                Toast.makeText(getApplicationContext(), "Hold to delete!", Toast.LENGTH_SHORT).show();
                                listView.requestFocus();
                                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                        JSONObject obj = new JSONObject();
                                        try {
                                            obj.put("request_id", myrequesttruck[position].request_id);
                                        } catch (JSONException e) {
                                            System.out.println(e.getMessage());
                                        }

                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                        String url = "http://46.101.108.112/etbara3/deletetruckreq.php";
                                        queue.add(new JsonObjectRequest(com.android.volley.Request.Method.POST, url, obj,
                                                new Response.Listener<JSONObject>() {
                                                    public void onResponse(JSONObject response) {
                                                        try {
                                                            int status = (int) response.get("Status");
                                                            if (status == 1) {
                                                                Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                                                                trucklist();
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
                                        return true;
                                    }
                                });
                            }
                        }
                        catch (Exception e)
                        {}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));
    }
    @Override
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