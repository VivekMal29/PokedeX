package com.vivek.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vivek.pokedex.Db_Handler.DbHandler;
import com.vivek.pokedex.models.Pokemon;
import com.vivek.pokedex.ui.favourites.FavouritesFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Fav_Poke_Activity extends AppCompatActivity {


    int position;

    TextView pokeName;
    TextView pokeHeight;
    TextView pokeWeight;
    TextView pokeBaseExperience;

    ImageView pokeImage;
    Button delelePoke;
    byte[] bytes;

    FavouritesFragment frg;
    Button share;
    Bitmap bitmap;
    Pokemon pokemon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav__poke_);

        final Intent intent = getIntent();
        Bundle b = intent.getExtras();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        final DbHandler db = new DbHandler(this);

        if (b != null) {
            position = (int) b.get("MSG");
        }
        pokeName = findViewById(R.id.pokeName);
        pokeImage = findViewById(R.id.pokeImage);
        pokeHeight = findViewById(R.id.height);
        pokeWeight = findViewById(R.id.weight);
        pokeBaseExperience = findViewById(R.id.base_experience);
        delelePoke = findViewById(R.id.deletePokemon);
        share = findViewById(R.id.shareImage);

        pokemon = db.getPokemon(position);

        Log.d("fav", String.valueOf(pokemon.getPrimaryId()));
        Log.d("fav", String.valueOf(pokemon.getId()));
        Log.d("fav", pokemon.getName());
        Log.d("fav", String.valueOf(pokemon.getWeight()));
        Log.d("fav", String.valueOf(pokemon.getHeight()));
        Log.d("fav", String.valueOf(pokemon.getBase_experience()));
        Log.d("fav", String.valueOf(pokemon.getBytes()));

        pokeName.setText(pokemon.getName());
        pokeHeight.setText(Integer.toString(pokemon.getHeight()));
        pokeWeight.setText(Integer.toString(pokemon.getWeight()));
        pokeBaseExperience.setText(Integer.toString(pokemon.getBase_experience()));

        bytes = pokemon.getBytes();

        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        pokeImage.setImageBitmap(bitmap);
        frg = new FavouritesFragment();
        frg = (FavouritesFragment) getSupportFragmentManager().findFragmentById(R.id.nav_favourites);
        final Intent intent1 = new Intent(this, FrontActivity.class);
        delelePoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deletePokemon(pokemon.getId());
                startActivity(intent1);
                Toast.makeText(Fav_Poke_Activity.this, "Removed Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/png");

                File f = new File(getExternalCacheDir(), "temporary_file.png");
                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fo);
                    Log.d("dbvivek", "HEY");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
                share.putExtra(Intent.EXTRA_TEXT, pokemon.getName().toUpperCase() + "\n" +
                        "WEIGHT  ->" + pokemon.getWeight() + "\n" +
                        "HEIGHT  ->" + pokemon.getHeight() + "\n" +
                        "BASE_EXPERIENCE  ->" + pokemon.getBase_experience());


//                share.putExtra(Intent.EXTRA_TEXT,"WEIGHT  ->"pokemon.getHeight());
//                share.putExtra(Intent.EXTRA_TEXT,"HEIGHT  ->"+pokemon.getWeight());
//                share.putExtra(Intent.EXTRA_TEXT,"BASE_EXPERIENCE  ->"+pokemon.getBase_experience());
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(share, "Share Image Via ..."));
//
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//                sendIntent.setType("text/plain");
//
//                Intent shareIntent = Intent.createChooser(sendIntent, null);
//                startActivity(shareIntent);
            }
        });

    }

}
