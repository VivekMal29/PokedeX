package com.vivek.pokedex.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vivek.pokedex.Fav_Poke_Activity;
import com.vivek.pokedex.FrontActivity;
import com.vivek.pokedex.R;
import com.vivek.pokedex.models.Pokemon;

import java.util.List;

public class RecyclerViewAdapterFavourites extends RecyclerView.Adapter<RecyclerViewAdapterFavourites.ViewHolder> {
    private Context context;
    private List<Pokemon> pokemonList;

    public RecyclerViewAdapterFavourites(Context context, List<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterFavourites.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fav,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterFavourites.ViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.favName.setText(pokemon.getName());

    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView favName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            favName = itemView.findViewById(R.id.favPokeName);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Pokemon pokemon = pokemonList.get(position);
            Intent intent  = new Intent(context, Fav_Poke_Activity.class);
            intent.putExtra("MSG",position);
            context.startActivity(intent);
        }
    }
}
