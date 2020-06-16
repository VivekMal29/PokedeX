package com.vivek.pokedex.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vivek.pokedex.Db_Handler.DbHandler;
import com.vivek.pokedex.FrontActivity;
import com.vivek.pokedex.PokeActivity;
import com.vivek.pokedex.R;
import com.vivek.pokedex.models.Pokemon;
import com.vivek.pokedex.ui.pokemons.Pokemons;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<Pokemon> pokemonList;
    private List<Pokemon> pokemonListFull;
   List<Pokemon> FavpokemonList;




    public RecyclerViewAdapter(Context context, List<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
        pokemonListFull = new ArrayList<>(pokemonList);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    //Where to get single card as viewholder object
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }
    //what will happen after we create view holder object
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Pokemon pokemon =  pokemonList.get(position);
        int id = pokemon.getPrimaryId();
        final DbHandler db = new DbHandler(context);
        FavpokemonList =  db.getFavPokemons();
        for(int i=0;i<FavpokemonList.size();i++){
            Log.d("testing",pokemon.getName()   );
            if(pokemon.getName().equals(FavpokemonList.get(i).getName())){
                Log.d("bugg", "this is fav"+ pokemon.getName());
                holder.favImage.setImageResource(R.drawable.ic_star_black_24dp);
            }
        }
        Picasso.get()
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png")
                .centerCrop()
                .resize(144, 118)
                .into(holder.imageView);




        holder.PokemonName.setText("(#" + id + ")   "+pokemon.getName());
    }
    //How many items?
    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private TextView PokemonName;
        private ImageView favImage;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            PokemonName = itemView.findViewById(R.id.pokemonName);
            favImage = itemView.findViewById(R.id.star);
            imageView = itemView.findViewById(R.id.viewImage);

        }


        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Pokemon pokemon =  pokemonList.get(position);
            Pokemons.pokeId = pokemon.getPrimaryId();
            Intent intent = new Intent(context,PokeActivity.class);
            intent.putExtra("MSG",pokemon.getName());
            context.startActivity(intent);


        }
    }

    @Override
    public Filter getFilter() {
        return pokemonFilter;
    }

    private Filter pokemonFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Pokemon> filteredList = new ArrayList<>();

            if(constraint == null ||constraint.length()==0){
                filteredList.addAll(pokemonListFull);
            }
            else{
                String  filterPatter = constraint.toString().toLowerCase().trim();

                for(Pokemon pokemon : pokemonListFull){
                    if(pokemon.getName().toLowerCase().contains(filterPatter)){
                        filteredList.add(pokemon);
                    }

                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            pokemonList.clear();
            pokemonList.addAll((Collection<? extends Pokemon>) results.values);
            notifyDataSetChanged();
        }
    };
}
