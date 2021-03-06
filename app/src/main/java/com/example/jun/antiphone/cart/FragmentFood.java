package com.example.jun.antiphone.cart;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jun.antiphone.R;
import com.example.jun.antiphone.singleton.RestfulAPIManager;
import com.example.jun.antiphone.singleton.VolleyCallback;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

import entity.PostDTO;

public class FragmentFood extends Fragment {

    View view;
    private final String TAG = "APICART";
    private List<PostDTO> posts = new ArrayList<>();
    private RecyclerView rvItems1;
    private ProgressDialog myProgress;
    private RecyclerDataAdapter adapter;
    public FragmentFood() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_item_recycleview, container, false);
        myProgress = new ProgressDialog(getActivity());
        myProgress.setMessage("Please wait...");
        myProgress.setCancelable(true);
        myProgress.show();
        RestfulAPIManager.getInstancẹ̣̣̣().getAllFoodPost(getActivity(), new VolleyCallback() {
            @Override
            public void onSuccess(Object response) {
                JsonNode data = (JsonNode) response;
                for (JsonNode item :
                        data) {
                    PostDTO dto = new PostDTO();
                    int postId = item.get("postID").asInt();
                    String logoStore = item.get("storeGroup").get("storeGroupLogo").asText();
                    String storeTitle = item.get("storeGroup").get("storeGroupName").asText();
                    int discountRate = item.get("postDiscountRate").asInt();
                    String banner = item.get("postBanner").asText();
                    String description = item.get("postDescription").asText();
                    String startDate = item.get("postStartDate").asText();
                    String endDate = item.get("postEndDate").asText();
                    dto.setPostID(postId);
                    dto.setLogoStore(logoStore);
                    dto.setStoreTitle(storeTitle);
                    dto.setDiscountRate(discountRate);
                    dto.setBanner(banner);
                    dto.setDescription(description);
                    dto.setStartDate(startDate);
                    dto.setToDate(endDate);
                    posts.add(dto);
                }
                updateList();
                myProgress.dismiss();
            }

            @Override
            public void onError(Object ex) {
                myProgress.dismiss();
            }
        });

        return view;
    }

    private void updateList() {
        adapter = new RecyclerDataAdapter(view.getContext(), posts);
        rvItems1 = view.findViewById(R.id.rv_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        rvItems1.setLayoutManager(layoutManager);
        rvItems1.setHasFixedSize(true);
        rvItems1.setAdapter(adapter);
    }

}
