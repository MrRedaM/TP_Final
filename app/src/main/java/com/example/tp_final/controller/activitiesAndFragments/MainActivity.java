package com.example.tp_final.controller.activitiesAndFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.tp_final.R;
import com.example.tp_final.controller.activitiesAndFragments.MenuFragment;
import com.example.tp_final.controller.activitiesAndFragments.CheckoutFragment;
import com.example.tp_final.controller.activitiesAndFragments.CommandsFragment;
import com.example.tp_final.model.Plat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int RESULT_ADD_PLAT = 1;
    public static final int RESULT_NEW_COMMAND = 2;

    private FloatingActionButton fab_main, fab_plat, fab_commande;
    private Animation fab_open, fab_close, fab_rotate_clock, fab_rotate_anticlock, text_open, text_close;
    private TextView plat_text, commande_text;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab_main = findViewById(R.id.fab_main);
        fab_plat = findViewById(R.id.fab_plat);
        fab_commande = findViewById(R.id.fab_commande);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_rotate_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_rotate_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);
        commande_text = findViewById(R.id.textCommande);
        plat_text = findViewById(R.id.textPlat);
        text_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.text_open);
        text_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.text_close);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {

                    commande_text.startAnimation(text_close);
                    plat_text.startAnimation(text_close);
                    commande_text.setVisibility(View.INVISIBLE);
                    plat_text.setVisibility(View.INVISIBLE);
                    fab_commande.startAnimation(fab_close);
                    fab_plat.startAnimation(fab_close);
                    fab_commande.setVisibility(View.INVISIBLE);
                    fab_plat.setVisibility(View.INVISIBLE);
                    fab_main.startAnimation(fab_rotate_anticlock);
                    fab_commande.setClickable(false);
                    fab_plat.setClickable(false);
                    isOpen = false;
                } else {
                    commande_text.startAnimation(text_open);
                    plat_text.startAnimation(text_open);
                    commande_text.setVisibility(View.VISIBLE);
                    plat_text.setVisibility(View.VISIBLE);
                    fab_commande.startAnimation(fab_open);
                    fab_plat.startAnimation(fab_open);
                    fab_commande.setVisibility(View.VISIBLE);
                    fab_plat.setVisibility(View.VISIBLE);
                    fab_main.startAnimation(fab_rotate_clock);
                    fab_commande.setClickable(true);
                    fab_plat.setClickable(true);
                    isOpen = true;
                }
            }
        });
        View.OnClickListener clickAddPlat = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPlatActivity.class);
                startActivityForResult(intent, RESULT_ADD_PLAT);
            }
        };
        View.OnClickListener clickNewCommand = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewCommandActivity.class);
                startActivityForResult(intent, RESULT_NEW_COMMAND);
            }
        };
        fab_plat.setOnClickListener(clickAddPlat);
        plat_text.setOnClickListener(clickAddPlat);
        fab_commande.setOnClickListener(clickNewCommand);
        commande_text.setOnClickListener(clickNewCommand);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setTitle("Menu");
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new MenuFragment()).commitNow();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_ADD_PLAT && resultCode == Activity.RESULT_OK) {
            Plat newPlat = (Plat) data.getSerializableExtra("EXTRA_PLAT");
            if (newPlat != null) {
                //sauvgarder
                Toast.makeText(MainActivity.this, "Plat ajouté avec succe!", Toast.LENGTH_SHORT).show();
            }
        }
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new MenuFragment()).commit();
            setTitle("Menu");
        } else if (id == R.id.nav_commands) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new CommandsFragment()).commit();
            setTitle("Commandes");
        } else if (id == R.id.nav_checkout) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new CheckoutFragment()).commit();
            setTitle("Caisse");
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
