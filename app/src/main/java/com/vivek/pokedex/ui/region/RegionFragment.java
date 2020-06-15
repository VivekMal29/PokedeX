package com.vivek.pokedex.ui.region;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vivek.pokedex.Api;
import com.vivek.pokedex.FrontActivity;
import com.vivek.pokedex.R;
import com.vivek.pokedex.TypeOfPokemon;
import com.vivek.pokedex.adapter.RecyclerViewAdapterRegion;
import com.vivek.pokedex.models.Region;
import com.vivek.pokedex.models.Types;
import com.vivek.pokedex.ui.Type.TypeViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegionFragment extends Fragment {

    private RegionViewModel toolsViewModel;
    public  static String regionName;
    ListView regionList ;
    Api api;
    RecyclerView recyclerView;
    public static RecyclerViewAdapterRegion recyclerViewAdapterRegion;
    ArrayList<Region.result> regionArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(RegionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_region, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

//        regionList = root.findViewById(R.id.regionList);

        FrontActivity.frangment = 4;

        recyclerView = root.findViewById(R.id.regionList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        regionArrayList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        final ArrayList<String> listOfRegions = new ArrayList<>();
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,listOfRegions);
        api = retrofit.create(Api.class);

        Call<Region> call = api.getRegions();


        call.enqueue(new Callback<Region>() {
            @Override
            public void onResponse(Call<Region> call, Response<Region> response) {
                if(response.isSuccessful()){
                    Region region = response.body();
                    ArrayList<Region.result> resultArrayList = region.getResults();
                    for(Region.result result : resultArrayList){
                        String name = result.getName();
                        Log.d("DVivek",name);
                        regionArrayList.add(result);
                    }
                    recyclerViewAdapterRegion = new RecyclerViewAdapterRegion(getContext(),regionArrayList);
                    recyclerView.setAdapter(recyclerViewAdapterRegion);
                }
            }

            @Override
            public void onFailure(Call<Region> call, Throwable t) {
                Log.d("DVivek",String.valueOf(t));
            }
        });

//        final Intent intent = new Intent(getContext(), TypeOfPokemon.class);
//        regionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                regionName= listOfRegions.get(position);
//                startActivity(intent);
//            }
//        });



        return root;
    }
}