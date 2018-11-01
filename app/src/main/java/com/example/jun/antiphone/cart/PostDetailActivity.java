package com.example.jun.antiphone.cart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jun.antiphone.R;
public class PostDetailActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId() == R.id.voucher) {
                Toast.makeText(PostDetailActivity.this, "Get Voucher", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
//
//        Intent intent = getIntent();
//        int imageDir = R.drawable.post_image01;
//        Toast.makeText(this, "image directory " + intent.getStringExtra("IMAGEDIR"), Toast.LENGTH_SHORT).show();
//        try {
//            imageDir = Integer.parseInt(intent.getStringExtra("IMAGEDIR"));
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        LinearLayout image = findViewById(R.id.postDetailImage);
//        image.setBackgroundResource(imageDir);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }


}
