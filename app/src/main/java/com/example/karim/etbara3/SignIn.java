package com.example.karim.etbara3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
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
public class SignIn extends AppCompatActivity {

    String SignIn_Email, SignIn_Password;
    EditText Email, Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        Email = (EditText) findViewById(R.id.Email_SignIn);
        Password = (EditText) findViewById(R.id.Password_SignIn);
        SharedPreferences preferences = getSharedPreferences("PREFERENCE", 0);
        SignIn_Email = preferences.getString("email", SignIn_Email);
        SignIn_Password = preferences.getString("password", SignIn_Password);
        Email.setText(SignIn_Email);
        Password.setText(SignIn_Password);

    }
    public void signin (View view) {


        SignIn_Email = Email.getText().toString();
        SignIn_Password = Password.getText().toString();

        CheckBox checkbox = (CheckBox) findViewById(R.id.checkBox);
        if (checkbox.isChecked())
        {
            SharedPreferences preferences = getSharedPreferences("PREFERENCE", 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email", SignIn_Email);
            editor.putString("password", SignIn_Password);
            editor.apply();
        }
        else
        {
            SharedPreferences preferences = getSharedPreferences("PREFERENCE", 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
        }


        if (SignIn_Email.equals("") || SignIn_Password.equals(""))
            Toast.makeText(getApplicationContext(), "Fill the required fields!", Toast.LENGTH_SHORT).show();
        else
        {
            JSONObject obj = new JSONObject();
            try {
                obj.put("email", SignIn_Email);
                obj.put("password", SignIn_Password);
            } catch (JSONException e) {
                System.out.println(e.getMessage());
            }

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://46.101.108.112/etbara3/login.php";
            queue.add(new JsonObjectRequest(Request.Method.POST, url, obj,
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject response) {
                            try {
                                int status = (int)response.get("Status");
                                if(status == 1) {
                                    MainActivity.profile_id = (String) response.get("profile_id");
                                    MainActivity.admin = (String) response.get("admin");
                                    MainActivity.help = true;
                                    final Intent intent = new Intent(SignIn.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                }
                                else if (status== 0)
                                    Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(), "Wrong email or password!", Toast.LENGTH_SHORT).show();
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

    public void signup (View view) {
        final Intent SignUp = new Intent(this, SignUp.class);
        startActivity(SignUp);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
}