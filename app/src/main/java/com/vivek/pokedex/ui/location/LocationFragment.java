package com.vivek.pokedex.ui.location;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.vivek.pokedex.adapter.RecylerViewAdapterLocation;
import com.vivek.pokedex.models.LocationRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationFragment extends Fragment {

    private LocationViewModel shareViewModel;
    ListView locationList;
    Api api;
    ArrayList<LocationRequest.result> locationArrayLiat;
    RecyclerView recyclerView;
    public static RecylerViewAdapterLocation recylerViewAdapterLocation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(LocationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_location, container, false);
        final TextView textView = root.findViewById(R.id.text_share);
        shareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        FrontActivity.frangment = 1;

        recyclerView = root.findViewById(R.id.locationList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        locationArrayLiat = new ArrayList<>();

//        locationList= root.findViewById(R.id.locationList);
//        final ArrayList<String> listOfLocation = new ArrayList<>();
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,listOfLocation);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);
        Call<LocationRequest> call = api.getLocations(781,0);

        call.enqueue(new Callback<LocationRequest>() {
            @Override
            public void onResponse(Call<LocationRequest> call, Response<LocationRequest> response) {
                if(response.isSuccessful()){
                    LocationRequest location = response.body();
                    ArrayList<LocationRequest.result> resultArrayList = location.getResults();
                    for(LocationRequest.result result : resultArrayList){
                        String name = result.getName();
                        locationArrayLiat.add(result);
                        Log.d("DVivek",name);
                    }
                    recylerViewAdapterLocation = new RecylerViewAdapterLocation(getContext(),locationArrayLiat);
                    recyclerView.setAdapter(recylerViewAdapterLocation);

                }
            }

            @Override
            public void onFailure(Call<LocationRequest> call, Throwable t) {
                Log.d("DVivek",String.valueOf(t));
            }
        });

        return root;
    }
}