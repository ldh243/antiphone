package com.example.jun.antiphone.cart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.jun.antiphone.R;
import java.util.ArrayList;
import java.util.List;

import entity.PostDTO;


public class FragmentFood extends Fragment {

    View view;
    private RecyclerView rvItems1;
    private RecyclerDataAdapter adapter;
    private List<PostDTO> posts;
    public FragmentFood() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.food_fragment, container, false);
        posts = new ArrayList<>();
        posts.add(new PostDTO(1, R.drawable.post_image01, "FOOD1" ,"MON AN 1", 0));
        posts.add(new PostDTO(2, R.drawable.post_image01, "FOOD2" ,"MON AN 2", 20));
        posts.add(new PostDTO(3, R.drawable.post_image01, "FOOD3" ,"MON AN 3", 70));
        posts.add(new PostDTO(4, R.drawable.post_image01, "FOOD4" ,"MON AN 4", 0));
        posts.add(new PostDTO(5, R.drawable.post_image01, "FOOD5" ,"MON AN 5", 50));
        posts.add(new PostDTO(6, R.drawable.post_image01, "FOOD6" ,"MON AN 6", 0));
        posts.add(new PostDTO(7, R.drawable.post_image01, "FOOD7" ,"MON AN 7", 70));

        rvItems1 = view.findViewById(R.id.rv_items);
        adapter = new RecyclerDataAdapter(view.getContext(), posts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        rvItems1.setLayoutManager(layoutManager);
        rvItems1.setHasFixedSize(true);
        rvItems1.setAdapter(adapter);
        return view;

    }
}
