package com.honeycrisp.fave.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honeycrisp.fave.R;
import com.honeycrisp.fave.ui.holder.MovieViewHolder;

import java.util.List;

/**
 *
 *
 * Created by Ryan Taylor on 6/5/16.
 */
public class FavoriteMoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private List<String> data;

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie,
                parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (data != null) {
            itemCount = data.size();
        }
        return itemCount;
    }
}
