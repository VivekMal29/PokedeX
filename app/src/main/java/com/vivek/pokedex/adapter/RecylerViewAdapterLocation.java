package com.vivek.pokedex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vivek.pokedex.R;
import com.vivek.pokedex.models.ItemsRequest;
import com.vivek.pokedex.models.LocationRequest;
import com.vivek.pokedex.models.Pokemon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecylerViewAdapterLocation extends RecyclerView.Adapter<RecylerViewAdapterLocation.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<LocationRequest.result> locationArrayList;
    private ArrayList<LocationRequest.result> locationArrayListFull;


    public RecylerViewAdapterLocation(Context context, ArrayList<LocationRequest.result> locationArrayList) {
        this.context = context;
        this.locationArrayList = locationArrayList;
        locationArrayListFull = new ArrayList<>(locationArrayList);
    }

    @NonNull
    @Override
    public RecylerViewAdapterLocation.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_location,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerViewAdapterLocation.ViewHolder holder, int position) {
        int id = position+1;
        LocationRequest.result result=  locationArrayList.get(position);
        holder.locationName.setText("(#" + id + ") "+result.getName());

    }

    @Override
    public int getItemCount() {
        return locationArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView locationName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            locationName = itemView.findViewById(R.id.locationName);

        }


        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public Filter getFilter() {
        return locationFilter;
    }

    private Filter locationFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<LocationRequest.result> filteredList = new ArrayList<>();

            if(constraint == null ||constraint.length()==0){
                filteredList.addAll(locationArrayListFull);
            }
            else{
                String  filterPatter = constraint.toString().toLowerCase().trim();

                for(LocationRequest.result result :locationArrayListFull){
                    if(result.getName().toLowerCase().contains(filterPatter)){
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
            locationArrayList.clear();
            locationArrayList.addAll((Collection<? extends LocationRequest.result>) results.values);
            notifyDataSetChanged();
        }
    };
}
