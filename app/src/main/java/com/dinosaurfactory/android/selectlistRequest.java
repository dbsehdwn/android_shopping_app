package com.dinosaurfactory.android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class selectlistRequest extends StringRequest {
    final static private String URL = "https://dinosaurfactory.000webhostapp.com/selectlist.php";
    private Map<String, String> parameters;

    public selectlistRequest(String name,Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("category",name);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
