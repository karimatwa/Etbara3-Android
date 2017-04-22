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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
public class Causes extends AppCompatActivity {
    public static ArrayList<Cause> arrayOfCause = new ArrayList<>();
    public ListView listView;
    public static Cause[] cause;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        arrayOfCause.clear();
        getcauses();
        setContentView(R.layout.causes);
    }
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void getcauses()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    _getcauses();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void _getcauses() throws JSONException
    {
        JSONArray array = new JSONArray();
        try {
            JSONObject obj = new JSONObject();
            obj.put("org", MainActivity.organization);
            array.put(obj);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://46.101.108.112/etbara3/getcauses.php";
        queue.add(new JsonArrayRequest(Request.Method.POST, url, array,
                new Response.Listener<JSONArray>() {
                    public void onResponse(JSONArray response) {
                        try {
                            Gson gson = new Gson();
                            Type t = new TypeToken<Cause[]>() {
                            }.getType();
                             cause = gson.fromJson(String.valueOf(response), t);
                            for (int i = 0; i < cause.length; i++) {
                                arrayOfCause.add(cause[i]);
                            }
                        listView = (ListView) findViewById(R.id.list);
                        CauseAdapter adapter = new CauseAdapter(Causes.this, arrayOfCause);
                        listView.setAdapter(adapter);
                        listView.requestFocus();
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                MainActivity.CausePosition = position;
                                String Selected = cause[MainActivity.CausePosition].name;
                                final Intent intent = new Intent(Causes.this, Money.class);
                                intent.putExtra("SelectedCause", Selected);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                            }
                        });
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
