package com.dinosaurfactory.android;



import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {

    ReviewAdapter adapter;
    ListView reviewlist;

    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(
                R.layout.fragment_review, container, false);
        reviewlist = (ListView)viewGroup.findViewById(R.id.review_list);
        adapter = new ReviewAdapter();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0 ; jsonArray.length() > i ; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String date = object.getString("date");
                        String text = object.getString("text");
                        String photo = object.getString("photo");

                        adapter.addItem(id,date,text,photo);
                    }

                }
                catch(JSONException e){
                    e.printStackTrace();
                }
                reviewlist.setAdapter(adapter);
                AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getContext(), Review2Activity.class);
                        intent.putExtra("id", adapter.getid(position));
                        intent.putExtra("date", adapter.getdate(position));
                        intent.putExtra("text", adapter.gettext(position));
                        intent.putExtra("photo", adapter.getphoto(position));
                        startActivity(intent);
                    }
                };
                reviewlist.setOnItemClickListener(listener);
            }
        };
        ReviewListRequest request = new ReviewListRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);


        return viewGroup;
    }

}
