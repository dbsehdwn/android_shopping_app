package com.dinosaurfactory.android;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private String url;
    private String pack;

    ImgPagerAdapter adapter;
    ViewPager viewPager;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = (ViewPager)viewGroup.findViewById(R.id.img_viewpager);
        adapter = new ImgPagerAdapter(getContext());

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0 ; jsonArray.length() > i ; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String img_name = object.getString("img_name");

                        adapter.addimg(img_name);
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                viewPager.setAdapter(adapter);

            }
        };
        ImgRequest request = new ImgRequest("main",responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

        ImageButton.OnClickListener onClickListener = new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.instar :
                        url = "https://www.instagram.com/sinmin_metal";
                        pack = "com.instagram.android";
                        break ;
                    case R.id.twitter:
                        url = "https://twitter.com/sinmin_metal";
                        pack = "com.twitter.android";
                        break ;
                }
                if(getPackageList(pack)){
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                }
                else{
                    String url = "market://details?id=" + pack;
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                }
            }
        } ;
        ImageButton instar_direct = (ImageButton)viewGroup.findViewById(R.id.instar);
        ImageButton twitter_direct = (ImageButton)viewGroup.findViewById(R.id.twitter);
        instar_direct.setOnClickListener(onClickListener) ;
        twitter_direct.setOnClickListener(onClickListener) ;

        return viewGroup;



    }

    public boolean getPackageList(String pack) {
        boolean isExist = false;

        PackageManager pkgMgr = getActivity().getPackageManager();
        List<ResolveInfo> mApps;
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mApps = pkgMgr.queryIntentActivities(mainIntent, 0);

        try {
            for (int i = 0; i < mApps.size(); i++) {
                if(mApps.get(i).activityInfo.packageName.startsWith(pack)){
                    isExist = true;
                    break;
                }
            }
        }
        catch (Exception e) {
            isExist = false;
        }
        return isExist;
    }
}
