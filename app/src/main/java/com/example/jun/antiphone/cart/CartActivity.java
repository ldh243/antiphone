package com.example.jun.antiphone.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jun.antiphone.MainActivity;
import com.example.jun.antiphone.R;
import com.example.jun.antiphone.ViewPagerAdapter;
import com.example.jun.antiphone.profile.UserProfileActivity;


public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView ivProfile;
    private FloatingActionButton fabHolding;
    private int[] iconTabLayout = new int[]{R.drawable.ic_diet_disable, R.drawable.ic_video_camera_disable, R.drawable.ic_waistcoat_disable};
    private int[] iconTabLayoutSelected = new int[]{R.drawable.ic_diet, R.drawable.ic_video_camera, R.drawable.ic_waistcoat};
    private static String[] tabNames = {"Drink & Food", "Entertainment", "Clothes"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ivProfile = findViewById(R.id.imgvGoToProfile_Cart);
        ivProfile.setOnClickListener(this);

        fabHolding = findViewById(R.id.fapCart);
        fabHolding.setOnClickListener(this);

        tabLayout = findViewById(R.id.tabLayoutCart);
        viewPager = findViewById(R.id.viewPagerCart);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentFood(), null);
        adapter.addFragment(new FragmentEntertainment(), null);
        adapter.addFragment(new FragmentClothes(), null);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setIcon(iconTabLayout[i]);
//            TextView tabView = (TextView) LayoutInflater.from(this).inflate(R.layout.item_custom_tab_layout, null);
//            tabView.setText(tabNames[i]);
//            tabView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, iconTabLayout[i]);
//            tabView.setCompoundDrawablesWithIntrinsicBounds(0, iconTabLayout[i], 0, 0);
//            tab.setCustomView(tabView);
        }
        int position = tabLayout.getSelectedTabPosition();
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        tab.setIcon(iconTabLayoutSelected[position]);
        tab.setText(tabNames[position]);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(iconTabLayoutSelected[tab.getPosition()]);
                tab.setText(tabNames[tab.getPosition()]);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(iconTabLayout[tab.getPosition()]);
                tab.setText(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v == ivProfile) {
            finish();
            Intent intent = new Intent(this, UserProfileActivity.class);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            startActivity(intent);
        }
        if (v == fabHolding) {
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            startActivity(intent);
        }
    }
}
