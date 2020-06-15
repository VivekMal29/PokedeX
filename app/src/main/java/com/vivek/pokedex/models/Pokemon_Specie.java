package com.vivek.pokedex.models;

public class Pokemon_Specie {

    Evolution evolution_chain;

    public Evolution getEvolution_chain() {
        return evolution_chain;
    }

    public class Evolution{
        String url;
        public String getUrl() {
            return url;
        }
    }
}
