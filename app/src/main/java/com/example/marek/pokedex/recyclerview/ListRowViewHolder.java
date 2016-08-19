package com.example.marek.pokedex.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.marek.pokedex.R;

/** ViewHolder for RecyclerView */

public class ListRowViewHolder extends RecyclerView.ViewHolder {

    protected NetworkImageView mRowIcon;
    protected TextView mPokemonName;
    protected TextView mPokemonId;
    protected RelativeLayout mRelativeLayout;


    public ListRowViewHolder(View view) {
        super(view);

        this.mRowIcon = (NetworkImageView) view.findViewById(R.id.row_icon);
        this.mPokemonName = (TextView) view.findViewById(R.id.pokemonName);
        this.mPokemonId = (TextView) view.findViewById(R.id.poke_id);
        this.mRelativeLayout = (RelativeLayout) view.findViewById(R.id.row_layout);
        view.setClickable(true);
    }
}
