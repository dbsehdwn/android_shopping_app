package com.dinosaurfactory.android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class ItemInfomationActivity extends AppCompatActivity {

    ImgPagerAdapter adapter;
    ViewPager viewPager;

    Intent intent;

    String category;
    int namecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_infomation);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_sub);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_48dp);

        intent = getIntent();
        category = intent.getStringExtra("category");
        namecode = intent.getIntExtra("name",0);
        setTitle(namecode);


        viewPager = (ViewPager)findViewById(R.id.img_viewpager);
        adapter = new ImgPagerAdapter(this);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0 ; jsonArray.length() > i ; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String img_name = object.getString("img_name");

                        adapter.addimg(img_name);
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                viewPager.setAdapter(adapter);
            }
        };
        ImgRequest request = new ImgRequest(category,responseListener);
        RequestQueue queue = Volley.newRequestQueue(ItemInfomationActivity.this);
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

    public void Order(View v){

        if(SaveSharedPreference.getUserid(ItemInfomationActivity.this).equals("")){
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            intent = new Intent(this,OrderActivity.class);
            intent.putExtra("category",category);
            startActivity(intent);
        }

    }


}
