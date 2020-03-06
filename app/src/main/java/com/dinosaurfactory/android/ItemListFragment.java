package com.dinosaurfactory.android;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemListFragment extends Fragment {

    String test1;


    public ItemListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView pizzaRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_item_list, container,false);

        final int[] categoryNames = new int[Category.categories.length];
        for(int i = 0 ; i<categoryNames.length; i++){
            categoryNames[i] = Category.categories[i].getName();
        }

        int[] categoryImages = new int[Category.categories.length];
        for(int i =0; i<categoryImages.length;i++){
            categoryImages[i] = Category.categories[i].getImageId();
        }

        final String[] category = new String[Category.categories.length];
        for(int i = 0 ; i<category.length; i++){
            category[i] = Category.categories[i].getCategory();
        }


        CategoryAdapter adapter = new CategoryAdapter(categoryNames, categoryImages);
        pizzaRecycler.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        pizzaRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new CategoryAdapter.Listener(){

            public void onClick(int position){

                Intent intent = new Intent(getActivity(), ItemInfomationActivity.class);
                intent.putExtra("category",category[position]);
                intent.putExtra("name",categoryNames[position]);
                getActivity().startActivity(intent);


            }
        });
        return pizzaRecycler;
    }



}
