package com.dinosaurfactory.android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class OrderlistRequest extends StringRequest {

    final static private String URL = "https://dinosaurfactory.000webhostapp.com/orderlist.php";
    private Map<String, String> parameters;

    public OrderlistRequest( int member_no,Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("member_no",member_no+"");
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
