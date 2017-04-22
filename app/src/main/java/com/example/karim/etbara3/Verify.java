package com.example.karim.etbara3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
public class Verify  extends AppCompatActivity {

    EditText etcode;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify);
        etcode = (EditText) findViewById(R.id.code);
    }

    public void verify (View view)
    {
        code = etcode.getText().toString();

        if (code.equals(""))
            Toast.makeText(getApplicationContext(), "Fill the required field!", Toast.LENGTH_SHORT).show();
        else {
            JSONObject obj = new JSONObject();
            try {
                obj.put("code", code);
                obj.put("email", SignUp.email);
                obj.put("password", SignUp.password);
                obj.put("name", SignUp.name);
                obj.put("phone", SignUp.phone);
                obj.put("address", SignUp.address);
                obj.put("city", SignUp.city);
            } catch (JSONException e) {
                System.out.println(e.getMessage());
            }

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://46.101.108.112/etbara3/verify.php";
            queue.add(new JsonObjectRequest(Request.Method.POST, url, obj,
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject response) {
                            try {
                                int status = (int)response.get("Status");
                                if(status == 1) {
                                    MainActivity.profile_id = (String) response.get("profile_id");
                                    MainActivity.admin = (String) response.get("admin");
                                    SharedPreferences preferences = getSharedPreferences("PREFERENCE", 0);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("email", SignUp.email);
                                    editor.putString("password", SignUp.password);
                                    editor.apply();
                                    MainActivity.help = true;
                                    final Intent intent = new Intent(Verify.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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
}
