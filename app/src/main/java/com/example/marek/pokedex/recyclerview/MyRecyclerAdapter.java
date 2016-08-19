package com.example.marek.pokedex.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.marek.pokedex.app.PokemonViewer;
import com.example.marek.pokedex.data.Pokemon;
import com.example.marek.pokedex.R;

import com.example.marek.pokedex.utils.MySingleton;

import java.util.List;


/** Adapter for RecyclerView */

public class MyRecyclerAdapter extends RecyclerView.Adapter<ListRowViewHolder> {

    private List<Pokemon> mPokemonList;
    private Context mContext;
    private ImageLoader mImageLoader;
    private int mFocusedItem = 0;

    public MyRecyclerAdapter(Context context, List<Pokemon> pokemonList){
        this.mContext = context;
        this.mPokemonList = pokemonList;

    }



    @Override
    public ListRowViewHolder onCreateViewHolder(final ViewGroup viewGroup, int position){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        ListRowViewHolder holder = new ListRowViewHolder(v);

        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                TextView pokemonId = (TextView) v.findViewById(R.id.poke_id);

                Intent intent = new Intent(mContext, PokemonViewer.class);
                Bundle extras = new Bundle();
                extras.putInt("pokemonId", Integer.valueOf(pokemonId.getText().toString()));
                extras.putString("from", "list");
                intent.putExtras(extras);
                mContext.startActivity(intent);

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(final ListRowViewHolder listRowViewHolder, int position){
        Pokemon pokemon = mPokemonList.get(position);
        listRowViewHolder.itemView.setSelected(mFocusedItem == position);

        listRowViewHolder.getLayoutPosition();

        mImageLoader = MySingleton.getInstance(mContext).getImageLoader();

        listRowViewHolder.mRowIcon.setImageUrl(pokemon.getSprite(), mImageLoader);
        listRowViewHolder.mRowIcon.setDefaultImageResId(R.drawable.row_icon_placeholder);

        listRowViewHolder.mPokemonName.setText(Html.fromHtml(pokemon.getName()));

        listRowViewHolder.mPokemonId.setText(Html.fromHtml(String.valueOf(pokemon.getId())));
    }


    public int getItemCount(){
        return (mPokemonList != null ? mPokemonList.size() : 0);
    }


}
