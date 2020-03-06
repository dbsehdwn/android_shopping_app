package com.dinosaurfactory.android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteWishlistRequest extends StringRequest {
    final static private String URL = "https://dinosaurfactory.000webhostapp.com/deletewishlist.php";
    private Map<String, String> parameters;

    public DeleteWishlistRequest(int no, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("no",no+"");
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
