package com.vivek.pokedex.ui.favourites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vivek.pokedex.Db_Handler.DbHandler;
import com.vivek.pokedex.FrontActivity;
import com.vivek.pokedex.R;
import com.vivek.pokedex.adapter.RecyclerViewAdapterFavourites;
import com.vivek.pokedex.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment {

    private FavouritesViewModel slideshowViewModel;


    RecyclerView recyclerView;
    RecyclerViewAdapterFavourites recyclerViewAdapterFavourites;
    ArrayList<Pokemon> pokemonArrayList;

    public FavouritesFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(FavouritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favourites, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        final DbHandler db = new DbHandler(getContext());

        recyclerView = root.findViewById(R.id.favouritesList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        pokemonArrayList = new ArrayList<>();

        List<Pokemon> FavPokemons = db.getFavPokemons();
        for(Pokemon pokemon : FavPokemons){
            pokemonArrayList.add(pokemon);
        }

        recyclerViewAdapterFavourites = new RecyclerViewAdapterFavourites(getContext(),pokemonArrayList);
        recyclerView.setAdapter(recyclerViewAdapterFavourites);




        return root;
    }



}