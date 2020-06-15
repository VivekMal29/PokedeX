package com.vivek.pokedex.models;

import java.util.ArrayList;

public class LocationRequest {

   ArrayList<LocationRequest.result> results;

    public ArrayList<result> getResults() {
        return results;
    }

    public class result{
       String name;

       public String getName() {
           return name;
       }
   }
}
