package com.dinosaurfactory.android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Order2Activity extends AppCompatActivity {

    TextView address,total_price,send_price;
    String name, address_detail,request;
    String[] order_option,category;
    int phonenumber;
    EditText edit_name, edit_num, edit_address, edit_request;
    Intent intent;
    RecyclerView price;
    OrderAdapter adapter = new OrderAdapter();
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order2);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_sub);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_48dp);
        setTitle("배송정보 입력");

        Intent intent = getIntent();
        order_option = intent.getStringArrayExtra("order_option");
        category= intent.getStringArrayExtra("category");


        address = (TextView)findViewById(R.id.address);
        total_price=(TextView)findViewById(R.id.total_price);

        edit_name = (EditText)findViewById(R.id.order_name);
        edit_num = (EditText)findViewById(R.id.phonenumber);
        edit_address = (EditText)findViewById(R.id.detail_address);
        edit_request = (EditText)findViewById(R.id.request);

        button = (Button)findViewById(R.id.final_order);

        price = (RecyclerView)findViewById(R.id.order_list2);
        for(int i=0;order_option.length>i;i++){
            adapter.addItem(category[i],order_option[i],"",20000);
        }

        price.setLayoutManager(new LinearLayoutManager(this)) ;
        price.setAdapter(adapter);
        send_price=(TextView) findViewById(R.id.send_price);


        int price = 0;
        for(int i =0 ; adapter.getCount()>i;i++){
            price += adapter.getItem(i).getPrice();
        }
        if(price >= 30000){
            total_price.setText(String.valueOf(price)+"원");
            send_price.setVisibility(View.GONE);
        }else{
            price+=3000;
            total_price.setText(String.valueOf(price)+"원");
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

    public void SearchAddress(View v){
        Intent intent = new Intent(this, SearchAddressActivity.class);
        startActivityForResult(intent,Code.requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent)
    {
        super.onActivityResult(requestCode,resultCode,resultIntent);
        if(requestCode == Code.requestCode && resultCode == Code.resultCode) {
            address.setText(resultIntent.getStringExtra("address"));

        }

    }
    public void final_Order(View v){
        button.setEnabled(false);
        int user_num = SaveSharedPreference.getUsernum(Order2Activity.this);
        name = edit_name.getText().toString();
        address_detail = address.getText().toString() + edit_address.getText().toString();
        phonenumber = Integer.parseInt(edit_num.getText().toString());
        request = edit_request.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){

                    }else{
                        Toast.makeText(Order2Activity.this, "주문실패", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(JSONException e){
                    Toast.makeText(Order2Activity.this, "주문실패2", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }
            }
        };
        RequestQueue queue = Volley.newRequestQueue(Order2Activity.this);
        for(int i=0;adapter.getCount()>i;i++){
            ApplyRequest apply = new ApplyRequest(user_num,category[i],name,order_option[i],phonenumber,request,address_detail,responseListener);
            queue.add(apply);
            try{
                Thread.sleep(1500);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(Order2Activity.this, MainActivity.class);
        Toast.makeText(Order2Activity.this, "주문이 완료되었습니다.\n" +
                "'주문내역'에서 확인할 수 있습니다.", Toast.LENGTH_LONG).show();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }


}
