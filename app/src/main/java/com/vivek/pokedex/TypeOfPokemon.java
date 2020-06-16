package com.vivek.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vivek.pokedex.adapter.RecyclerViewAdapter;
import com.vivek.pokedex.adapter.RecyclerViewAdapterOfPokemonOfType;
import com.vivek.pokedex.adapter.RecyclerViewAdapterType;
import com.vivek.pokedex.models.Pokemon;
import com.vivek.pokedex.models.Types_of_pokemon;
import com.vivek.pokedex.ui.Type.TypeFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TypeOfPokemon extends AppCompatActivity {

    String typeName = RecyclerViewAdapterType.NameOfType;

    Api api;
//    ListView listOfPokemon;
    RecyclerView recyclerView;
    RecyclerViewAdapterOfPokemonOfType recyclerViewAdapterOfPokemonOfType;
    ArrayList<Types_of_pokemon.pokemon> pokemonArrayList;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_pokemon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        listOfPokemon = findViewById(R.id.listOfPokemon);
//        final ArrayList<String> listOfPoke= new ArrayList<>();
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listOfPoke);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);

        recyclerView = findViewById(R.id.listOfPokemons);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        Bundle b= intent.getExtras();

        if(b != null){
            typeName = (String) b.get("MSG");
        }


        pokemonArrayList = new ArrayList<>();
        recyclerViewAdapterOfPokemonOfType = new RecyclerViewAdapterOfPokemonOfType(this,pokemonArrayList);

        Call<Types_of_pokemon> call = api.getPokemonList(typeName);

        call.enqueue(new Callback<Types_of_pokemon>() {
            @Override
            public void onResponse(Call<Types_of_pokemon> call, Response<Types_of_pokemon> response) {
                if(response.isSuccessful()){

                    Types_of_pokemon types_of_pokemon = response.body();
                    ArrayList<Types_of_pokemon.pokemons> pokemonsArrayList = types_of_pokemon.getPokemons();
                    for(Types_of_pokemon.pokemons pokemons : pokemonsArrayList){
                        Types_of_pokemon.pokemon pokemon = pokemons.getPokemon();
                        String name = pokemon.getName();
                        Log.d("DVlivek",name);
                       // listOfPoke.add(name);
                        pokemonArrayList.add(pokemon);
                    }
                    //listOfPokemon.setAdapter(arrayAdapter);
                    recyclerView.setAdapter(recyclerViewAdapterOfPokemonOfType);
                }

            }

            @Override
            public void onFailure(Call<Types_of_pokemon> call, Throwable t) {
                Log.d("DVlivek", String.valueOf(t));
            }
        });




    }
}
