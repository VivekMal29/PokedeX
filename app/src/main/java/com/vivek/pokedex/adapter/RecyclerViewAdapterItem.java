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
import com.vivek.pokedex.models.Pokemon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerViewAdapterItem extends RecyclerView.Adapter<RecyclerViewAdapterItem.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<ItemsRequest.item> itemArrayList;
    private ArrayList<ItemsRequest.item> itemArrayListfull;

    public RecyclerViewAdapterItem(Context context, ArrayList<ItemsRequest.item> itemArrayList) {
        this.context = context;
        this.itemArrayList = itemArrayList;
        itemArrayListfull = new ArrayList<>(itemArrayList);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int id = position+1;
        ItemsRequest.item item=  itemArrayList.get(position);
        holder.itemName.setText("(#" + id + ") "+item.getName());
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView itemName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemName = itemView.findViewById(R.id.itemName);

        }

        @Override
        public void onClick(View v) {

        }
    }
    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ItemsRequest.item> filteredList = new ArrayList<>();

            if(constraint == null ||constraint.length()==0){
                filteredList.addAll(itemArrayListfull);
            }
            else{
                String  filterPatter = constraint.toString().toLowerCase().trim();

                for(ItemsRequest.item item : itemArrayListfull){
                    if(item.getName().toLowerCase().contains(filterPatter)){
                        filteredList.add(item);
                    }

                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemArrayList.clear();
            itemArrayList.addAll((Collection<? extends ItemsRequest.item>) results.values);
            notifyDataSetChanged();
        }
    };

}
