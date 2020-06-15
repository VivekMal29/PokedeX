package com.vivek.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vivek.pokedex.Db_Handler.DbHandler;
import com.vivek.pokedex.adapter.RecyclerViewAdapter;
import com.vivek.pokedex.adapter.RecyclerViewAdapterEvolution;
import com.vivek.pokedex.adapter.RecyclerViewAdapterPokeType;
import com.vivek.pokedex.models.Evolution_Chain;
import com.vivek.pokedex.models.Pokemon;
import com.vivek.pokedex.models.Pokemon_Specie;
import com.vivek.pokedex.ui.pokemons.Pokemons;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokeActivity extends AppCompatActivity {

    int pokeId = Pokemons.pokeId;
    Api api;
    ImageView pokeImage;
    ArrayList<String> listOfLocationUrl;
    TextView pokeName;
    TextView height;
    TextView weight;
    TextView base_experience;
    ListView itemList;
    ListView locationList;
    LayoutParams list;
    LayoutParams list1;
    float listHeight = 0;
    float listHeight1 = 0;
    int evulution_chain_id = 1;
    public static Pokemon pokemon;

    public static int add = 0;

    ProgressBar progressBar;

    String nameOfPoke;

    byte[] bytes;
    int flag = 0;

    Boolean isPoke = false;
    Boolean isPicasso = false;
    Boolean isEvoluiton = false;
    ImageView pokeStar;

    ConstraintLayout constraintLayout;
    RecyclerView recyclerView;
    RecyclerViewAdapterEvolution recyclerViewAdapterEvolution;
    ArrayList<Evolution_Chain.Species> evolution_chains;

    RecyclerView recyclerViewType;
    RecyclerViewAdapterPokeType recyclerViewAdapterPokeType;
    ArrayList<Pokemon.Type> typeArrayList;

    final DbHandler db = new DbHandler(PokeActivity.this);
    List<Pokemon> favPokemons;
    private GestureDetectorCompat gestureDetectorCompat = null;
    TextView swipe;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke);

        GestureDectecter gestureDectecter = new GestureDectecter();
        gestureDectecter.setActivity(this);
        gestureDetectorCompat = new GestureDetectorCompat(this, gestureDectecter);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        isEvoluiton = false;
        isPoke = false;

        if (b != null) {
            nameOfPoke = (String) b.get("MSG");
        }


        pokeImage = findViewById(R.id.pokeImage);
        pokeName = findViewById(R.id.pokeName);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        itemList = findViewById(R.id.itemList);
        locationList = findViewById(R.id.locationList);
        constraintLayout = findViewById(R.id.pokeLayout);
        swipe = findViewById(R.id.swipe);

        recyclerView = findViewById(R.id.evolutionList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        evolution_chains = new ArrayList<>();

        recyclerViewType = findViewById(R.id.pokeTypeList);
        recyclerViewType.setHasFixedSize(true);
        recyclerViewType.setLayoutManager(new LinearLayoutManager(this));

        typeArrayList = new ArrayList<>();

//        list = itemList.getLayoutParams();
//        list1 = locationList.getLayoutParams();

        progressBar = findViewById(R.id.progressBar);
        pokeStar = findViewById(R.id.pokeStar);
//        listHeight =  (45 * ((float) this.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
//        listHeight1 =  (45 * ((float) this.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));


        base_experience = findViewById(R.id.base_experience);
        constraintLayout.setAlpha((float) 0.1);

//        final ArrayList<String> listOfItem = new ArrayList<>();
//        final ArrayList<String> listOfLocation = new ArrayList<>();
//
//
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listOfItem);
//        final ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listOfLocation);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);

        Call<Pokemon> call = api.getPokemonData(nameOfPoke);


        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful()) {

                    pokemon = response.body();


//                    Log.d("DVivek",pokemon.getName());
                    ArrayList<Pokemon.Held_Items> held_items = pokemon.getHeld_items();
//                    Log.d("DVivek",Integer.toString(held_items.size()));
                    favPokemons = db.getFavPokemons();
                    for (Pokemon favpokemon : favPokemons) {
//                        Log.d("testing",pokemon.getName()   +   favpokemon.getName());
                        if (favpokemon.getName().equals(pokemon.getName())) {
                            pokeStar.setImageResource(R.drawable.ic_star_black_24dp);
                            swipe.setText("swipe down to remove");
                        }
                    }

                    Log.d("vytes", String.valueOf(pokemon.getBytes()));

//                                    List<Pokemon> myPokemons = db.getFavPokemons();
//                                    Pokemon favPokemon = myPokemons.get(0);
//
//                                    Log.d("fav", String.valueOf(favPokemon.getPrimaryId()));
//                                    Log.d("fav", String.valueOf(favPokemon.getId()));
//                                    Log.d("fav",favPokemon.getName());
//                                    Log.d("fav", String.valueOf(favPokemon.getWeight()));
//                                    Log.d("fav", String.valueOf(favPokemon.getHeight()));
//                                    Log.d("fav", String.valueOf(favPokemon.getBase_experience()));


                    ArrayList<Pokemon.Types> types = pokemon.getTypes();
                    for (Pokemon.Types type : types) {
                        Pokemon.Type typeOfPoke = type.getType();
                        String name = typeOfPoke.getName();
//                        Log.d("DVivek",name);
                        typeArrayList.add(typeOfPoke);
                    }
                    recyclerViewAdapterPokeType = new RecyclerViewAdapterPokeType(PokeActivity.this, typeArrayList);
                    recyclerViewType.setAdapter(recyclerViewAdapterPokeType);

                    ArrayList<Pokemon.statObject> statObjects = pokemon.getStats();
                    for (Pokemon.statObject statObject : statObjects) {
                        Pokemon.stat stat = statObject.getStat();
                        String name = stat.getName();
                        Log.d("DVivek", name);
                        int baseState = statObject.getBase_stat();
                        Log.d("DVivek", String.valueOf(baseState));
                    }

                    height.setText(Integer.toString(pokemon.getHeight()));
                    weight.setText(Integer.toString(pokemon.getWeight()));
                    base_experience.setText(Integer.toString(pokemon.getBase_experience()));
//                    list.height = (int) (listHeight+(itemList.getDividerHeight() * (arrayAdapter.getCount() )))*held_items.size();

                    pokeName.setText(pokemon.getName());
//                    if(held_items.size()==0){
////                        listOfItem.add("N/A");
////                        list.height =100;
//                    }
//                    itemList.setLayoutParams(list);
//                    itemList.setAdapter(arrayAdapter);

                }
                Picasso.get()
                        .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokeId + ".png")
                        .centerCrop()
                        .resize(144, 118)
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                                /* Save the bitmap or do something with it here */
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                bytes = stream.toByteArray();
                                //Set it in the ImageView
                                pokemon.setBytes(bytes);
                                Log.d("vytes", String.valueOf(bytes));
                                Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                                pokeImage.setImageBitmap(bitmap1);
                                isPoke = true;

                                if (isPoke && isEvoluiton) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    constraintLayout.setAlpha(1);
                                }

                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }

                        });


            }


            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.d("DVivek", String.valueOf(t));
            }
        });

        Call<Pokemon_Specie> pokemon_specieCall = api.getPokemonSpecie(nameOfPoke);

        pokemon_specieCall.enqueue(new Callback<Pokemon_Specie>() {
            @Override
            public void onResponse(Call<Pokemon_Specie> call, Response<Pokemon_Specie> response) {
                if (response.isSuccessful()) {
                    Pokemon_Specie pokemonSpecie = response.body();
                    Pokemon_Specie.Evolution evolution_chain = pokemonSpecie.getEvolution_chain();
                    String url;
                    url = evolution_chain.getUrl();

                    String[] urlPartes = url.split("/");
                    evulution_chain_id = Integer.parseInt(urlPartes[urlPartes.length - 1]);
                    Log.d("dVivek", String.valueOf(evulution_chain_id));

                    Call<Evolution_Chain> chainCall = api.getEvolutionChain(evulution_chain_id);
                    chainCall.enqueue(new Callback<Evolution_Chain>() {
                        @Override
                        public void onResponse(Call<Evolution_Chain> call, Response<Evolution_Chain> response) {
                            if (response.isSuccessful()) {
                                Evolution_Chain evolution_chain = response.body();
                                Evolution_Chain.Chain chain = evolution_chain.getChain();
                                Evolution_Chain.Species species = chain.getSpecies();
                                String name1 = species.getName();
//                                Log.d("DVivek",name1);
                                evolution_chains.add(species);

                                ArrayList<Evolution_Chain.Evolves_to> evolves_tos = chain.getEvolves_to();
                                while (evolves_tos.size() != 0) {
                                    Evolution_Chain.Evolves_to evolves_to = evolves_tos.get(0);
                                    Evolution_Chain.Species species1 = evolves_to.getSpecies();
                                    String name2 = species1.getName();
//                                    Log.d("DVivek",name2);
                                    evolution_chains.add(species1);
                                    evolves_tos = evolves_to.getEvolves_to();
                                }


                            } else {
                                Log.d("DVivek", "failed !!!!");
                            }
                            Log.d("evolution number", String.valueOf(evolution_chains.size()));
                            recyclerViewAdapterEvolution = new RecyclerViewAdapterEvolution(PokeActivity.this, evolution_chains);
                            recyclerView.setAdapter(recyclerViewAdapterEvolution);

                            isEvoluiton = true;

                            if (isPoke && isEvoluiton) {
                                progressBar.setVisibility(View.INVISIBLE);
                                constraintLayout.setAlpha(1);
                            }
                        }

                        @Override
                        public void onFailure(Call<Evolution_Chain> call, Throwable t) {

                        }
                    });
                } else {
                    Log.d("dVivek", "incomplete");
                }

            }

            @Override
            public void onFailure(Call<Pokemon_Specie> call, Throwable t) {
                Log.d("dVivek", String.valueOf(t));
            }
        });


