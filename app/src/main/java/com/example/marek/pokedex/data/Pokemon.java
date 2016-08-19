package com.example.marek.pokedex.data;

import org.parceler.Parcel;

import java.util.List;


/**
 * Created by Marek on 2016-06-30.
 */
@Parcel
public class Pokemon {

    int id;
    String name;
    List<String> forms;
    String species;
    String sprite;
    int weight;
    int height;
    List<String> abilities;
    List<Stat> stats;

    public Pokemon(){

    }

    public Pokemon(int weight, List<String> abilities, List<String> forms, int height, int id, String name, String species, String sprite, List<Stat> stats) {
        this.weight = weight;
        this.abilities = abilities;
        this.forms = forms;
        this.height = height;
        this.id = id;
        this.name = name;
        this.species = species;
        this.sprite = sprite;
        this.stats = stats;
    }


    public List<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }

    public List<String> getForms() {
        return forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public List<Stat> getStats() {
        return stats;
    }

    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
