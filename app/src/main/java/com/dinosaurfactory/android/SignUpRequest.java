package com.dinosaurfactory.android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignUpRequest extends StringRequest {

    final static private String URL = "https://dinosaurfactory.000webhostapp.com/sign_up.php";
    private Map<String, String> parameters;

    public SignUpRequest(String id, String pw, String name, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("id",id);
        parameters.put("pw",pw);
        parameters.put("name",name);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