//        Call<List<LocationRequest>> call2 = api.getLocation(pokeId);
//
//        call2.enqueue(new Callback<List<LocationRequest>>() {
//            @Override
//            public void onResponse(Call<List<LocationRequest>> call, Response<List<LocationRequest>> response) {
//                    if(response.isSuccessful()){
//                        List<LocationRequest> locationLists = response.body();
//                        for(LocationRequest locationArea: locationLists){
//                            String name= locationArea.getLocation_area().getName();
////                            listOfLocationUrl.add(locationArea.getLocation_area().getLocation_area_url());
//                            listOfLocation.add(name);
//                            locationList.measure(0,0);
//                            listHeight1 += locationList.getMeasuredHeight();
//
//
//                        }
//                        if(locationLists.size()==0){
//                            listOfLocation.add("N/A");
//                            list1.height = 100;
//                        }
//                        list1.height = (int) (listHeight1 )*locationLists.size()+ (locationList.getDividerHeight() * (locationLists.size()-1 ));
//                        locationList.setAdapter(arrayAdapter1);
//                        locationList.setLayoutParams(list1);
//
//                    }
//            }
//
//            @Override
//            public void onFailure(Call<List<LocationRequest>> call, Throwable t) {
//                Log.d("DVivek", String.valueOf(t));
//            }
//        });


