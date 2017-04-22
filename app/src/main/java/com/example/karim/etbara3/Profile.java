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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Karim on 7/3/2016.
 */
public class Profile extends AppCompatActivity {

    EditText name, city, address, phone, email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        name = (EditText) findViewById(R.id.name);
        city = (EditText) findViewById(R.id.city);
        address = (EditText) findViewById(R.id.address);
        phone = (EditText) findViewById(R.id.number);
        email = (EditText) findViewById(R.id.email);
        email.setEnabled(false);
        password = (EditText) findViewById(R.id.password);
        JSONObject obj = new JSONObject();

        try {
            obj.put("profile_id", MainActivity.profile_id);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://46.101.108.112/etbara3/getprofile.php";

        queue.add(new JsonObjectRequest(Request.Method.POST, url, obj,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            name.setText((String) response.get("name"));
                            city.setText((String) response.get("city"));
                            address.setText((String) response.get("address"));
                            phone.setText((String) response.get("phone"));
                            email.setText((String) response.get("email"));
                            password.setText((String) response.get("password"));
                        } catch (Exception e) {
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

    public void save(View view)
    {
        JSONObject obj = new JSONObject();
        try {
            obj.put("profile_id", MainActivity.profile_id);
            obj.put("name", name.getText());
            obj.put("address", address.getText());
            obj.put("city", city.getText());
            obj.put("phone", phone.getText());
            obj.put("password", password.getText());
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://46.101.108.112/etbara3/saveprofile.php";

        queue.add(new JsonObjectRequest(Request.Method.POST, url, obj,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            int status = (int) response.get("Status");
                            if (status == 1)
                            {
                                Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
                                final Intent intent = new Intent(Profile.this, MainActivity.class);
                                startActivity(intent);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                            }
                            else
                                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
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
