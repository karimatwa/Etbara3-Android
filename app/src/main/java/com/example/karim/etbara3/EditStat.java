package com.example.karim.etbara3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Karim on 7/17/2016.
 */
public class EditStat  extends AppCompatActivity {

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editstat);

        editText = (EditText) findViewById(R.id.editText);
        JSONObject obj = new JSONObject();
        try {
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://46.101.108.112/etbara3/stat.php";
        queue.add(new JsonObjectRequest(com.android.volley.Request.Method.GET, url, obj,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            int status = (int)response.get("Status");
                            if(status == 1) {
                                editText.setText((String) response.get("data"));
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

    public void update (View view)
    {
        JSONObject obj = new JSONObject();
        try {
            obj.put("data", editText.getText().toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://46.101.108.112/etbara3/stat.php";
        queue.add(new JsonObjectRequest(com.android.volley.Request.Method.POST, url, obj,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            int status = (int)response.get("Status");
                            if(status == 2) {
                                Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
                                final Intent intent = new Intent(EditStat.this, Admin.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
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