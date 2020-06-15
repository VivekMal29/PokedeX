package com.vivek.pokedex.Db_Handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.vivek.pokedex.PokeActivity;
import com.vivek.pokedex.models.Pokemon;
import com.vivek.pokedex.params.Params;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private Context context;

    public DbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create = "CREATE TABLE " + Params.TABLE_NAME + "(" +
                Params.KEY_ID + " INTEGER PRIMARY KEY," +
                Params.KEY_POKEID + "INTEGER," +
                Params.KEY_NAME + " TEXT," +
                Params.KEY_HEIGHT + " INTEGER," +
                Params.KEY_WEIGHT + " INTEGER," +
                Params.KEY_BASE_EXPERIENCE + " INTEGER," +
                Params.KEY_IMAGE + " BLOB" + ")";

        db.execSQL(create);

    }

    public void addToFavourites(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //       values.put(Params.KEY_POKEID,pokemon.getPrimaryId());
        values.put(Params.KEY_NAME, pokemon.getName());
        values.put(Params.KEY_HEIGHT, pokemon.getHeight());
        values.put(Params.KEY_WEIGHT, pokemon.getWeight());
        values.put(Params.KEY_BASE_EXPERIENCE, pokemon.getBase_experience());
        values.put(Params.KEY_IMAGE, pokemon.getBytes());

        db.insert(Params.TABLE_NAME, null, values);
        Log.d("dbvivek", "contact has been successfully added");
        Toast toast = Toast.makeText(context, "hello", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.TOP, 0, 60);
        toast.show();
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Pokemon> getFavPokemons() {
        List<Pokemon> pokemonList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()) {
            do {
                Pokemon pokemon = new Pokemon();


                pokemon.setId(cursor.getInt(0));
                pokemon.setPrimaryId(cursor.getInt(1));
                pokemon.setName(cursor.getString(2));
                pokemon.setHeight(cursor.getInt(3));
                pokemon.setWeight(cursor.getInt(4));
                pokemon.setBase_experience(cursor.getInt(5));
                pokemon.setBytes(cursor.getBlob(6));
                pokemonList.add(pokemon);


            } while (cursor.moveToNext());
        }
        return pokemonList;
    }

    public Pokemon getPokemon(int position) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);

        cursor.moveToPosition(position);

        Pokemon pokemon = new Pokemon();


        pokemon.setId(cursor.getInt(0));
        pokemon.setPrimaryId(cursor.getInt(1));
        pokemon.setName(cursor.getString(2));
        pokemon.setHeight(cursor.getInt(3));
        pokemon.setWeight(cursor.getInt(4));
        pokemon.setBase_experience(cursor.getInt(5));
        pokemon.setBytes(cursor.getBlob(6));


        return pokemon;
    }

    public void deletePokemon(int id) {
        Log.d("delete", "deleted succesfully");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Params.TABLE_NAME, Params.KEY_ID + "=?",
                new String[]{String.valueOf(id)});
    }

    public void deletePokemonByName(String name) {
        Log.d("delete", "deleted succesfully by name");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Params.TABLE_NAME, Params.KEY_NAME + "=?",
                new String[]{name});
    }


}
