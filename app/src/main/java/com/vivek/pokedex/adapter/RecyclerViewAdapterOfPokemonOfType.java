package com.vivek.pokedex.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vivek.pokedex.PokeActivity;
import com.vivek.pokedex.R;
import com.vivek.pokedex.TypeOfPokemon;
import com.vivek.pokedex.models.Pokemon;
import com.vivek.pokedex.models.Types_of_pokemon;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class RecyclerViewAdapterOfPokemonOfType extends RecyclerView.Adapter<RecyclerViewAdapterOfPokemonOfType.ViewHolder> {
    private Context context;
    private ArrayList<Types_of_pokemon.pokemon> pokemonArrayList;

    public RecyclerViewAdapterOfPokemonOfType(Context context, ArrayList<Types_of_pokemon.pokemon> pokemonArrayList) {
        this.context = context;
        this.pokemonArrayList = pokemonArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterOfPokemonOfType.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_poke_type,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterOfPokemonOfType.ViewHolder holder, int position) {
        Types_of_pokemon.pokemon pokemon = pokemonArrayList.get(position);
        String name = pokemon.getName();
        holder.NameOfPoke.setText(name);

    }

    @Override
    public int getItemCount() {
        return pokemonArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView NameOfPoke;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            NameOfPoke = itemView.findViewById(R.id.nameOfPokemon);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Types_of_pokemon.pokemon pokemon = pokemonArrayList.get(position);
            Intent intent = new Intent(context,PokeActivity.class);
            intent.putExtra("MSG",pokemon.getName());
            context.startActivity(intent);

        }
    }
}
