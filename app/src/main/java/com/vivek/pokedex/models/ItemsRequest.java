package com.vivek.pokedex.models;
import java.util.ArrayList;

public class ItemsRequest {

    private ArrayList<item> results;

    public ArrayList<item> getResults() {
        return results;
    }

    public class item{
        String name ;

        public String getName() {
            return name;
        }
    }
}
