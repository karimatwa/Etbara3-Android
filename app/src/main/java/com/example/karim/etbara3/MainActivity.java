package com.example.karim.etbara3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Organization[] organization_menu;
    private List<ItemSlideMenu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    public static boolean flag = false;
    public static int organization;
    public static String profile_id;
    public static String admin;
    public static int CausePosition;
    public static String data;
    String org[] = new String [4];
    public static Boolean help = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init component
        listViewSliding = (ListView) findViewById(R.id.lv_sliding_menu);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listSliding = new ArrayList<>();
        //Add item for sliding list
        getmenuorg();

        //Display icon to open/ close sliding list
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        drawerLayout.closeDrawer(listViewSliding);
        if (help)
        {
            Toast helpmsg = Toast.makeText(getApplicationContext(), "<- Check this menu!", Toast.LENGTH_SHORT);
            helpmsg.setGravity(Gravity.TOP , -200, 20);
            helpmsg.show();
            help = false;
        }
        //Hanlde on item click

        listViewSliding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Set title
                setTitle(listSliding.get(position).getTitle());
                //item selected
                listViewSliding.setItemChecked(position, true);
                //Replace fragment
                replaceFragment(position);
                //Close menu
                drawerLayout.closeDrawer(listViewSliding);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }
    public void getmenuorg()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    _getmenuorg();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void _getmenuorg() throws JSONException
    {
        JSONObject obj = new JSONObject();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://46.101.108.112/etbara3/getorg.php";
        String url2;
        if (admin.equals("1"))
            url2 = "http://46.101.108.112/etbara3/agetstatistics.php";
        else
            url2 = "http://46.101.108.112/etbara3/getstatistics.php";

        queue.add(new JsonObjectRequest(Request.Method.POST, url2, obj,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            data = (String) response.get("data");
                            replaceFragment(4);
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


        JSONArray array = new JSONArray();
        queue.add(new JsonArrayRequest(Request.Method.GET, url, array,
                new Response.Listener<JSONArray>() {
                    public void onResponse(JSONArray response) {
                        try {
                            Gson gson = new Gson();
                            Type t = new TypeToken<Organization[]>() {
                            }.getType();
                            organization_menu = gson.fromJson(String.valueOf(response), t);
                            for (int i = 0; i < organization_menu.length; i++) {
                                listSliding.add(new ItemSlideMenu(organization_menu[i].photo, organization_menu[i].name));
                            }
                            adapter = new SlidingMenuAdapter(MainActivity.this, listSliding);
                            listViewSliding.setAdapter(adapter);
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
        // Handle item selection
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    //Create method replace fragment

    private void replaceFragment(int pos) {
        Fragment fragment = null;
        switch (pos) {
            case 0:
                fragment = new Fragment1();
                organization = 1;
                break;
            case 1:
                fragment = new Fragment2();
                organization = 2;
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case 2:
                fragment = new Fragment3();
                organization = 3;
                break;
            case 3:
                fragment = new Fragment4();
                organization = 4;
                break;
            case 4:
                fragment = new Fragment5();
                organization = 4;
                break;
            default:
                fragment = new Fragment5();
                organization = -1;
                break;
        }

        if(null!=fragment) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
    public void onBackPressed() {
        if (flag) {
            replaceFragment(4);
            setTitle("Etbara3");
        }
        else
            this.finish();
    }
    public void money (View view)
    {
        final Intent intent = new Intent(this, Causes.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
    public void truck (View view)
    {
        final Intent intent = new Intent(this, Truck.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
    public void volunteer (View view)
    {
        final Intent intent = new Intent(this, Volunteer.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
    public void whoarewe (View view)
    {
        final Intent intent = new Intent(this, Whoarewe.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public void admin (View view)
    {
        final Intent intent = new Intent(this, Admin.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

}
