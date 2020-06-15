package com.vivek.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.vivek.pokedex.adapter.RecyclerViewAdapterPokeOFRegion;
import com.vivek.pokedex.models.Pokedex;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegionActivity extends AppCompatActivity {

    int regionPOsition=0;

    int pokeIndex;

    Api api;

    RecyclerView recyclerView;
    RecyclerViewAdapterPokeOFRegion recyclerViewAdapterPokeOFRegion;
    ArrayList<Pokedex.pokemon_species> pokemon_speciesArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);

        Intent intent = getIntent();
        Bundle b= intent.getExtras();

        if(b != null){
            regionPOsition=b.getInt("MSG");
        }

        if(regionPOsition==0){
            pokeIndex=2;
        }
        else if(regionPOsition==1){
            pokeIndex=3;
        }
        else if(regionPOsition==2){
            pokeIndex=4;
        }
        else if(regionPOsition==3){
            pokeIndex=5;
        }
        else if(regionPOsition==4){
            pokeIndex=8;
        }
        else if(regionPOsition==5){
            pokeIndex=12;
        }
        else if(regionPOsition==6){
            pokeIndex=16;
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/\n")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);

        Call<Pokedex> call = api.getPOkedex(pokeIndex);


        pokemon_speciesArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.listOfRegionPoke);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapterPokeOFRegion = new RecyclerViewAdapterPokeOFRegion(this,pokemon_speciesArrayList);

        call.enqueue(new Callback<Pokedex>() {
            @Override
            public void onResponse(Call<Pokedex> call, Response<Pokedex> response) {
                Pokedex pokedex = response.body();
                ArrayList<Pokedex.pokemon_entry> pokemon_entries = pokedex.getPokemon_entries();
                for(Pokedex.pokemon_entry pokemon_entry : pokemon_entries){
                    Pokedex.pokemon_species pokemon_species = pokemon_entry.getPokemonSpecies();
                    String name = pokemon_species.getName();
                    pokemon_speciesArrayList.add(pokemon_species);
                }

                recyclerView.setAdapter(recyclerViewAdapterPokeOFRegion);

            }

            @Override
            public void onFailure(Call<Pokedex> call, Throwable t) {

            }
        });

    }
}
