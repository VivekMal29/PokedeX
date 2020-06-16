package com.vivek.pokedex.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class Pokemon {

    public Pokemon() {
    }

    private String name;
    private String url;
    private int id;
    private int height;
    private  int weight;
    private  int base_experience;

    private byte[] bytes;

    public Pokemon(int id) {
        this.id = id;
    }
    private boolean isFav;

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    private int primaryId;

    public int getPrimaryId() {

        return primaryId;
    }

    public void setPrimaryId(int primaryId) {
        this.primaryId = primaryId;
    }

    public ArrayList<Types> types;
    public ArrayList<Types> getTypes() {
        return types;
    }
    public int getHeight() {
        return height;
    }
    public int getWeight() {
        return weight;
    }
    public int getBase_experience() {
        return base_experience;
    }
    @SerializedName("location_area_encounters")
    private String locationURL;
    public String getLocationURL() {
        return locationURL;
    }
    private ArrayList<Held_Items> held_items;
    public ArrayList<Held_Items> getHeld_items() {
        return held_items;
    }
    public String getUrl() {
        return url;
    }
    public String getName() {
        return name;
    }
    public int getId(){

        return id;
    }
    public class Held_Items{
            Items item;
        public Items getItem() {
            return item;
        }
    }
    public class Items{
        String name;
        public String getName() {
            return name;
        }
    }
    public class Types{
       Type type;

        public Type getType() {
            return type;
        }
    }
    public class Type{
        String name;

        public String getName() {
            return name;
        }
    }
    ArrayList<Pokemon.statObject> stats ;

    public ArrayList<statObject> getStats() {
        return stats;
    }

    public class statObject{
        stat stat;
        int base_stat;

        public int getBase_stat() {
            return base_stat;
        }

        public Pokemon.stat getStat() {
            return stat;
        }
    }
    public class stat{
        String name;

        public String getName() {
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setBase_experience(int base_experience) {
        this.base_experience = base_experience;
    }
}
