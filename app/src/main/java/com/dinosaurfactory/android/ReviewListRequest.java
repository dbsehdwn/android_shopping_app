package com.dinosaurfactory.android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReviewListRequest extends StringRequest {
    final static private String URL = "https://dinosaurfactory.000webhostapp.com/reviewlist.php";
    private Map<String, String> parameters;

    public ReviewListRequest( Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
