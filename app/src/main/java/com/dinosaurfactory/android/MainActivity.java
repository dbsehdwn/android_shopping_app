package com.dinosaurfactory.android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.IntentCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
                            implements NavigationView.OnNavigationItemSelectedListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    int user_num;
    String id,name,pw;

    Button Login, Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        final DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,toolbar,R.string.nav_open, R.string.nav_close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.baseline_menu_black_48dp);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        //drawer.addDrawerListener(toggle);
        //toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        View nav_header_view = navigationView.getHeaderView(0);
        Login = (Button)nav_header_view.findViewById(R.id.login_button);
        Logout = (Button)nav_header_view.findViewById(R.id.logout_button);

        id = SaveSharedPreference.getUserid(MainActivity.this);
        name = SaveSharedPreference.getUserName(MainActivity.this);
        pw = SaveSharedPreference.getUserpw(MainActivity.this);
        user_num = SaveSharedPreference.getUsernum(MainActivity.this);

        if(user_num != 0){
            Login.setVisibility(View.GONE);
            Logout.setVisibility(View.VISIBLE);
        } else {
            Login.setVisibility(View.VISIBLE);
            Logout.setVisibility(View.GONE);
        }

        tabLayout = (TabLayout)findViewById(R.id.tab);
        viewPager = (ViewPager)findViewById(R.id.content_frame);


        TabPageAdapter pageAdapter = new TabPageAdapter(
                getSupportFragmentManager(), tabLayout.getTabCount()
        );
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent intent = null;

        switch (id){

            case R.id.order_list:
                intent = new Intent(this, OrderlistActivity.class);
                break;

            case R.id.wishlist:
                intent = new Intent(this, WishlistActivity.class);
                break;
            case R.id.review:

                intent = new Intent(this, WriteReviewActivity.class);
                break;
        }
        if(user_num == 0){
            Toast.makeText(this, "로그인이 필요합니다", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    public void Login(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void Logout(View v){
        SaveSharedPreference.clearUser(this);
        Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(this, MainActivity.class));

    }

}
