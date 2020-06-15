package com.vivek.pokedex.models;

import java.util.ArrayList;

public class Evolution_Chain {

    private Chain chain;

    public Chain getChain() {
        return chain;
    }

    public class Chain{
        Species species;
        ArrayList<Evolves_to> evolves_to;

        public ArrayList<Evolves_to> getEvolves_to() {
            return evolves_to;
        }

        public Species getSpecies() {
            return species;
        }
    }
    public class Species{
        String name;

        public String getName() {
            return name;
        }
    }
    public class Evolves_to {
        Species species;
        ArrayList<Evolves_to> evolves_to;


        public ArrayList<Evolves_to> getEvolves_to() {
            return evolves_to;
        }

        public Species getSpecies() {
            return species;
        }
    }
}
