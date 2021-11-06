package com.notice_center.cymo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class sdashboardone extends FragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
        BottomNavigationView sbNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdashboardone);
        loadFragments(new HomeFragmentOne());
        sbNav = findViewById(R.id.bottom_Snav);
        sbNav.setOnItemSelectedListener(this);

    }
    public boolean loadFragments(Fragment fragment)
    {
        if(fragment != null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.sfragment_container,fragment)
                    .addToBackStack(null)
                    .commit();
        }


        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.s_homeId:
                selectedFragment = new HomeFragmentOne();
                break;
            case R.id.todoId:
                selectedFragment = new ToDoFragment();
                break;
            case R.id.s_searchId:
                selectedFragment = new SearchFragmentOne();
                break;

        }
        return loadFragments(selectedFragment);
    };

}