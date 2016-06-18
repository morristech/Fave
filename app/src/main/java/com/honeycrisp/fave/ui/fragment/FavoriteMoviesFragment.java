package com.honeycrisp.fave.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honeycrisp.fave.R;
import com.honeycrisp.fave.ui.adapter.FavoriteMoviesAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan Taylor on 6/5/16.
 */
public class FavoriteMoviesFragment extends android.support.v4.app.Fragment {

    private FavoriteMoviesAdapter recyclerAdapter;

    public static FavoriteMoviesFragment createInstance() {
        FavoriteMoviesFragment fragment = new FavoriteMoviesFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_favorite_movies,
                container, false);
        setUpRecyclerView(recyclerView);
        return recyclerView;
    }

    private void setUpRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new FavoriteMoviesAdapter();
        recyclerAdapter.setData(createItemList());
        recyclerView.setAdapter(recyclerAdapter);
    }

    private List<String> createItemList() {
        List<String> itemList = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null) {
            int itemsCount = 20;
            for (int i = 0; i < itemsCount; i++) {
                itemList.add("Item " + i);
            }
        }
        return itemList;
    }
}
