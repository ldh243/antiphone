package com.example.jun.antiphone.cart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jun.antiphone.R;

import java.util.ArrayList;
import java.util.List;
import entity.VoucherLogDTO;

public class voucherLogActivity extends AppCompatActivity {

    View view;
    private List<VoucherLogDTO> posts = new ArrayList<>();
    private RecyclerView rvItems1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_log);

        //get user point
        TextView userPoint = findViewById(R.id.userPoint);


        //Lấy list voucher Log --> đổ vào recyclerVoucherLogAdapter
        // get API ....

        //Dữ lịu test
        posts.add(new VoucherLogDTO(true, "1/1/2018", 1 , 1));
        posts.add(new VoucherLogDTO(true, "2/2/2018", 1 , 1));
        posts.add(new VoucherLogDTO(true, "3/3/2018", 1 , 1));
        posts.add(new VoucherLogDTO(true, "4/4/2018", 1 , 1));
        posts.add(new VoucherLogDTO(true, "5/5/2018", 1 , 1));
        posts.add(new VoucherLogDTO(true, "6/6/2018", 1 , 1));

        RecyclerVoucherLogAdapter adapter = new RecyclerVoucherLogAdapter(posts);
        rvItems1 = findViewById(R.id.rv_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvItems1.setLayoutManager(layoutManager);
        rvItems1.setHasFixedSize(true);
        rvItems1.setAdapter(adapter);
    }
}
