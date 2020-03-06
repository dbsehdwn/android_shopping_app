package com.dinosaurfactory.android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WishlistRequest extends StringRequest {
    final static private String URL = "https://dinosaurfactory.000webhostapp.com/wishlist.php";
    private Map<String, String> parameters;

    public WishlistRequest(String option, String category, int user_no, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("option",option);
        parameters.put("category",category);
        parameters.put("user_no",user_no+"");
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
