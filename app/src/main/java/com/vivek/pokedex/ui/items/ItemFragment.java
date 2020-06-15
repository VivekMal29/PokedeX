package com.vivek.pokedex.ui.items;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
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
import com.vivek.pokedex.adapter.RecyclerViewAdapterItem;
import com.vivek.pokedex.models.ItemsRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemFragment extends Fragment {

    private ItemViewModel homeViewModel;

    ListView itemList;
    Api api;
    RecyclerView recyclerView;
    public static RecyclerViewAdapterItem recyclerViewAdapterItem;
    ArrayList<ItemsRequest.item> itemArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(ItemViewModel.class);
        View root = inflater.inflate(R.layout.fragment_item, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        FrontActivity.frangment = 2;

        recyclerView = root.findViewById(R.id.itemList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        itemArrayList = new ArrayList<>();

//        itemList = root.findViewById(R.id.itemList);
        final ArrayList<String> listOfItems = new ArrayList<>();
        final ArrayAdapter<String>arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,listOfItems);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);

        Call<ItemsRequest> call = api.getItems(954,0);

        call.enqueue(new Callback<ItemsRequest>() {
            @Override
            public void onResponse(Call<ItemsRequest> call, Response<ItemsRequest> response) {
                if(response.isSuccessful()){
                    ItemsRequest itemsRequest = response.body();
                    ArrayList<ItemsRequest.item> itemArrayListl = itemsRequest.getResults();
                    for(ItemsRequest.item item : itemArrayListl){
                        String name = item.getName();
                        itemArrayList.add(item);

                    }
                    recyclerViewAdapterItem = new RecyclerViewAdapterItem(getContext(),itemArrayList);
                    recyclerView.setAdapter(recyclerViewAdapterItem);

                }
            }

            @Override
            public void onFailure(Call<ItemsRequest> call, Throwable t) {
                Log.d("DVivek",String.valueOf(t));
            }
        });



        return root;
    }
}