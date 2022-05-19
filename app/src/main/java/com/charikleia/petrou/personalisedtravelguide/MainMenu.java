package com.charikleia.petrou.personalisedtravelguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        System.out.println(getIntent().getStringExtra("uid"));
        mAuth = FirebaseAuth.getInstance();
        System.out.println(mAuth.getCurrentUser().getDisplayName());

        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        drawer= findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState==null){
            //to show our first fragment when activity is loaded
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
            //select our fist item
            navigationView.setCheckedItem(R.id.nav_profile);
        }


    }

    @Override
    public void onBackPressed() {
        //a check because we want to close our navigation drawer not the whole activity when pressing back
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //created because we implemented NavigationView
        //chooses the correct activity for each navigation item
        switch (item.getItemId()){
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
                break;
            case R.id.nav_add:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new NewTripFragment()).commit();
                break;
            case R.id.nav_map:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyTripsFragment()).commit();
                break;
//            case R.id.nav_map:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyTripsFragment()).commit();
//                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        //if false no item woyld be selected
        return true;
    }
}