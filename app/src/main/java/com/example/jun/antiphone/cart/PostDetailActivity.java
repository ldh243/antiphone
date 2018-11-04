package com.example.jun.antiphone.cart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jun.antiphone.R;
import com.example.jun.antiphone.StoreGroupLocationActivity;
import com.example.jun.antiphone.profile.ConfirmLogoutDialogFragment;
import com.example.jun.antiphone.singleton.RestfulAPIManager;
import com.example.jun.antiphone.singleton.VolleyCallback;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class PostDetailActivity extends AppCompatActivity {

    private final String TAG = "PERSONNALLOG";
    private ImageView ivLocation;
    private int postId;
    private ProgressDialog myProgress;
    private ImageView postDetailImageDetailScreen;
    private TextView tvTitlePostDetailScreen;
    private TextView tvContentDescriptionCartScreen;
    private TextView tvTotalPointsRemainPostDetail;
    private LinearLayout btnGetVoucher;
    private String uid;
    private int storeGroupId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        myProgress = new ProgressDialog(this);
        myProgress.setMessage("Please wait...");
        myProgress.show();
        configToolbar();
        initControl();
        fetchData();
        addControls();
        addEvents();
    }

    private void fetchData() {
        postId = getIntent().getIntExtra("POSTID", -1);
        if (postId != -1) {
            RestfulAPIManager.getInstancẹ̣̣̣().getPostById(this, postId, new VolleyCallback() {
                @Override
                public void onSuccess(Object response) {
                    JsonNode data = (JsonNode) response;
                    String banner = data.get("postBanner").asText();
                    String title = data.get("postTitle").asText();
                    String description = data.get("postDescription").asText();
                    storeGroupId = data.get("storeGroup").get("storeGroupID").asInt();
                    Picasso.with(getApplicationContext()).load(banner).error(R.drawable.image_not_available).into(postDetailImageDetailScreen);
                    tvTitlePostDetailScreen.setText(title);
                    tvContentDescriptionCartScreen.setText(description);
                    myProgress.dismiss();
                }

                @Override
                public void onError(Object ex) {
                    myProgress.dismiss();
                }
            });
        }
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        RestfulAPIManager.getInstancẹ̣̣̣().getTotalPointRemain(this, uid, new VolleyCallback() {
            @Override
            public void onSuccess(Object response) {
                JsonNode data = (JsonNode) response;
                String pointRemain = data.get("left").asText();
                tvTotalPointsRemainPostDetail.setText(pointRemain);
            }
            @Override
            public void onError(Object ex) {

            }
        });
    }

    private void initControl() {
        postDetailImageDetailScreen = findViewById(R.id.postDetailImageDetailScreen);
        tvTitlePostDetailScreen = findViewById(R.id.tvTitlePostDetailScreen);
        tvContentDescriptionCartScreen = findViewById(R.id.tvContentDescriptionCartScreen);
        tvTotalPointsRemainPostDetail = findViewById(R.id.tvTotalPointsRemainPostDetail);
        btnGetVoucher = findViewById(R.id.getVoucher);
    }

    private void addControls() {
        ivLocation = findViewById(R.id.ivLocation);
    }

    private void addEvents() {
        ivLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreGroupLocationActivity.class);
                intent.putExtra("storeGroupID", storeGroupId);
                startActivity(intent);
            }
        });
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        btnGetVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = ConfirmGetVoucherDialogFragment.create("This voucher will expire in 6 hours." +
                        " Are you sure to spent 10 points to get this voucher?", uid, postId, tvTotalPointsRemainPostDetail);
                dialogFragment.show(getSupportFragmentManager(), TAG);
                Log.d("GETVOUCHER", "onClick: GET VOUCHER");
            }
        });

    }

    private void configToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarDetail);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(getApplication(), VoucherLogActivity.class));
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post_detail_menu, menu);
        return true;
    }
}
