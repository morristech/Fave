package com.honeycrisp.fave.ui.recyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * A ScrollListener for RecyclerViews that adds support for pagination.
 */
public abstract class ContinuousScrollListener extends RecyclerView.OnScrollListener {

    // Minimum number of items to have below the currently visible items before loading more
    private static final int VISIBLE_THRESHOLD = 5;
    // Layout manager used on the RecyclerView
    private LinearLayoutManager linearLayoutManager;
    // Total number of items in the dataset after the last load
    private int previousTotalItemCount;
    // True if application is waiting for the most recently requested set of data to load
    private boolean loading = true;

    public ContinuousScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    public abstract void onLoadMore();

    @Override
    public void onScrolled(RecyclerView recyclerView, int deltaX, int deltaY) {
        int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        int totalItemCount = linearLayoutManager.getItemCount();

        if (loading && totalItemCount > previousTotalItemCount) {
            // Since the dataset count has increased, assume that the loading has finished.
            loading = false;
            previousTotalItemCount = totalItemCount;
        }
        // If a load is not in progress, check to see if the scroll threshold has been breached.
        else if (!loading && totalItemCount <= (lastVisibleItemPosition + VISIBLE_THRESHOLD)) {
            // The threshold has been breached; attempt to fetch more data.
            onLoadMore();
            loading = true;
        }
        else if (previousTotalItemCount > totalItemCount) {
            // The adapter has been refreshed or invalided; reset previousTotalItemCount.
            previousTotalItemCount = 0;
        }
    }
}