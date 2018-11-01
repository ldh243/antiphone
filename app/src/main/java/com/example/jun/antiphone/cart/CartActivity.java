package com.example.jun.antiphone.cart;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.jun.antiphone.R;
import com.example.jun.antiphone.ViewPagerAdapter;


public class CartActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tabLayout = findViewById(R.id.tabLayoutCart);
        viewPager = findViewById(R.id.viewPagerCart);

        ViewPagerAdapter viewFragmentPostAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewFragmentPostAdapter.addFragment(new FragmentDrink(), "DRINK");
        viewFragmentPostAdapter.addFragment(new FragmentFood(), "FOOD");

        viewPager.setAdapter(viewFragmentPostAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

}
