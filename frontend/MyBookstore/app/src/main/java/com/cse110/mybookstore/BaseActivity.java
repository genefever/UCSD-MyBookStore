package com.cse110.mybookstore;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by genehorecka on 10/31/15.
 */
public class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationDrawer;

    protected FloatingActionButton addButton;

    public static final String BOOK_LIST_TRANSFER = "BOOK_LIST_TRANSFER";

    protected void onCreateDrawer() {

        activateToolbarWithHomeEnabled();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close){
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if(addButton != null) {
                    addButton.setAlpha(1 - slideOffset);
                }
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {

            @Override
            public void run() {

                mDrawerToggle.syncState();

            }

        });

        mNavigationDrawer = (NavigationView) findViewById(R.id.nav_drawer);
        setupDrawerContent(mNavigationDrawer);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                }
        );
    }

    private void selectDrawerItem(MenuItem menuItem) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        Intent intent;

        switch(menuItem.getItemId()) {
            case R.id.selling:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.buying:
                if (!(getClass().equals(BuyingActivity.class))) {
                    intent = new Intent(this, BuyingActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.edit_profile:
                intent = new Intent(this, UserProfile.class);
                startActivity(intent);
                break;

            case R.id.login:
                intent = new Intent(this, Login.class);
                startActivity(intent);
                break;

            default:
                fragmentManager.beginTransaction().replace(R.id.flContent, new Fragment()).commit();

        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    protected ActionBarDrawerToggle getToggle(){
        return mDrawerToggle;
    }

    protected Toolbar activateToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        return mToolbar;
    }

    protected Toolbar activateToolbarWithHomeEnabled() {
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        return mToolbar;
    }

    // Checks internet connection
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
