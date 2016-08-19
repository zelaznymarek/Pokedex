package com.example.marek.pokedex.realm;

import io.realm.RealmObject;

/** Stores pokemons stats */
public class StatRealm extends RealmObject {

    private String mStatName;
    private int mStatValue;

    public StatRealm(){}

    public String getmStatName() {
        return mStatName;
    }

    public void setmStatName(String statName) {
        this.mStatName = statName;
    }

    public int getmStatValue() {
        return mStatValue;
    }

    public void setmStatValue(int statValue) {
        this.mStatValue = statValue;
    }
}

