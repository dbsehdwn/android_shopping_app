package com.dinosaurfactory.android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ImgRequest extends StringRequest {

    final static private String URL = "https://dinosaurfactory.000webhostapp.com/img.php";
    private Map<String, String> paramters;

    public ImgRequest(String category,Response.Listener<String> listener ){
        super(Method.POST,URL,listener,null);
        paramters = new HashMap<>();
        paramters.put("category",category);
    }

    @Override
    public Map<String, String> getParams() { return paramters;}
}
