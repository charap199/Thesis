package com.charikleia.petrou.personalisedtravelguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import android.database.sqlite.SQLiteDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private DrawerLayout drawer;
    private TextView textView6;
    //bd variables
    Connection connect;
    List<String> ConnectionResult=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        System.out.println(getIntent().getStringExtra("uid"));
        mAuth = FirebaseAuth.getInstance();
        //System.out.println("curr user is:" + Objects.requireNonNull(mAuth.getCurrentUser()).getDisplayName());
        textView6= findViewById(R.id.textView6);
        textView6.setText(getIntent().getStringExtra("uid"));
        //get instance of realtime db Firebase
//        DatabeaseReference grPois = FirebaseAuth.getInstance().getReference;

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

//        server connection
        try {
            System.out.println("c11");
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null){
                System.out.println("c12");
                String query = "Select * from pois.greecepois";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);

                while (rs.next()){
                    ConnectionResult.add(rs.toString());
                    System.out.println(rs.getString(1));
                }
            }else{
                System.out.println("c13");
                ConnectionResult.add("Check Connection!");
            }

        }catch (Exception e){
            System.out.println("c13");
            System.out.println("Exeption:" + e);
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
            case R.id.nav_logout:
                System.out.println("logout");
                if (mAuth != null){
                    //do if user logged in
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(MainMenu.this, MainActivity.class);
                    intent.putExtra("","");
                    startActivity(intent);
                }else {
                    //do if no user logged in
                    Toast.makeText(this, "No user logged in!", Toast.LENGTH_SHORT).show();
                }
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new NewTripFragment()).commit();
//            case R.id.nav_map:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MyTripsFragment()).commit();
//                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        //if false no item woyld be selected
        return true;
    }
}