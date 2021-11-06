package com.notice_center.cymo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class AdminDashboard extends FragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admindashboard);
        loadFragments(new HomeFragment());
        bNav = findViewById(R.id.bottom_Nav);
        bNav.setOnItemSelectedListener(this);

    }
        public boolean loadFragments(Fragment fragment)
        {
            if(fragment != null)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment)
                        .addToBackStack(null)
                        .commit();
            }


            return true;
        }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.homeId:
                selectedFragment = new HomeFragment();
                break;
            case R.id.editId:
                selectedFragment = new EditFragment();
                break;
            case R.id.searchId:
                selectedFragment = new SearchFragment();
                break;
            
        }
        return loadFragments(selectedFragment);
    };

}
