package com.example.dima.workipmyr.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

import com.example.dima.workipmyr.DB;
import com.example.dima.workipmyr.R;
import com.example.dima.workipmyr.WorkedActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btGiweWorkc;
    Button btHistori;
    Button btDrivers;
    Button btCustomer;

    private static final int CM_DELETE_ID = 1;
    DB db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btGiweWorkc = (Button) findViewById(R.id.bt_give_work);
        btHistori = (Button) findViewById(R.id.bt_history);
        btDrivers = (Button) findViewById(R.id.bt_drivers);
        btCustomer = (Button) findViewById(R.id.bt_customers);

        btGiweWorkc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, WorkedActivity.class);
                startActivity(intent);
            }
        });

        btHistori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        btDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, ActivityDrivers.class);
                startActivity(intent);
            }
        });

        btCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, ActivityCustomer.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Intent intent;
        int id = item.getItemId();
        if (id == R.id.getWork) {
             intent = new Intent(this, WorkedActivity.class);
            startActivity(intent);

        } else if (id == R.id.histori) {
            intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);

        } else if (id == R.id.drivers) {
            intent = new Intent(this, ActivityDrivers.class);
            startActivity(intent);

        } else if (id == R.id.worked) {
            intent = new Intent(this, ActivityCustomer.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
