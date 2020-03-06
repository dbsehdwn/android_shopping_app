package com.dinosaurfactory.android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReviewRequest extends StringRequest {
    final static private String URL = "https://dinosaurfactory.000webhostapp.com/review.php";
    private Map<String, String> parameters;

    public ReviewRequest(int num,String review, String photo, String date,Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("num",num + "");
        parameters.put("review",review);
        parameters.put("photo",photo);
        parameters.put("date",date);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
