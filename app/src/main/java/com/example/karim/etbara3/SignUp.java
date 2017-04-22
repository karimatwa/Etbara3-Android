package com.example.karim.etbara3;

import android.content.Intent;
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
public class SignUp extends AppCompatActivity {

    EditText etfirstname, etlastname, etaddress, etcity, etphone, etemail, etpassword;
    public static String email, password, name, firstname, lastname, address, city, phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        etfirstname = (EditText) findViewById(R.id.firstname);
        etlastname = (EditText) findViewById(R.id.lastname);
        etaddress = (EditText) findViewById(R.id.address);
        etcity = (EditText) findViewById(R.id.city);
        etphone = (EditText) findViewById(R.id.phone);
        etemail = (EditText) findViewById(R.id.email);
        etpassword = (EditText) findViewById(R.id.password);
    }

    public void signup (View view)
    {
        firstname = etfirstname.getText().toString();
        lastname = etlastname.getText().toString();
        name = firstname + " " + lastname;
        address = etaddress.getText().toString();
        city = etcity.getText().toString();
        phone = etphone.getText().toString();
        email = etemail.getText().toString();
        password = etpassword.getText().toString();

        if (firstname.equals("") || lastname.equals("") || address.equals("") || city.equals("") || phone.equals("") || email.equals("") || password.equals(""))
            Toast.makeText(getApplicationContext(), "Fill the required fields!", Toast.LENGTH_SHORT).show();
        else {
            JSONObject obj = new JSONObject();
            try {
                obj.put("email", email);
            } catch (JSONException e) {
                System.out.println(e.getMessage());
            }

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://46.101.108.112/etbara3/register.php";
            queue.add(new JsonObjectRequest(Request.Method.POST, url, obj,
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject response) {
                            try {
                                int status = (int)response.get("Status");
                                if(status == 1) {
                                    final Intent intent = new Intent(SignUp.this, Verify.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                }
                                else if(status == 2) {
                                    Toast.makeText(getApplicationContext(), "Email already exists!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
