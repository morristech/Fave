package com.honeycrisp.fave.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.honeycrisp.fave.R;
import com.honeycrisp.fave.controller.MovieSearchController;
import com.honeycrisp.fave.ui.adapter.FragmentPager;
import com.honeycrisp.fave.ui.fragment.FavoriteMoviesFragment;

public class HomeActivity extends AppCompatActivity {

    private MovieSearchController favoriteMoviesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        View rootView = findViewById(R.id.coordinator_layout);
        if (rootView != null) {
            final Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            AppBarLayout appBarLayout = (AppBarLayout) rootView.findViewById(R.id.app_bar_layout);
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    // Fade in the contents of the Toolbar when the AppBarLayout expands and fade out
                    // the contents when the AppBarLayout collapses
                    float alpha = (verticalOffset / 100.0f) + 1;
                    toolbar.setAlpha(alpha);
                }
            });

            initViewPagerAndTabs();

            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction(R.string.action_undo, null).show();
                }
            });
        }

        favoriteMoviesController = new MovieSearchController();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }

    private void initViewPagerAndTabs() {
        FragmentPager pagerAdapter = new FragmentPager(getSupportFragmentManager());
        pagerAdapter.addFragment(FavoriteMoviesFragment.createInstance(), getString(R.string.tab_title_movies));
        pagerAdapter.addFragment(FavoriteMoviesFragment.createInstance(), getString(R.string.tab_title_music));
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        assert viewPager != null;
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);
    }
}
