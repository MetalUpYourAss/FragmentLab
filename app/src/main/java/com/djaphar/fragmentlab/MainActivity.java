package com.djaphar.fragmentlab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment gitRepoFragment, mapsFragment, contactsFragment, infoFragment, sensorAndCameraFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        gitRepoFragment = new GitRepoFragment();
        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putStringArray("Repos", Objects.requireNonNull(getIntent().getExtras()).getStringArray("Repositories"));
        fragmentArgs.putString("Own", Objects.requireNonNull(getIntent().getExtras().get("Owner")).toString());
        gitRepoFragment.setArguments(fragmentArgs);

        mapsFragment = new MapsFragment();
        contactsFragment = new ContactsFragment();
        infoFragment = new InfoFragment();
        sensorAndCameraFragment = new SensorAndCameraFragment();

        navigationView.setCheckedItem(R.id.nav_github_auth);
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, gitRepoFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_github_auth:
                fragment = gitRepoFragment;
                break;
            case R.id.nav_maps:
                fragment = mapsFragment;
                break;
            case R.id.nav_contacts:
                fragment = contactsFragment;
                break;
            case R.id.nav_info:
                fragment = infoFragment;
                break;
            case R.id.nav_sencor_and_camera:
                fragment = sensorAndCameraFragment;
                break;
        }

//        if (id == R.id.nav_github_auth) {
//            fragment = gitRepoFragment;
//        } else if (id == R.id.nav_maps) {
//            fragment = mapsFragment;
//        } else if (id == R.id.nav_calculator) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
