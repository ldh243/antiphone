package com.example.jun.antiphone.profile;

import android.content.Intent;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jun.antiphone.MainActivity;
import com.example.jun.antiphone.R;
import com.example.jun.antiphone.ViewPagerAdapter;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fapProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentProfile(), "Your stats");
        adapter.addFragment(new FragmentEditProfile(), "Edit Profile");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        fapProfile = findViewById(R.id.fapProfile);
        fapProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == fapProfile) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
}
