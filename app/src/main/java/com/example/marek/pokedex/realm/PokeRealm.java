package com.example.marek.pokedex.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/** Stores pokemon object transformed to realm object */
public class PokeRealm extends RealmObject {

    private String mName;
    private String mForms;
    private String mSpecies;
    private String mSprite;
    private String mAbilities;
    private String mStats;
    private int mWeight;
    private int mHeight;
    @PrimaryKey
    private int mId;

    public PokeRealm() {
    }

    public String getmAbilities() {
        return mAbilities;
    }

    public void setmAbilities(String mAbilities) {
        this.mAbilities = mAbilities;
    }

    public String getmForms() {
        return mForms;
    }

    public void setmForms(String mForms) {
        this.mForms = mForms;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmSpecies() {
        return mSpecies;
    }

    public void setmSpecies(String mSpecies) {
        this.mSpecies = mSpecies;
    }

    public String getmSprite() {
        return mSprite;
    }

    public void setmSprite(String mSprite) {
        this.mSprite = mSprite;
    }

    public String getmStats() {
        return mStats;
    }

    public void setmStats(String mStats) {
        this.mStats = mStats;
    }

    public int getmWeight() {
        return mWeight;
    }

    public void setmWeight(int mWeight) {
        this.mWeight = mWeight;
    }
}
