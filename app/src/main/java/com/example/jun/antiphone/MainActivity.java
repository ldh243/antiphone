package com.example.jun.antiphone;

import android.content.Intent;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jun.antiphone.profile.UserProfileActivity;

public class MainActivity extends AppCompatActivity {

    BottomAppBar bottomAppBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_app_bar);
        bottomAppBar = findViewById(R.id.btnAppBar);
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HoldingActivity.class);
                startActivity(intent);
            }
        });

    }

    public void startHolding(View view) {
        Intent intent = new Intent(this, HoldingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    public void goToProfile(View view) {
        Intent intent = this.getIntent();
        Bundle userInfo = intent.getBundleExtra("USERINFO");
        intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("USERINFO", userInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    public void testFragment(View view) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }
}
