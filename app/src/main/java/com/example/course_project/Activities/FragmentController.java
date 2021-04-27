package com.example.course_project.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.course_project.DataManagement.User;
import com.example.course_project.Fragments.ConsumptionEntry;
import com.example.course_project.Fragments.ConsumptionLog;
import com.example.course_project.Fragments.BiometricsEntry;
import com.example.course_project.Fragments.BiometricsLog;
import com.example.course_project.R;
import com.google.android.material.navigation.NavigationView;

public class FragmentController extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            User user = (User) getIntent().getSerializableExtra("User");
            System.out.println("Changing to Consumption Entry");
            Fragment mealEntry = ConsumptionEntry.getInstance();
            sendUserDataToFragment(user, mealEntry);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, mealEntry).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        User user = (User) getIntent().getSerializableExtra("User");

        switch (item.getItemId()) {
            case (R.id.nav_consumptionEntry):
                System.out.println("Changing to Consumption Entry");
                Fragment mealEntry = ConsumptionEntry.getInstance();
                sendUserDataToFragment(user, mealEntry);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, mealEntry).commit();
                break;
            case (R.id.nav_biometrics_entry):
                System.out.println("Changing to Biometrics Entry");
                Fragment personInfoEntry = BiometricsEntry.getInstance();
                sendUserDataToFragment(user, personInfoEntry);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, personInfoEntry).commit();
                break;
            case (R.id.nav_consumption_log):
                System.out.println("Changing to Consumption Log");
                Fragment mealLog = ConsumptionLog.getInstance();
                sendUserDataToFragment(user, mealLog);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, mealLog).commit();
                break;
            case (R.id.nav_biometrics_log):
                System.out.println("Changing to Biometrics Log");
                Fragment personInfoLog = BiometricsLog.getInstance();
                sendUserDataToFragment(user, personInfoLog);
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, personInfoLog).commit();
                break;
            case (R.id.nav_logOff):
                Toast.makeText(this, "Change to Chat fragment", Toast.LENGTH_SHORT).show();
                changeActivity();
                break;
            case (R.id.nav_profile):
                Toast.makeText(this, "Change to Profile fragment", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void changeActivity() {
        //Intent intent = new Intent(this, StartAppFragmentController.class);
        //startActivity(intent);
        finish();
    }

    private void sendUserDataToFragment(User user, Fragment frag) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("User", user);
        frag.setArguments(bundle);
        System.out.println("#######################" + bundle.getSerializable("User") + "#######################");
    }
}