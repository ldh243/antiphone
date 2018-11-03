package com.example.jun.antiphone.cart;

import android.content.Intent;
import android.media.Image;
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

import java.util.Objects;


public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imgvProfile;
    private FloatingActionButton fabHolding;

    private static String[] tabNames = {"FOOD", "DRINK", "SHOPPING"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        imgvProfile = findViewById(R.id.imgvGoToProfile_Cart);
        imgvProfile.setOnClickListener(this);

        fabHolding = findViewById(R.id.fapCart);
        fabHolding.setOnClickListener(this);

        tabLayout = findViewById(R.id.tabLayoutCart);
        viewPager = findViewById(R.id.viewPagerCart);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentDrink(), tabNames[0]);
        adapter.addFragment(new FragmentFood(), tabNames[1]);
        adapter.addFragment(new FragmentFood(), tabNames[2]);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            TextView tabView = (TextView) LayoutInflater.from(this).inflate(R.layout.item_custom_tab_layout, null);
            tabView.setText(tabNames[i]);
            if (i == 0) {
                tabView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cutlery,0,0);
            } else if (i == 1) {
                tabView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_fruit,0,0);
            } else if (i == 2) {
                tabView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_clothes,0,0);
            }
            tab.setCustomView(tabView);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == imgvProfile) {
            finish();
            Intent intent= new Intent(this, UserProfileActivity.class);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            startActivity(intent);
        }
        if (v == fabHolding) {
            finish();
            Intent intent= new Intent(this, MainActivity.class);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            startActivity(intent);
        }
    }
}
