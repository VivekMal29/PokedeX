package com.vivek.pokedex.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Types_of_pokemon {

    private ArrayList<pokemons> pokemon;

    public ArrayList<pokemons> getPokemons() {
        return pokemon;
    }

    public  class pokemons{
        pokemon pokemon;

        public Types_of_pokemon.pokemon getPokemon() {
            return pokemon;
        }
    }

    public class pokemon{
        String name;

        public String getName() {
            return name;
        }
    }

}
