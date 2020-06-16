package com.vivek.pokedex.ui.pokemons;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vivek.pokedex.Api;
import com.vivek.pokedex.Db_Handler.DbHandler;
import com.vivek.pokedex.FrontActivity;
import com.vivek.pokedex.PokeActivity;
import com.vivek.pokedex.R;
import com.vivek.pokedex.adapter.RecyclerViewAdapter;
import com.vivek.pokedex.models.Pokemon;
import com.vivek.pokedex.models.PokemonRequest;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pokemons extends Fragment {

    private PokemonViewModel pokemonViewModel;

    public static int pokeId;
    //    ListView pokemonList;
    ArrayList<String> arrayList = new ArrayList<>();
    Api api;
    RecyclerView recyclerView;
    public static RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Pokemon> pokemonArrayList;
    public static byte[] bytes;
    int offset;
    Retrofit retrofit;
    Boolean isScrolling = false;
    ProgressBar progressBar;
    ConstraintLayout constraintLayout;
    ImageView favourite;
    ImageView viewImage;

    Boolean isFav;
    List<Pokemon> FavpokemonList;
    LottieAnimationView  lottieAnimationView;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pokemonViewModel =
                ViewModelProviders.of(this).get(PokemonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pokemon, container, false);
        final TextView textView = root.findViewById(R.id.text_pokemon);
        pokemonViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        lottieAnimationView = root.findViewById(R.id.av_loader_front);
        lottieAnimationView.setAnimation("loader.json" );
        retrofit = new Retrofit.Builder()
                .baseUrl(" https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        favourite = root.findViewById(R.id.star);
        final DbHandler db = new DbHandler(getContext());

        FavpokemonList = db.getFavPokemons();


        FrontActivity.frangment = 0;
        constraintLayout = root.findViewById(R.id.pokeLayout);
        viewImage = root.findViewById(R.id.viewImage);

        //recyclerView Initialisation
        recyclerView = root.findViewById(R.id.pokemonList);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if (dy > 0) {
//                    int visibleItemCount = layoutManager.getChildCount();
//                    int totalItemCount = layoutManager.getItemCount();
//                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
//
//                    if (isScrolling) {
//                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
//
//
//                            isScrolling = false;
//                            offset += 15;
//                            obtainPokemons(offset);
//                        }
//                    }
//                }
//                else{
//                    int visibleItemCount = layoutManager.getChildCount();
//                    int totalItemCount = layoutManager.getItemCount();
//                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
//
//                    if (isScrolling) {
//                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
//
//
//                            isScrolling = false;
//                            offset -= 15;
//                            obtainPokemons(offset);
//                        }
//                    }
//                }
//            }
//        });

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, arrayList);
//        pokemonList = root.findViewById(R.id.pokemonList);
        pokemonArrayList = new ArrayList<>();


//        final Intent intent = new Intent(getActivity(), PokeActivity.class);
//        pokemonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                pokeId=position+1;
//                startActivity(intent);
//
//
//
//            }
//        });
        api = retrofit.create(Api.class);
        Call<PokemonRequest> call = api.obtenerListPokemon(807, 0);
        call.enqueue(new Callback<PokemonRequest>() {
            @Override
            public void onResponse(Call<PokemonRequest> call, Response<PokemonRequest> response) {
                if (!response.isSuccessful()) {
                    Log.d("Vivek", Integer.toString(response.code()));

                }

                isScrolling = true;
                PokemonRequest pokemonRequest = response.body();
                ArrayList<Pokemon> listPokemon = pokemonRequest.getResults();
                arrayList.clear();
                for (Pokemon pokemon : listPokemon) {
                    for(Pokemon favPokemon : FavpokemonList){
                        if(favPokemon.getName().equals(pokemon.getName())){
                            pokemon.setFav(true);
                            Log.d("bugg", "this is fav"+ pokemon.getName());
                        }
                        else {
                            pokemon.setFav(false);
                        }
                    }

                    String name = pokemon.getName();
                    String url = pokemon.getUrl();
                    String[] urlPartes = url.split("/");
                    int primaryId = Integer.parseInt(urlPartes[urlPartes.length - 1]);
                    pokemon.setPrimaryId(primaryId);
                    int id = pokemon.getPrimaryId();
                    pokeId = id;

                    Log.d("Vivek", Integer.toString(id));
                    Log.d("Vivek", name);
                    arrayList.add("(#" + id + ") " + name);
//                    pokemonList.setAdapter(arrayAdapter);




                    pokemonArrayList.add(pokemon);

                }
                //Use your recyclerView here
                recyclerViewAdapter = new RecyclerViewAdapter(getContext(), pokemonArrayList);
                recyclerView.setAdapter(recyclerViewAdapter);

                lottieAnimationView.pauseAnimation();
                lottieAnimationView.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onFailure(Call<PokemonRequest> call, Throwable t) {
                Log.d("Vivek", String.valueOf(t));
            }


        });

//        Glide.with(this)
//                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokeId + ".png")
//                .centerCrop()
//                .override(144,118)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(viewImage);


        return root;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}