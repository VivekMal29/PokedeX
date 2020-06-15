package com.vivek.pokedex.models;

import java.util.ArrayList;

public class Pokedex {


    ArrayList<pokemon_entry> pokemon_entries;

    public ArrayList<pokemon_entry> getPokemon_entries() {
        return pokemon_entries;
    }

    public class pokemon_entry{
            pokemon_species pokemon_species;

        public pokemon_species getPokemonSpecies() {
            return pokemon_species;
        }
    }

    public class pokemon_species{
        String name;

        public String getName() {
            return name;
        }
    }
}
