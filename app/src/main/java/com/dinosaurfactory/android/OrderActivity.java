package com.dinosaurfactory.android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ListView select_list;
    SelectOrderAdapter adapter;
    String[] category = new String[1];
    String[] order_option = {"null"};


    Intent intent;

    ArrayList count, ring_size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_sub);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_48dp);


        select_list = (ListView)findViewById(R.id.select_list);
        adapter = new SelectOrderAdapter();

        intent = getIntent();
        category[0] = intent.getStringExtra("category");

        count = new ArrayList();
        ring_size = new ArrayList();

        int i=1;
        while(i<21){
            count.add(i);
            i++;
        }
        int j=1;
        while(j<28){
            ring_size.add(j+"호");
            j++;
        }

        adapter.addItem("수량", count);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray option = jsonObject.getJSONArray("list");
                    System.out.println(option);
                    for(int i = 0 ; option.length() > i ; i++) {
                        ArrayList pick = new ArrayList();
                        String list = option.getString(i);
                        JSONArray jpick = jsonObject.getJSONArray(list);
                        for(int j=0; jpick.length() >j ; j++){
                            pick.add(jpick.getString(j));
                        }

                        adapter.addItem(list,pick);
                    }
                    adapter.addItem("반지 사이즈", ring_size);
                    select_list.setAdapter(adapter);


                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        };
        selectlistRequest request = new selectlistRequest(category[0],responseListener);
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

    public void Next(View v){

        ArrayList<String> optionlist = new ArrayList<String>();

        optionlist = adapter.getOrder_option();
        for(int i=0; adapter.getCount() > i;i++){
            if(i==3){
                if(!(optionlist.get(2).contains("반지"))){
                    continue;
                }
            }
            if(order_option[0].equals("null")){
                order_option[0] = optionlist.get(i)+"\n";
            }
            else{
                order_option[0] = order_option[0] + optionlist.get(i)+"\n";
            }
        }
        Intent intent = new Intent(this, Order2Activity.class);
        intent.putExtra("order_option",order_option);
        intent.putExtra("category",category);
        startActivity(intent);
    }

    public void wishlist(View v){
        ArrayList<String> optionlist = new ArrayList<String>();

        optionlist = adapter.getOrder_option();
        for(int i=0; adapter.getCount() > i;i++){
            if(i==3){
                if(!(optionlist.get(2).contains("반지"))){
                    continue;
                }
            }
            if(order_option[0].equals("null")){
                order_option[0] = optionlist.get(i)+"\n";
            }
            else{
                order_option[0] = order_option[0] + optionlist.get(i)+"\n";
            }
        }
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                        Toast.makeText(OrderActivity.this, "장바구니에 상품이 담겼습니다 .", Toast.LENGTH_LONG).show();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{

                    }
                }
                catch(JSONException e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                    builder.setMessage("실패2")
                            .setNegativeButton("다시시도",null)
                            .create().show();
                    e.printStackTrace();

                }
            }
        };
        WishlistRequest request = new WishlistRequest(order_option[0],category[0],
                SaveSharedPreference.getUsernum(OrderActivity.this),responseListener);
        RequestQueue queue = Volley.newRequestQueue(OrderActivity.this);
        queue.add(request);
    }

}
