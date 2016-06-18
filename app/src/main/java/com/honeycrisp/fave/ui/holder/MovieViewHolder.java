package com.honeycrisp.fave.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.honeycrisp.fave.R;

/**
 * Created by Ryan Taylor on 6/5/16.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder {

    private final TextView movieTitleTextView;

    public MovieViewHolder(View itemView) {
        super(itemView);
        movieTitleTextView = (TextView) itemView.findViewById(R.id.text_movie_title);
    }

    public void bind(String movieTitle) {
        movieTitleTextView.setText(movieTitle);
    }
}
