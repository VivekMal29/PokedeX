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
import com.vivek.pokedex.models.Pokedex;
import com.vivek.pokedex.models.Types_of_pokemon;

import java.util.ArrayList;

public class RecyclerViewAdapterPokeOFRegion extends RecyclerView.Adapter<RecyclerViewAdapterPokeOFRegion.ViewHolder> {

    private Context context;
    private ArrayList<Pokedex.pokemon_species> pokemon_speciesArrayList;

    public RecyclerViewAdapterPokeOFRegion(Context context, ArrayList<Pokedex.pokemon_species> pokemon_speciesArrayList) {
        this.context = context;
        this.pokemon_speciesArrayList = pokemon_speciesArrayList;
    }


    @NonNull
    @Override
    public RecyclerViewAdapterPokeOFRegion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_poke_region,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterPokeOFRegion.ViewHolder holder, int position) {
        Pokedex.pokemon_species  pokemon_species = pokemon_speciesArrayList.get(position);
        holder.pokeName.setText(pokemon_species.getName());
    }

    @Override
    public int getItemCount() {
        return pokemon_speciesArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView pokeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            pokeName = itemView.findViewById(R.id.nameOfPokemonRegion);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Pokedex.pokemon_species pokemon_species = pokemon_speciesArrayList.get(position);
            String name = pokemon_species.getName();
            Intent intent = new Intent(context, PokeActivity.class);
            intent.putExtra("MSG",name);
            context.startActivity(intent);
        }
    }
}
