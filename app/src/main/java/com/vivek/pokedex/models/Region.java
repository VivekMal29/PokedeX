package com.vivek.pokedex.models;

import java.util.ArrayList;

public class Region {

    private ArrayList<Region.result> results;

    public ArrayList<Region.result> getResults() {
        return results;
    }

    public class result{
        String name;

        public String getName() {
            return name;
        }
    }


}
