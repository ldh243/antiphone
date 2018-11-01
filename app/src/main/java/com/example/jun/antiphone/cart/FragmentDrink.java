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

public class FragmentDrink extends Fragment {

    View view;
    private List<PostDTO> posts1 = new ArrayList<>();
    private RecyclerView rvItems1;

    public FragmentDrink() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.food_fragment, container, false);

        posts1.add(new PostDTO(1, R.drawable.avatar_kelly, "FOOD1" ,"DO UONG 1", 0));
//        posts1.add(new PostDTO(2, R.drawable.post_image01, "FOOD2" ,"DO UONG 2", 20));
//        posts1.add(new PostDTO(3, R.drawable.post_image01, "FOOD3" ,"DO UONG 3", 50));

        RecyclerDataAdapter adapter = new RecyclerDataAdapter(view.getContext(), posts1);
        rvItems1 = view.findViewById(R.id.rv_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        rvItems1.setLayoutManager(layoutManager);
        rvItems1.setHasFixedSize(true);
        rvItems1.setAdapter(adapter);

        return  view;

    }
}
