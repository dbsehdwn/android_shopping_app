package com.dinosaurfactory.android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Review2Activity extends AppCompatActivity {

    String id,date,text,photo;
    TextView reviewer_id, review_date, review;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review2);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_sub);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_48dp);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        date = intent.getStringExtra("date");
        text = intent.getStringExtra("text");
        photo = intent.getStringExtra("photo");

        reviewer_id = (TextView)findViewById(R.id.reviewer_id);
        review_date = (TextView)findViewById(R.id.reivew_date);
        review = (TextView)findViewById(R.id.reivew_text);
        img = (ImageView)findViewById(R.id.reivew_img);

        reviewer_id.setText(id);
        review_date.setText(date);
        review.setText(text);

        String url = "https://dinosaurfactory.000webhostapp.com/img"+photo;
        if(url.equals("https://dinosaurfactory.000webhostapp.com/imgnull")){
            img.setVisibility(View.GONE);
        }else{
            Glide.with(this).load(url).into(img);
        }

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