//        Glide.with(this)
//                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokeId + ".png")
//                .centerCrop()
//                .override(144,118)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(pokeImage);


    }

    class GestureDectecter extends GestureDetector.SimpleOnGestureListener {

        private int MIN_SWIPE_DISTANCE_X = 100;
        private int MIN_SWIPE_DISTANCE_Y = 100;
        private int MAX_SWIPE_DISTANCE_X = 1000;
        private int MAX_SWIPE_DISTANCE_Y = 1000;

        private PokeActivity activity = null;

        int add = PokeActivity.add;

        public PokeActivity getActivity() {
            return activity;
        }

        public void setActivity(PokeActivity activity) {
            this.activity = activity;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            float deltaX = e1.getX() - e2.getX();
            float deltaY = e1.getY() - e2.getY();

            float deltaXAbs = Math.abs(e1.getX() - e2.getX());
            float deltaYAbs = Math.abs(e1.getY() - e2.getY());


            if (deltaYAbs >= MIN_SWIPE_DISTANCE_Y && deltaYAbs <= MAX_SWIPE_DISTANCE_Y) {
                //swipe up
                List<Pokemon> FavpokemonList = db.getFavPokemons();
                for (Pokemon favpokemon : FavpokemonList) {
                    if (pokemon.getName().equals(favpokemon.getName())) {
                        flag = 1;
                    }
                }
                if (deltaY > 0) {
                    if (flag == 1) {
                        Toast.makeText(activity, "Already in Favourites", Toast.LENGTH_SHORT).show();
                    } else {
                        db.addToFavourites(pokemon);
                        pokeStar.setImageResource(R.drawable.ic_star_black_24dp);
                        swipe.setText("swipe down to remove");
                        add = 1;
                        Toast.makeText(activity, "Added to Favourites", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    if (flag == 1) {
                        db.deletePokemonByName(pokemon.getName());
                        pokeStar.setImageResource(R.drawable.ic_star_border_black_24dp);
                        swipe.setText("swipe up to add to favourites");
                        Toast.makeText(activity, "Removed Successfully", Toast.LENGTH_SHORT).show();
                        flag = 0;
                    } else {

                        Toast.makeText(activity, "Not on Favourites", Toast.LENGTH_SHORT).show();
                    }//swipe down

                }
            }


            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, FrontActivity.class);
        startActivity(intent);
    }
}
