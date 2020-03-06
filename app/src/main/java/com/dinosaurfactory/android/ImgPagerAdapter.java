package com.dinosaurfactory.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;



public class ImgPagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<String> img =  new ArrayList();

    public ImgPagerAdapter(Context context){
        this.context = context;

    }

    @Override

    public int getItemPosition(Object object) {

        return POSITION_NONE;

    }


    @Override
    public int getCount() {
        return img.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider,container,false);
        ImageView imageView = (ImageView)v.findViewById(R.id.img);
        String url="https://dinosaurfactory.000webhostapp.com/img/"+img.get(position);
        Glide.with(context).load(url).into(imageView);
        container.addView(v);
        return v;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.invalidate();
    }

    public void addimg(String name){
        img.add(name);
    }



}
