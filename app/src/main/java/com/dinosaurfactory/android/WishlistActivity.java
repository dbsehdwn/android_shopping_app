package com.dinosaurfactory.android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {

    ListView wishlist;
    WishlistAdapter adapter = new WishlistAdapter();
    String[] category,order_option,price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_sub);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_48dp);
        setTitle("장바구니");

        wishlist = (ListView)findViewById(R.id.wish_listview);


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

    public void Select_Order(View v){
        if(adapter.getcheck().isEmpty()){
            Toast.makeText(this, "선택된 상품이 없습니다.", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(this,Order2Activity.class);
            category = new String[adapter.getcheck().size()];
            order_option = new String[adapter.getcheck().size()];
            price = new String[adapter.getcheck().size()];
            for(int i=0; adapter.getcheck().size()>i;i++){
                category[i]=adapter.getOrder_name(adapter.getcheck().get(i));
                order_option[i] = adapter.getOrder_optioin(adapter.getcheck().get(i));
            }
            intent.putExtra("category",category);
            intent.putExtra("order_option",order_option);
            adapter.getcheck().clear();
            startActivity(intent);

        }
    }

    public void Select_Delete(View v){
        if(adapter.getcheck().isEmpty()){
            Toast.makeText(this, "선택된 상품이 없습니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                    }
                    catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            };

            RequestQueue queue = Volley.newRequestQueue(this);
            for(int i=0; adapter.getcheck().size()>i;i++){
                DeleteWishlistRequest request = new DeleteWishlistRequest(adapter.getNo(adapter.getcheck().get(i)), responseListener);
                queue.add(request);
            }
            Toast.makeText(this, "선택 상품을 삭제하였습니다.", Toast.LENGTH_SHORT).show();
            adapter.getcheck().clear();
            adapter.ListClear();
            onResume();
        }
    }   @Override
    protected void onResume() {
        super.onResume();
        adapter.ListClear();
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0 ; jsonArray.length() > i ; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        int no = object.getInt("no");
                        String name = object.getString("category");
                        String option = object.getString("order_option");

                        adapter.addItem(no,name,option);
                    }

                }
                catch(JSONException e){
                    e.printStackTrace();
                }
                wishlist.setAdapter(adapter);

            }
        };
        GetWishlistRequest request = new GetWishlistRequest(SaveSharedPreference.getUsernum(WishlistActivity.this),responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }
}
