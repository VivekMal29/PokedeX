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
import com.vivek.pokedex.RegionActivity;
import com.vivek.pokedex.models.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerViewAdapterRegion extends RecyclerView.Adapter<RecyclerViewAdapterRegion.ViewHolder>implements Filterable {

    private Context context;
    private List<Region.result> regionArrayList;
    private List<Region.result> regionArrayListFull;

    public RecyclerViewAdapterRegion(Context context, List<Region.result> regionArrayList) {
        this.context = context;
        this.regionArrayList = regionArrayList;
        regionArrayListFull = new ArrayList<>(regionArrayList);
    }

    @NonNull
    @Override
    public RecyclerViewAdapterRegion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_region, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterRegion.ViewHolder holder, int position) {
        Region.result result = regionArrayList.get(position);
        holder.regionName.setText(result.getName());

    }

    @Override
    public int getItemCount() {
        return regionArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView regionName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            regionName= itemView.findViewById(R.id.regionName);

        }

        @Override
        public void onClick(View v) {
                int position = getAdapterPosition();
            Intent intent = new Intent(context, RegionActivity.class);
            intent.putExtra("MSG",position);
            context.startActivity(intent);
        }
    }
    @Override
    public Filter getFilter() {
        return regionFilter;
    }


    public Filter regionFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Region.result> filteredList = new ArrayList<>();
            if(constraint==null||constraint.length()==0){
                filteredList.addAll(regionArrayListFull);
            }
            else{
                String  filteredPattern = constraint.toString().toLowerCase().trim();
                for(Region.result result : regionArrayListFull){
                    if(result.getName().contains(filteredPattern)){
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
            regionArrayList.clear();
            regionArrayList.addAll((Collection <? extends Region.result>) results.values);
            notifyDataSetChanged();
        }
    };
}
