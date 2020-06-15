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
import com.vivek.pokedex.models.Evolution_Chain;
import com.vivek.pokedex.models.Types_of_pokemon;

import java.util.ArrayList;

import retrofit2.Callback;

public class RecyclerViewAdapterEvolution extends RecyclerView.Adapter<RecyclerViewAdapterEvolution.ViewHolder> {
    private Context context;
    private ArrayList<Evolution_Chain.Species> speciesArrayList;
    private ArrayList<Evolution_Chain.Species> speciesArrayListFull;

    public RecyclerViewAdapterEvolution(Context context, ArrayList<Evolution_Chain.Species> speciesArrayList) {
        this.context = context;
        this.speciesArrayList = speciesArrayList;
        speciesArrayListFull= new ArrayList<>(speciesArrayList);
    }


    @NonNull
    @Override
    public RecyclerViewAdapterEvolution.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_evolution_chain,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterEvolution.ViewHolder holder, int position) {
        Evolution_Chain.Species species = speciesArrayList.get(position);
        String name = species.getName();
        holder.pokeName.setText(name);

    }

    @Override
    public int getItemCount() {
        return speciesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView pokeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            pokeName = itemView.findViewById(R.id.pokeEvoName);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Evolution_Chain.Species species = speciesArrayList.get(position);
            String name = species.getName();
            Intent intent = new Intent(context,PokeActivity.class);
            intent.putExtra("MSG",name);
            context.startActivity(intent);
        }
    }
}
