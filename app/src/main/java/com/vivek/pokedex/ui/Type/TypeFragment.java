package com.vivek.pokedex.ui.Type;

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
import com.vivek.pokedex.adapter.RecyclerViewAdapterType;
import com.vivek.pokedex.models.Types;
import com.vivek.pokedex.models.Types_of_pokemon;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TypeFragment extends Fragment {

    private TypeViewModel typeViewModel;
    public  static String typeName;
    ListView typeList ;
    Api api;
    RecyclerView recyclerView;
    public static RecyclerViewAdapterType recyclerViewAdapterType;
    ArrayList<Types.result> typeArrayList ;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        typeViewModel =
                ViewModelProviders.of(this).get(TypeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_type, container, false);
        final TextView textView = root.findViewById(R.id.text_type);
        typeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        FrontActivity.frangment = 3;

        recyclerView = root.findViewById(R.id.typeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        typeArrayList = new ArrayList<>();


//        typeList = root.findViewById(R.id.typeList);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        final ArrayList<String> listOftypes = new ArrayList<>();
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,listOftypes);
        api = retrofit.create(Api.class);

        Call<Types> typesCall = api.getResult();

        typesCall.enqueue(new Callback<Types>() {
            @Override
            public void onResponse(Call<Types> call, Response<Types> response) {
                if(response.isSuccessful()){
                    Types types = response.body();
                    ArrayList<Types.result> resultArrayList = types.getResults();
                    for(Types.result result : resultArrayList){
                        String name = result.getName();
                        Log.d("DVivek",name);
                       typeArrayList.add(result);
                    }
                    recyclerViewAdapterType = new RecyclerViewAdapterType(getContext(),typeArrayList);
                    recyclerView.setAdapter(recyclerViewAdapterType);
                }
            }

            @Override
            public void onFailure(Call<Types> call, Throwable t) {
                Log.d("DVivek",String.valueOf(t));
            }
        });

//        final Intent intent = new Intent(getContext(), TypeOfPokemon.class);
//        typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                typeName= .get(position);
//                startActivity(intent);
//            }
//        });


        return root;
    }
}