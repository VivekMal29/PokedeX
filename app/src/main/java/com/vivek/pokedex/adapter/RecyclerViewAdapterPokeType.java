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
import com.vivek.pokedex.models.Pokemon;
import com.vivek.pokedex.models.Types_of_pokemon;

import java.util.ArrayList;

public class RecyclerViewAdapterPokeType extends RecyclerView.Adapter<RecyclerViewAdapterPokeType.ViewHolder> {
    private Context context;
    private ArrayList<Pokemon.Type> typeArrayList;

    public RecyclerViewAdapterPokeType(Context context, ArrayList<Pokemon.Type> typeArrayList) {
        this.context = context;
        this.typeArrayList = typeArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterPokeType.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.row_type_poke,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterPokeType.ViewHolder holder, int position) {
            Pokemon.Type type = typeArrayList.get(position);
            String name = type.getName();
            holder.typePokeName.setText(name);

    }

    @Override
    public int getItemCount() {
        return typeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView typePokeName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            typePokeName = itemView.findViewById(R.id.typePokeName);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Pokemon.Type type = typeArrayList.get(position);
            String name = type.getName();
            Intent intent = new Intent(context, PokeActivity.class);
            intent.putExtra("MSG",name);
            context.startActivity(intent);
        }
    }
}
