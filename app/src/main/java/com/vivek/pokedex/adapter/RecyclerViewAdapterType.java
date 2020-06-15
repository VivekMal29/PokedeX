package com.vivek.pokedex.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vivek.pokedex.R;
import com.vivek.pokedex.TypeOfPokemon;
import com.vivek.pokedex.models.ItemsRequest;
import com.vivek.pokedex.models.Types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RecyclerViewAdapterType extends RecyclerView.Adapter<RecyclerViewAdapterType.ViewHolder>implements Filterable {

    private Context context;
    private ArrayList<Types.result> typeArrayLiat;
    private ArrayList<Types.result> typeArrayLiatFull;
    public static String NameOfType;

    public RecyclerViewAdapterType(Context context, ArrayList<Types.result> typeArrayLiat) {
        this.context = context;
        this.typeArrayLiat = typeArrayLiat;
        typeArrayLiatFull = new ArrayList<>(typeArrayLiat);
    }


    @NonNull
    @Override
    public RecyclerViewAdapterType.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_type,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterType.ViewHolder holder, int position) {

        Types.result result = typeArrayLiat.get(position);
        holder.typeName.setText(result.getName());

    }

    @Override
    public int getItemCount() {
        return typeArrayLiat.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView typeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            typeName = itemView.findViewById(R.id.typeName);

        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Types.result result = typeArrayLiat.get(position);
            NameOfType = result.getName();
            Intent intent  = new Intent(context, TypeOfPokemon.class);
            context.startActivity(intent);

        }
    }
    @Override
    public Filter getFilter() {
        return typeFilter;
    }
    public Filter typeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Types.result> filteredList = new ArrayList<>();

            if(constraint == null ||constraint.length()==0){
                filteredList.addAll(typeArrayLiatFull);
            }
           else {
                String FilterPattern = constraint.toString().toLowerCase().trim();

                for(Types.result result : typeArrayLiatFull){
                    if(result.getName().contains(FilterPattern)){
                        filteredList.add(result);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            typeArrayLiat.clear();
            typeArrayLiat.addAll( (Collection<? extends Types.result>)results.values);
            notifyDataSetChanged();
        }
    };


}
