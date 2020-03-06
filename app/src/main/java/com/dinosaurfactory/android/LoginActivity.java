package com.dinosaurfactory.android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText editid, editpw;
    Button login,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_sub);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_48dp);

        editid = (EditText)findViewById(R.id.edit_id);
        editpw = (EditText)findViewById(R.id.edit_pw);

        login = (Button)findViewById(R.id.login_button);
        login.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                String id = editid.getText().toString();
                String pw = editpw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                int login_num = jsonResponse.getInt("user_num");
                                String login_id = jsonResponse.getString("id");
                                String login_name = jsonResponse.getString("name");
                                String login_pw = jsonResponse.getString("pw");

                                Toast.makeText(LoginActivity.this, login_name + "님 로그인하였습니다.", Toast.LENGTH_SHORT).show();


                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                SaveSharedPreference.setUser(LoginActivity.this,
                                        login_name,login_pw,login_id,login_num);

                                startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패했습니다")
                                        .setNegativeButton("다시시도",null)
                                        .create().show();
                            }

                        }
                        catch(JSONException e){
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("실패2")
                                    .setNegativeButton("다시시도",null)
                                    .create().show();
                            e.printStackTrace();

                        }
                    }
                };
                LoginRequest request = new LoginRequest(id,pw,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(request);
            }
        });


        signup = (Button)findViewById(R.id.sign_up_button);
        signup.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }

        });
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
