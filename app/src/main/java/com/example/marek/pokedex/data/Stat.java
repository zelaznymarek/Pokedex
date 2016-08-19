package com.example.marek.pokedex.data;

import org.parceler.Parcel;

/**
 * Created by Marek on 2016-06-30.
 */
@Parcel
public class Stat {

    String statName;
    int statValue;

    public Stat(){

    }

    public Stat(String statName, int statValue) {
        this.statName = statName;
        this.statValue = statValue;
    }
    @Override
    public String toString(){
        return statName + ": " + String.valueOf(statValue);
    }

    public String getStatName() {
        return statName;
    }

    public void setStatName(String statName) {
        this.statName = statName;
    }

    public int getStatValue() {
        return statValue;
    }

    public void setStatValue(int statValue) {
        this.statValue = statValue;
    }
}
