package com.example.jun.antiphone.cart;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.jun.antiphone.MainActivity;
import com.example.jun.antiphone.R;
import com.example.jun.antiphone.ViewPagerAdapter;
import com.example.jun.antiphone.profile.UserProfileActivity;


public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imgvProfile;
    private FloatingActionButton fabHolding;
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

        adapter.addFragment(new FragmentDrink(), "FOOD");
        adapter.addFragment(new FragmentFood(), "DRINK");
        adapter.addFragment(new FragmentFood(), "SHOPPING");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            tabLayout.getTabAt(i).setIcon(R.drawable.cutlery);
//        }
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
