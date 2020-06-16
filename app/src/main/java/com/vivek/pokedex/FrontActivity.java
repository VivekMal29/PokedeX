package com.vivek.pokedex;

import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.vivek.pokedex.adapter.RecyclerViewAdapter;
import com.vivek.pokedex.models.LocationRequest;
import com.vivek.pokedex.ui.Type.TypeFragment;
import com.vivek.pokedex.ui.favourites.FavouritesFragment;
import com.vivek.pokedex.ui.items.ItemFragment;
import com.vivek.pokedex.ui.location.LocationFragment;
import com.vivek.pokedex.ui.pokemons.Pokemons;
import com.vivek.pokedex.ui.region.RegionFragment;
import com.vivek.pokedex.ui.region.RegionViewModel;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ListView;

import java.util.ArrayList;

public class FrontActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


    public static int frangment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_item, R.id.nav_pokemon, R.id.nav_favourites,
                R.id.nav_region, R.id.nav_location, R.id.nav_type)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.front, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (frangment == 0) {
                    Pokemons.recyclerViewAdapter.getFilter().filter(newText);
                } else if (frangment == 2) {
                    ItemFragment.recyclerViewAdapterItem.getFilter().filter(newText);
                } else if (frangment == 1) {
                    LocationFragment.recylerViewAdapterLocation.getFilter().filter(newText);
                } else if (frangment == 3) {
                    TypeFragment.recyclerViewAdapterType.getFilter().filter(newText);
                } else if (frangment == 4) {
                    RegionFragment.recyclerViewAdapterRegion.getFilter().filter(newText);
                }

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
