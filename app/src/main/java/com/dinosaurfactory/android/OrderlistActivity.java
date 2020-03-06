package com.dinosaurfactory.android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderlistActivity extends AppCompatActivity {

    ListView orderlist;
    OrderlistAdapter adapter = new OrderlistAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_sub);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_48dp);
        setTitle("주문내역");

        orderlist = (ListView)findViewById(R.id.order_listview);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0 ; jsonArray.length() > i ; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String name = object.getString("category");
                        String option = object.getString("order_option");
                        String state = object.getString("state");

                        adapter.addItem(name,option,state,0);
                    }

                }
                catch(JSONException e){
                    e.printStackTrace();
                }
                orderlist.setAdapter(adapter);

            }
        };
        OrderlistRequest request = new OrderlistRequest(SaveSharedPreference.getUsernum(OrderlistActivity.this),responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
