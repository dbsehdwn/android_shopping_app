package com.dinosaurfactory.android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    EditText editid, editpw, editname, editcheckpw;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_sub);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_black_48dp);

        editid = (EditText)findViewById(R.id.edit_id2);
        editpw = (EditText)findViewById(R.id.edit_pw2);
        editname = (EditText)findViewById(R.id.edit_name);
        editcheckpw = (EditText)findViewById(R.id.edit_check_pw);

        signup = (Button)findViewById(R.id.sign_up_button2);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editid.getText().toString();
                String pw = editpw.getText().toString();
                String name = editname.getText().toString();
                String check_pw = editcheckpw.getText().toString();

                if(pw.equals(check_pw)){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
                                    Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                    builder.setMessage("중복된 아이디입니다")
                                            .setNegativeButton("다시시도",null)
                                            .create().show();
                                }
                            }
                            catch(JSONException e){
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                builder.setMessage("실패2")
                                        .setNegativeButton("다시시도",null)
                                        .create().show();
                                e.printStackTrace();

                            }
                        }
                    };
                    SignUpRequest request = new SignUpRequest(id,pw,name,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                    queue.add(request);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage("비밀번호가 일치하지 않습니다")
                            .setNegativeButton("다시시도",null)
                            .create().show();
                }

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
