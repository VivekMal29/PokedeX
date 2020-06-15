package com.vivek.pokedex;



import com.vivek.pokedex.models.Evolution_Chain;
import com.vivek.pokedex.models.ItemsRequest;
import com.vivek.pokedex.models.LocationRequest;
import com.vivek.pokedex.models.Pokedex;
import com.vivek.pokedex.models.Pokemon;
import com.vivek.pokedex.models.PokemonRequest;
import com.vivek.pokedex.models.Pokemon_Specie;
import com.vivek.pokedex.models.Region;
import com.vivek.pokedex.models.Types;
import com.vivek.pokedex.models.Types_of_pokemon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("type")
    Call<Types> getResult();

    @GET("region")
    Call<Region> getRegions();

    @GET("location")
    Call<LocationRequest> getLocations(@Query("limit") int limit, @Query("offset") int offset );

    @GET("item")
    Call<ItemsRequest> getItems(@Query("limit") int limit, @Query("offset") int offset);

    @GET ("pokemon")
    Call<PokemonRequest> obtenerListPokemon(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{name}/")
    Call<Pokemon> getPokemonData(@Path("name") String Name);

    @GET("pokemon/{id}/encounters")
    Call<List<LocationRequest>> getLocation(@Path("id") int postId);

    @GET("type/{typeName}")
    Call<Types_of_pokemon> getPokemonList(@Path("typeName") String typeName);

    @GET("pokemon-species/{name}/")
    Call<Pokemon_Specie> getPokemonSpecie(@Path("name") String postName);

    @GET("evolution-chain/{id}")
    Call<Evolution_Chain> getEvolutionChain(@Path("id") int postId);

    @GET("pokedex/{id}/")
    Call<Pokedex> getPOkedex(@Path("id") int pokeIndex);




}
