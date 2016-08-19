package com.example.marek.pokedex.app;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;

import com.example.marek.pokedex.data.Pokemon;
import com.example.marek.pokedex.R;
import com.example.marek.pokedex.realm.RealmController;
import com.example.marek.pokedex.recyclerview.MyRecyclerAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;



public class MyPokemons extends Activity {

    private List<Pokemon> mPokemonList;
    private MyRecyclerAdapter mAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemonlist);
        ButterKnife.bind(this);


        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.BLACK).build());

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mPokemonList = Parcels.unwrap(getIntent().getParcelableExtra("pokemonList"));

        updateList();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void updateList(){

        mAdapter = new MyRecyclerAdapter(MyPokemons.this, mPokemonList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {

            Intent intent = new Intent(MyPokemons.this,MainActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }




}
