package com.dinosaurfactory.android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ApplyRequest extends StringRequest {
    final static private String URL = "https://dinosaurfactory.000webhostapp.com/order_apply.php";
    private Map<String, String> parameters;

    public ApplyRequest(int user_num,String category,String name, String order_list, int phone, String request, String address, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("name",name);
        parameters.put("category",category);
        parameters.put("order_option",order_list);
        parameters.put("address",address);
        parameters.put("request",request);
        parameters.put("phone",phone+"");
        parameters.put("user_num",user_num+"");

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}

